[TOC]

### 1. Apace Shiro 简介

#### 1.1 简介

- Apache Shiro 是 Java 的一个安全（权限）框架。
- Shiro 可以非常容易的开发出足够好的应用，其不仅可以用在 JavaSE 环境，也可以用在 JavaEE 环境。
- Shiro 可以完成：认证、授权、加密、会话管理、与Web 集成、 缓存等。
- 下载：http://shiro.apache.org/ 
- 阅读文档：http://shiro.apache.org/reference.html



#### 1.2 功能简介

- 基本功能点如下图所示：

  ![ShiroFeatures.png](../images/ShiroFeatures.png)

- **Authentication：身份认证/登录**，验证用户是不是拥有相应的身份；

- **Authorization：授权，即权限验证**，验证某个已认证的用户是否拥有某个权限；即判断用户是否能进行什么操作，如：验证某个用户是否拥有某个角色。或者细粒度的验证某个用户对某个资源是否具有某个权限；

- **Session Manager：会话管理**，即用户登录后就是一次会话，在没有退出之前，它的所有信息都在会话中；**会话可以是普通 JavaSE 环境，也可以是 Web 环境的**；

- **Cryptography：加密**，保护数据的安全性，如密码加密存储到数据库，而不是明文存储；

- **Web Support：Web 支持**，可以非常容易的集成到Web 环境；

- **Caching：缓存**，比如用户登录后，其用户信息、拥有的角色/权限不必每次去查，这样可以提高效率；

- Concurrency：Shiro 支持多线程应用的并发验证，即如在一个线程中开启另一个线程，能把权限自动传播过去；

- Testing：提供测试支持；

- **Run As：允许一个用户假装为另一个用户（如果他们允许）的身份进行访问；**

- **Remember Me：记住我**，这个是非常常见的功能，即一次登录后，下次再来的话不用登录了 



#### 1.3 Shiro 架构：

1. 从应用程序的角度（外部）来看：

   - 图示：

     ![](../images/sdfjasdr3857312-323412.png)

   - **Subject：应用代码直接交互的对象是 Subject**，也就是说 Shiro 的对外API 核心就是 Subject。 **Subject 代表了当前“用户”** ， 这个用户不一定是一个具体的人，与当前应用交互的任何东西都是 Subject，如网络爬虫，机器人等；**与 Subject 的所有交互都会委托给 SecurityManager；Subject 其实是一个门面，SecurityManager 才是实际的执行者**；

   - **SecurityManager：安全管理器；即所有与安全有关的操作都会与SecurityManager 交互**；且其管理着所有 Subject；可以看出它是 **Shiro的核心**，它**负责与 Shiro 的其他组件进行交互**，它相当于 SpringMVC 中DispatcherServlet 的角色

   - **Realm：Shiro 从 Realm 获取安全数据（如用户、角色、权限）**，就是说SecurityManager 要验证用户身份，那么它需要从 Realm 获取相应的用户进行比较以确定用户身份是否合法；也需要从 Realm 得到用户相应的角色/权限进行验证用户是否能进行操作；**可以把 Realm 看成 DataSource** 

2. 从内部看：

   - 图示：

     ![img](../images/ShiroArchitecture.png)

   - **Subject**：任何可以与应用交互的“用户”；

   - **SecurityManager** ：相当于SpringMVC 中的 DispatcherServlet；是 Shiro 的心脏；所有具体的交互都通过 SecurityManager 进行控制；它管理着所有 Subject、且负责进行认证、授权、会话及缓存的管理。

   - **Authenticator：负责 Subject 认证**，是一个扩展点，可以自定义实现；可以使用认证策略（Authentication Strategy），即什么情况下算用户认证通过了；

   - **Authorizer：授权器**、 即访问控制器，用来决定主体是否有权限进行相应的操作；即**控制着用户能访问应用中的哪些功能；**

   - **Realm**：可以有 1 个或多个 Realm，可以认为是安全实体数据源，即用于获取安全实体的；可以是JDBC 实现，也可以是内存实现等等；由用户提供；所以一般在应用中都需要实现自己的 Realm；

   - **SessionManager：管理 Session 生命周期的组件；**而 Shiro 并不仅仅可以用在 Web环境，也可以用在如普通的 JavaSE 环境

   - **CacheManager：缓存控制器，**来管理如用户、角色、权限等的缓存的；因为这些数据基本上很少改变，放到缓存中后可以提高访问的性能

   - **Cryptography：密码模块**，Shiro 提高了一些常见的加密组件用于如密码加密/解密。 



