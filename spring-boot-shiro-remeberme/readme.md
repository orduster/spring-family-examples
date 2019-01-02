[TOC]

### Spring Boot Shiro Remember Me

> 以下的更改都是基于完成用户认证的情况下

1. 更改 ShiroConfig：在原有的基础上加上以下代码：

   ```java
   /**
    * cookie 管理器
    */
   public CookieRememberMeManager rememberMeManager() {
       CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
       cookieRememberMeManager.setCookie(rememberMeCookie());
       //rememberMe cookie 加密的密钥，通过AESUtil 获取
       cookieRememberMeManager.setCipherKey(Base64.decode("L7RioUULEFhRyxM7a2R/Yg=="));
       return cookieRememberMeManager;
   }
   
   /**
    * cookie 对象
    */
   public SimpleCookie rememberMeCookie() {
       //设置 cookie 名称，对应登录界面（login.html）<input type="checkbox" name="rememberMe"/>
       SimpleCookie cookie = new SimpleCookie("rememberMe");
       //设置cookie的过期时间 这里为一天
       cookie.setMaxAge(60 * 60 * 24);
       return cookie;
   }
   ```

2. 将 Cookie 管理器设置到 SecurityManager 中

   ```java
   @Bean
   public SecurityManager securityManager() {
       //配置 SecurityManager，并注入 shiroRealm，注意不是DefaultSecurityManager
       DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
       securityManager.setRealm(shiroRealm());
   
       //设置 cookie 管理器
       securityManager.setRememberMeManager(rememberMeManager());
       return securityManager;
   }
   ```

3. 修改权限配置，将 ShiroFilterFactoryBean 中 的 `filterChainDefinitionMap.put("/**", "authc")` 更改为 `filterChainDefinitionMap.put("/**", "user")` ，user 指用户认证通过或者配置了 Remember Me记住用户登录状态后可访问。

   ```java
   @Bean
   public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
       // ......省略了之前重复代码
       
       //除以上所有 url 都必须通过认证才可以访问，未通过认证自动访问 loginurl
       //filterChainDefinitionMap.put("/**", "authc");
       //user指的是用户认证通过或者配置了Remember Me记住用户登录状态后可访问。
       filterChainDefinitionMap.put("/**", "user");
       
       shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
   
       return shiroFilterFactoryBean;
   }
   ```

4. 更改登录界面 login.html ，加入 Remember Me checkbox：

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
           <p><input type="checkbox" name="rememberMe"/>记住我</p>
           <button onclick="login()">登录</button>
       </div>
   </div>
   <script th:src="@{/js/jquery-1.11.1.min.js}"></script>
   <script th:inline="javascript">
       var ctx = [[@{/}]];
       function login() {
           var username = $("input[name='username']").val();
           var password = $("input[name='password']").val();
           var rememberMe = $("input[name='rememberMe']").is(':checked');
           $.ajax({
               type: "post",
               url: ctx + "login",
               data: {"username": username, "password": password, "rememberMe": rememberMe},
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

5. 更改控制层 LoginController 的 login 方法

   ```java
   @PostMapping("/login")
   @ResponseBody
   public ResponseBo login(String username, String password, Boolean rememberMe) {
       //对密码进行加密
       password = MD5Utils.encrypt(username, password);
       UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
       Subject subject = SecurityUtils.getSubject();
       try {
           subject.login(token);
           return ResponseBo.ok();
       } catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
           return ResponseBo.error(e.getMessage());
       } catch (AuthenticationException e) {
           return ResponseBo.error("认证失败！");
       }
   }
   ```

> 参考博文：https://mrbird.cc/Spring-Boot-Shiro%20Remember-Me.html


