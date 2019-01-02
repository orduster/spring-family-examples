### Spring Boot Shiro在线会话管理

- 在Shiro中我们可以通过 `org.apache.shiro.session.mgt.eis.SessionDAO` 对象的  `getActiveSessions()` 方法方便的获取到当前所有有效的 Session 对象。通过这些Session对象，我们可以实现一些功能，比如查看当前系统的在线人数，查看这些在线用户的一些基本信息，强制让某个用户下线等。
- 在现有的Spring Boot Shiro项目基础上进行一些改造（缓存使用Ehcache）。



1. 更改 ShiroConfig ，为了能够在Spring Boot中使用`SessionDao`，我们在ShiroConfig中配置该Bean：

   ```java
   @Bean
   public SessionDAO sessionDAO() {
       return new MemorySessionDAO();
   }
   ```

   > 使用的是Redis作为缓存实现，那么 SessionDAO 则为 `RedisSessionDAO`：

   ```java
   //使用shiro作为缓存实现
   @Bean
   public RedisSessionDAO sessionDAO() {
       RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
       redisSessionDAO.setRedisManager(redisManager());
       return redisSessionDAO;
   }
   ```

2. 在 Shiro 中，`SessionDao` 通过 `org.apache.shiro.session.mgt.SessionManager` 进行管理，所以继续在 ShiroConfig 中配置 `SessionManager`：

   ```java
   @Bean
   public SecurityManager securityManager() {
       //配置 SecurityManager，并注入 shiroRealm，注意不是DefaultSecurityManager
       DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
       securityManager.setRealm(shiroRealm());
   
       //设置 cookie 管理对象
       securityManager.setRememberMeManager(rememberMeManager());
   
       //加入 缓存对象
       securityManager.setCacheManager(getEhCacheManager());
   
       //加入会话管理
       securityManager.setSessionManager(sessionManager());
   
       return securityManager;
   }
   
   //加入会话管理
   @Bean
   public SessionManager sessionManager() {
       DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
       Collection<SessionListener> listeners = new ArrayList<SessionListener>();
       listeners.add(new ShiroSessionListener());
       sessionManager.setSessionListeners(listeners);
       sessionManager.setSessionDAO(sessionDAO());
       return sessionManager;
   }
   ```

3. 上面代码中，`ShiroSessionListener` 为 `org.apache.shiro.session.SessionListener` 接口的手动实现，所以接下来定义一个该接口的实现：

   ```java
   import org.apache.shiro.session.Session;
   import org.apache.shiro.session.SessionListener;
   
   import java.util.concurrent.atomic.AtomicInteger;
   
   public class ShiroSessionListener implements SessionListener {
   
       private final AtomicInteger sessionCount = new AtomicInteger(0);
   
       @Override
       public void onStart(Session session) {
           sessionCount.incrementAndGet();
       }
   
       @Override
       public void onStop(Session session) {
           sessionCount.decrementAndGet();
       }
   
       @Override
       public void onExpiration(Session session) {
           sessionCount.decrementAndGet();
       }
   }
   ```

   > 其维护着一个原子类型的Integer对象，用于统计在线Session的数量。

4. 配置完ShiroConfig后，我们可以创建一个UserOnline实体类，用于描述每个在线用户的基本信息：

   ```java
   package com.ordust.entity;
   
   import java.io.Serializable;
   import java.util.Date;
   
   public class UserOnline implements Serializable {
   
       private static final long serialVersionUID = -3047532658814495889L;
       private String id; //Session id
       private String userId; //用户 id
       private String username; //用户名称
       private String host; //用户主机地址
       private String systemHost; //用户登录时系统 IP
       private String status; //状态
       private Date startTimestamp; // session 创建时间
       private Date lastAccessTime; //session 最后访问时间
       private Long timeout; // 超时时间
   
       //省略 setter、getter、toString 方法
   }
   ```

5. 创建一个 Service 接口，包含查看所有在线用户和根据 SessionId 踢出用户抽象方法：

   ```java
   import com.ordust.entity.UserOnline;
   
   import java.util.List;
   
   public interface SessionService {
       List<UserOnline> list();
   
       boolean forceLogout(String sessionId);
   }
   ```

6. Service 接口具体实现：

   ```java
   import com.ordust.entity.User;
   import com.ordust.entity.UserOnline;
   import com.ordust.service.SessionService;
   import org.apache.shiro.session.Session;
   import org.apache.shiro.session.mgt.eis.SessionDAO;
   import org.apache.shiro.subject.SimplePrincipalCollection;
   import org.apache.shiro.subject.support.DefaultSubjectContext;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   
   import java.util.ArrayList;
   import java.util.Collection;
   import java.util.List;
   
   @Service("sessionService")
   public class SessionServiceImpl implements SessionService {
   
       @Autowired
       private SessionDAO sessionDAO;
   
       @Override
       public List<UserOnline> list() {
           List<UserOnline> list = new ArrayList<>();
           Collection<Session> sessions = sessionDAO.getActiveSessions();
           for (Session session : sessions) {
               UserOnline userOnline = new UserOnline();
               User user = new User();
               SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
               if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                   continue;
               } else {
                   principalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                   user = (User) principalCollection.getPrimaryPrincipal();
                   userOnline.setUsername(user.getUserName());
                   userOnline.setUserId(user.getId().toString());
               }
               userOnline.setId((String) session.getId());
               userOnline.setHost(session.getHost());
               userOnline.setStartTimestamp(session.getStartTimestamp());
               userOnline.setLastAccessTime(session.getLastAccessTime());
               Long timeout = session.getTimeout();
               if (timeout == 0) {
                   userOnline.setStatus("离线");
               } else {
                   userOnline.setStatus("在线");
               }
               userOnline.setTimeout(timeout);
               list.add(userOnline);
           }
   
           return list;
       }
   
       @Override
       public boolean forceLogout(String sessionId) {
           Session session = sessionDAO.readSession(sessionId);
           session.setTimeout(0);
           return true;
       }
   
       //redis 作为缓存
       /*@Override
       public boolean forceLogout(String sessionId) {
           Session session = sessionDAO.readSession(sessionId);
           sessionDAO.delete(session);
           return true;
       }*/
   }
   ```

   > 通过 SessionDao 的 `getActiveSessions()` 方法，我们可以获取所有有效的 Session，通过该 Session，我们还可以获取到当前用户的 Principal 信息。
   >
   > 当某个用户被踢出后（Session Time置为0），该 Session 并不会立刻从 ActiveSessions 中剔除，所以我们可以通过其 timeout 信息来判断该用户在线与否。