### 2. Spring Boot Shiro 用户认证

1. 实现流程

   - 定义一个配置 SecurityManager Bean 的配置配置类 ShiroConfig
   - 在 ShiroConfig 中配置 Shiro 过滤器工厂 ShiroFilterFactoryBean，依赖于 SecurityManager
   - 自定义 Realm 实现

2. 引入依赖：

   - spring boot web 基本环境

   - Shiro 依赖

   - MyBatis、数据库（mysql）依赖

   - thymeleaf 依赖

     ```xml
     <dependencies>
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-web</artifactId>
         </dependency>
     
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-test</artifactId>
             <scope>test</scope>
         </dependency>
     
         <!--thymeleaf-->
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-thymeleaf</artifactId>
         </dependency>
     
         <!--mybatis-->
         <dependency>
             <groupId>org.mybatis.spring.boot</groupId>
             <artifactId>mybatis-spring-boot-starter</artifactId>
             <version>1.3.2</version>
         </dependency>
     
         <!--shiro-spring-->
         <dependency>
             <groupId>org.apache.shiro</groupId>
             <artifactId>shiro-spring</artifactId>
             <version>1.4.0</version>
         </dependency>
     
         <!--MySQL 驱动-->
         <dependency>
             <groupId>mysql</groupId>
             <artifactId>mysql-connector-java</artifactId>
             <version>5.1.47</version>
         </dependency>
     
         <!--druid 数据源驱动-->
         <dependency>
             <groupId>com.alibaba</groupId>
             <artifactId>druid-spring-boot-starter</artifactId>
             <version>1.1.10</version>
         </dependency>
     </dependencies>
     ```

3. Shio 配置类 — ShiroConfig

   ```java
   import com.ordust.shiro.ShiroRealm;
   import org.apache.shiro.mgt.DefaultSecurityManager;
   import org.apache.shiro.mgt.SecurityManager;
   import org.apache.shiro.spring.LifecycleBeanPostProcessor;
   import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
   import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   import java.util.LinkedHashMap;
   
   /**
    * shiro 配置类
    */
   @Configuration
   public class ShiroConfig {
   
       @Bean
       public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
           ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
           //设置securityManager
           shiroFilterFactoryBean.setSecurityManager(securityManager);
           //登录的url
           shiroFilterFactoryBean.setLoginUrl("/login");
           //登录成功跳转的yrl
           shiroFilterFactoryBean.setSuccessUrl("/index");
           //未授权的url
           shiroFilterFactoryBean.setUnauthorizedUrl("/403");
   
           //使用 LinkedHashMap 保证添加后有序
           LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
   
           //定义filterChain，静态资源不拦截
           filterChainDefinitionMap.put("/css/**", "anon");
           filterChainDefinitionMap.put("/js/**", "anon");
           filterChainDefinitionMap.put("/fonts/**", "anon");
           filterChainDefinitionMap.put("/img/**", "anon");
           //druid 数据源监控界面不拦截
           filterChainDefinitionMap.put("/druid/**", "anon");
           //配置退出过滤器，其中具体的退出代码 Shiro 已经帮我们实现了
           filterChainDefinitionMap.put("/logout", "logout");
           filterChainDefinitionMap.put("/", "anon");
           //除以上所有 url 都必须通过认证才可以访问，未通过认证自动访问 loginurl
           filterChainDefinitionMap.put("/**", "authc");
   
           shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
   
           return shiroFilterFactoryBean;
       }
   
       @Bean
       public SecurityManager securityManager() {
           //配置 SecurityManager，并注入 shiroRealm，注意不是DefaultSecurityManager
           DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
           securityManager.setRealm(shiroRealm());
           return securityManager;
       }
   
       @Bean(name = "lifecycleBeanPostProcessor")
       public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
           //shiro生命周期处理器
           return new LifecycleBeanPostProcessor();
       }
   
       @Bean
       public ShiroRealm shiroRealm() {
           //配置 Realm，需要自己实现
           return new ShiroRealm();
       }
   
   }
   ```

   - filterChainDefinitionMap 的实现使用 LinkedHashMap，保证元素的迭代顺序是插入顺序。

   - filterChain 基于短路机制，即最先匹配原则：

     ```java
     /user/**=anon
     /user/aa=authc 永远不会执行
     ```

   - Shiro 为我们实现的过滤器如下：

     | Filter Name       | Class                                                        | Description                                                  |
     | ----------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
     | anon              | [org.apache.shiro.web.filter.authc.AnonymousFilter](http://shiro.apache.org/static/current/apidocs/org/apache/shiro/web/filter/authc/AnonymousFilter.html) | 匿名拦截器，即不需要登录即可访问；一般用于静态资源过滤；示例`/static/**=anon` |
     | authc             | [org.apache.shiro.web.filter.authc.FormAuthenticationFilter](http://shiro.apache.org/static/current/apidocs/org/apache/shiro/web/filter/authc/FormAuthenticationFilter.html) | 基于表单的拦截器；如`/**=authc`，如果没有登录会跳到相应的登录页面登录 |
     | authcBasic        | [org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter](http://shiro.apache.org/static/current/apidocs/org/apache/shiro/web/filter/authc/BasicHttpAuthenticationFilter.html) | Basic HTTP身份验证拦截器                                     |
     | logout            | [org.apache.shiro.web.filter.authc.LogoutFilter](http://shiro.apache.org/static/current/apidocs/org/apache/shiro/web/filter/authc/LogoutFilter.html) | 退出拦截器，主要属性：redirectUrl：退出成功后重定向的地址（/），示例`/logout=logout` |
     | noSessionCreation | [org.apache.shiro.web.filter.session.NoSessionCreationFilter](http://shiro.apache.org/static/current/apidocs/org/apache/shiro/web/filter/session/NoSessionCreationFilter.html) | 不创建会话拦截器，调用`subject.getSession(false)`不会有什么问题，但是如果`subject.getSession(true)`将抛出`DisabledSessionException`异常 |
     | perms             | [org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter](http://shiro.apache.org/static/current/apidocs/org/apache/shiro/web/filter/authz/PermissionsAuthorizationFilter.html) | 权限授权拦截器，验证用户是否拥有所有权限；属性和roles一样；示例`/user/**=perms["user:create"]` |
     | port              | [org.apache.shiro.web.filter.authz.PortFilter](http://shiro.apache.org/static/current/apidocs/org/apache/shiro/web/filter/authz/PortFilter.html) | 端口拦截器，主要属性`port(80)`：可以通过的端口；示例`/test= port[80]`，如果用户访问该页面是非80，将自动将请求端口改为80并重定向到该80端口，其他路径/参数等都一样 |
     | rest              | [org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter](http://shiro.apache.org/static/current/apidocs/org/apache/shiro/web/filter/authz/HttpMethodPermissionFilter.html) | rest风格拦截器，自动根据请求方法构建权限字符串；示例`/users=rest[user]`，会自动拼出user:read,user:create,user:update,user:delete权限字符串进行权限匹配（所有都得匹配，isPermittedAll） |
     | roles             | [org.apache.shiro.web.filter.authz.RolesAuthorizationFilter](http://shiro.apache.org/static/current/apidocs/org/apache/shiro/web/filter/authz/RolesAuthorizationFilter.html) | 角色授权拦截器，验证用户是否拥有所有角色；示例`/admin/**=roles[admin]` |
     | ssl               | [org.apache.shiro.web.filter.authz.SslFilter](http://shiro.apache.org/static/current/apidocs/org/apache/shiro/web/filter/authz/SslFilter.html) | SSL拦截器，只有请求协议是https才能通过；否则自动跳转会https端口443；其他和port拦截器一样； |
     | user              | [org.apache.shiro.web.filter.authc.UserFilter](http://shiro.apache.org/static/current/apidocs/org/apache/shiro/web/filter/authc/UserFilter.html) | 用户拦截器，用户已经身份验证/记住我登录的都可；示例`/**=user` |

4. Realm

   - 自定义 Realm 需要继承 AuthorizingRealm 类，再实现 `doGetAuthorizationInfo()`和`doGetAuthenticationInfo()` 方法。authorization，为授权，批准的意思，即获取用户的角色和权限等信息；authentication，认证，身份验证的意思，即登录时验证用户的合法性，比如验证用户名和密码。

     ```java
     import com.ordust.dao.UserMapper;
     import com.ordust.entity.User;
     import org.apache.shiro.authc.*;
     import org.apache.shiro.authz.AuthorizationInfo;
     import org.apache.shiro.realm.AuthorizingRealm;
     import org.apache.shiro.subject.PrincipalCollection;
     import org.springframework.beans.factory.annotation.Autowired;
     
     public class ShiroRealm extends AuthorizingRealm {
     
         @Autowired
         private UserMapper userMapper;
     
         /**
          * 获取用户角色和权限
          */
         @Override
         protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
             return null;
         }
     
         /**
          * 验证用户的合法性
          */
         @Override
         protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
             //获取用户输入的用户名和密码
             String userName = (String) token.getPrincipal();
             String password = new String((char[]) token.getCredentials());
     
             System.out.println("用户" + userName + "认证-----ShiroRealm.doGetAuthenticationInfo");
     
             //通过用户名到数据库查询用户信息
             User user = userMapper.findByUserName(userName);
     
             if (user == null) {
                 throw new UnknownAccountException("用户名或者密码错误！");
             }
             if (!password.equals(user.getPassword())) {
                 throw new IncorrectCredentialsException("用户名或者密码错误！");
             }
             if (user.getStatus().equals("0")) {
                 throw new LockedAccountException("账号已经被锁定，请联系管理员！");
             }
             return new SimpleAuthenticationInfo(user, password, getName());
         }
     }
     ```

5. 使用 MD5 进行加密：

   ```java
   import org.apache.shiro.crypto.hash.SimpleHash;
   import org.apache.shiro.util.ByteSource;
   
   public class MD5Utils {
       //加密的盐值
       private static final String salt = "ordust";
       //算法名称
       private static final String algorith_name = "md5";
       //加密次数
       private static final int hash_iterations = 2;
   
       /**
        * 使用盐值 ordust 加密，得到新密码
        */
       public static String encrypt(String password) {
           String newPassword = new SimpleHash(algorith_name, password, ByteSource.Util.bytes(salt), hash_iterations).toHex();
           return newPassword;
       }
   
       /**
        * 使用盐值 username+ordust 加密，得到新密码
        */
       public static String encrypt(String username, String password) {
           String newPassword = new SimpleHash(algorith_name, password, ByteSource.Util.bytes(username + salt), hash_iterations).toHex();
           return newPassword;
       }
   
       //这里主函数主要是得到测试数据
       public static void main(String[] args) {
           //得到测试数据
           System.out.println(MD5Utils.encrypt("ordust","12345"));
       }
   }
   ```

6. 创建数据表和实体类：

   ```mysql
   DROP TABLE IF EXISTS `t_user`;
   
   CREATE TABLE `t_user` (
     `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
     `USERNAME` varchar(50) NOT NULL COMMENT '用户名',
     `PASSWORD` varchar(128) NOT NULL COMMENT '密码',
     `STATUS` char(1) NOT NULL COMMENT '状态 0锁定 1有效',
     `CRATE_TIME` date NOT NULL COMMENT '创建时间',
     PRIMARY KEY (`ID`)
   ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
   
   
   insert  into `t_user`(`ID`,`USERNAME`,`PASSWORD`,`STATUS`,`CRATE_TIME`) values (1,'ordust','93e4a60023701f52b8e8cb50472ff210','1','2018-12-31'),(2,'12345','cd1191e3c54919fc5fed64516bbb53fe','1','2018-12-31');
   ```

   > 这里有两个用户，用户一(ordust，12345)；用户二(12345，12345)，加密方法见工具类 MD5Utils

   ```java
   /**
    * 用户实体类
    */
   public class User implements Serializable {
   
       private static final long serialVersionUID = 5564159164407658394L;
       private Integer id;
       private String userName;
       private String password;
       private Date createTime;
       private String status;
       // 省略 setter、getter、toString 方法
   }
   ```

7. 持久层：

   - 定义接口 UserMapper：

     ```java
     import com.ordust.entity.User;
     import org.apache.ibatis.annotations.Mapper;
     
     @Mapper
     public interface UserMapper {
         User findByUserName(String username);
     }
     ```

   - xml 实现

     ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
     <mapper namespace="com.ordust.dao.UserMapper">
         <resultMap id="User" type="com.ordust.entity.User">
             <id column="id" property="id" javaType="java.lang.Integer" jdbcType="BIGINT"/>
             <id column="username" property="userName" javaType="java.lang.String" jdbcType="VARCHAR"/>
             <id column="password" property="password" javaType="java.lang.String" jdbcType="VARCHAR"/>
             <id column="create_time" property="createTime" javaType="java.util.Date" jdbcType="DATE"/>
             <id column="status" property="status" javaType="java.lang.String" jdbcType="VARCHAR"/>
         </resultMap>
     
         <select id="findByUserName" resultMap="User">
             select * from t_user where username = #{userName}
         </select>
     </mapper>
     ```

8. 准备页面：

   - 登录界面：login.html

     ```html
     <!doctype html>
     <html lang="zh-cn" xmlns:th="http://www.thymeleaf.org">
     <head>
         <meta charset="UTF-8">
         <meta name="viewport"
               content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
         <meta http-equiv="X-UA-Compatible" content="ie=edge">
         <title>登录</title>
         <link rel="stylesheet" th:href="@{/css/login.css}">
     </head>
     <body>
     
     <div class="login-page">
         <div class="form">
             <input type="text" placeholder="用户名" name="username" required="required"/>
             <input type="password" placeholder="密码" name="password" required="required"/>
             <button onclick="login()">登录</button>
         </div>
     </div>
     <script th:src="@{/js/jquery-1.11.1.min.js}"></script>
     <script th:inline="javascript">
         var ctx = [[@{/}]];
         function login() {
             var username = $("input[name='username']").val();
             var password = $("input[name='password']").val();
             $.ajax({
                 type: "post",
                 url: ctx + "login",
                 data: {"username": username, "password": password},
                 dataType: "json",
                 success: function (result) {
                     if (result.code == 0) {
                         location.href = ctx + 'index';
                     } else {
                         alert(result.msg);
                     }
                 }
             });
         }
     </script>
     </body>
     </html>
     ```

   - 主界面：index.html

     ```html
     <!doctype html>
     <html lang="zh-cn" xmlns:th="http://www.thymeleaf.org">
     <head>
         <meta charset="UTF-8">
         <meta name="viewport"
               content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
         <meta http-equiv="X-UA-Compatible" content="ie=edge">
         <title>首页</title>
     </head>
     <body>
     <p>你好！[[${user.userName}]]</p>
     <a th:href="@{/logout}">注销</a>
     </body>
     </html>
     ```

9. 控制层：LoginController

   ```java
   import com.ordust.entity.ResponseBo;
   import com.ordust.entity.User;
   import com.ordust.util.MD5Utils;
   import org.apache.shiro.SecurityUtils;
   import org.apache.shiro.authc.*;
   import org.apache.shiro.subject.Subject;
   import org.springframework.stereotype.Controller;
   import org.springframework.ui.Model;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.PostMapping;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.ResponseBody;
   
   @Controller
   public class LoginController {
   
       @GetMapping("/login")
       public String login() {
           return "login";
       }
   
       @PostMapping("/login")
       @ResponseBody
       public ResponseBo login(String username, String password) {
           //对密码进行加密
           password = MD5Utils.encrypt(username, password);
           UsernamePasswordToken token = new UsernamePasswordToken(username, password);
           Subject subject = SecurityUtils.getSubject();
           try {
               subject.login(token);
               return ResponseBo.ok();
           } catch (UnknownAccountException | LockedAccountException | IncorrectCredentialsException e) {
               return ResponseBo.error(e.getMessage());
           } catch (AuthenticationException e) {
               return ResponseBo.error("认证失败！");
           }
       }
   
       @RequestMapping("/")
       public String redirectIndex() {
           return "redirect:/index";
       }
   
       @RequestMapping("/index")
       public String index(Model model) {
           User user = (User) SecurityUtils.getSubject().getPrincipal();
           model.addAttribute("user", user);
           return "index";
       }
   }
   ```

10. 测试：启动项目，分别访问以下路径查看效果

    - http://localhost:8080/web/
    - http://localhost:8080/web/index
    - http://localhost:8080/web/haha
    - http://localhost:8080/web




> 参考博文：https://mrbird.cc/Spring-Boot-shiro%20Authentication.html