7. 定义一个SessionContoller，用于处理Session的相关操作：

   ```java
   import com.ordust.entity.ResponseBo;
   import com.ordust.entity.UserOnline;
   import com.ordust.service.SessionService;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Controller;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.ResponseBody;
   
   import java.util.List;
   
   @Controller
   @RequestMapping("/online")
   public class SessionController {
   
       @Autowired
       private SessionService sessionService;
   
       @RequestMapping("/index")
       public String online() {
           return "online";
       }
   
       @RequestMapping("/list")
       @ResponseBody
       public List<UserOnline> list() {
           return sessionService.list();
       }
   
       @RequestMapping("/forceLogout")
       @ResponseBody
       public ResponseBo forceLogout(String id) {
           try {
               sessionService.forceLogout(id);
               return ResponseBo.ok();
           } catch (Exception e) {
               e.printStackTrace();
               return ResponseBo.error("踢出用户失败");
           }
       }
   
   }
   ```

8. 我们编写一个online.html页面，用于展示所有在线用户的信息：

   ```html
   <!doctype html>
   <html lang="zh-cn" xmlns:th="http://www.thymeleaf.org">
   <head>
       <meta charset="UTF-8">
       <meta name="viewport"
             content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
       <meta http-equiv="X-UA-Compatible" content="ie=edge">
       <title>在线用户管理</title>
       <link rel="stylesheet" th:href="@{/css/online.css}">
   </head>
   <body>
   <h3>在线用户人数：<span id="onlineCount"></span></h3>
   <table>
       <tr>
           <td>序号</td>
           <td>用户名称</td>
           <td>登录时间</td>
           <td>最后访问时间</td>
           <td>主机</td>
           <td>状态</td>
           <td>操作</td>
       </tr>
   </table>
   <a th:href="@{/index}">返回</a>
   
   <script th:src="@{/js/jquery-1.11.1.min.js}"></script>
   <script th:src="@{/js/dateFormat.js}"></script>
   <script th:inline="javascript">
       var ctx = [[@{/}]];
       $.get(ctx + "online/list", {}, function (result) {
           console.log(result);
           var length = result.length;
           $('#onlineCount').text(length);
           var html = "";
           for (var i = 0; i < length; i++) {
               html += "<tr>"
                   + "<td>" + (i + 1) + "</td>"
                   + "<td>" + result[i].username + "</td>"
                   + "<td>" + new Date(result[i].startTimestamp).Format("yyyy-MM-dd hh:mm:ss") + "</td>"
                   + "<td>" + new Date(result[i].lastAccessTime).Format("yyyy-MM-dd hh:mm:ss") + "</td>"
                   + "<td>" + result[i].host + "</td>"
                   + "<td>" + result[i].status + "</td>"
                   + "<td><a href='#' onclick='offline(\"" + result[i].id + "\",\"" + result[i].status + "\")'>下线</a></td>"
                   + "</tr>";
           }
           $("table").append(html);
       }, "json");
   
       function offline(id, status) {
           if (status == "离线") {
               alert("该用户已经是离线状态！");
               return;
           }
           $.get(ctx + "online/forceLogout", {"id": id}, function (result) {
               if (result.code == 0) {
                   alert("该用户已经强制下线！");
                   location.href = ctx + 'online/index';
               } else {
                   alert(result.msg);
               }
           }, "json");
       }
   </script>
   </body>
   </html>
   ```

9. 在index.html中加入该页面的入口：

   ```html
   <!doctype html>
   <html lang="zh-cn" xmlns:th="http://www.thymeleaf.org"
         xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">
   <head>
       <meta charset="UTF-8">
       <meta name="viewport"
             content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
       <meta http-equiv="X-UA-Compatible" content="ie=edge">
       <title>首页</title>
   </head>
   <body>
   <p>你好！[[${user.userName}]]</p>
   <p shiro:hasRole="admin">你的角色为超级管理员</p>
   <p shiro:hasRole="test">你的角色为测试账户</p>
   <a th:href="@{/logout}">注销</a>
   
   <h3>权限测试链接</h3>
   <div>
       <div>
           <a shiro:hasPermission="user:user" th:href="@{/user/list}">获取用户信息</a>
           <a shiro:hasPermission="user:add" th:href="@{/user/add}">新增用户</a>
           <a shiro:hasPermission="user:delete" th:href="@{/user/delete}">删除用户</a>
       </div>
   </div>
   <a shiro:hasRole="admin" th:href="@{/online/index}">在线用户管理</a>
   </body>
   </html>
   ```




> 参考博文：https://mrbird.cc/Spring-Boot-Shiro%20session.html