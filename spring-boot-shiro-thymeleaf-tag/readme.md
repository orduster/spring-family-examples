### Spring Boot Thymeleaf中使用Shiro标签

- 在 Spring-Boot-shiro权限控制中，当用户没有相应的权限访问资源时，设置的是直接跳转到 403 页面，但是通常的做法是只显示当前用户拥有访问权限的资源链接。配合 Thymeleaf 中的 Shiro 标签可以很简单的实现这个目标。
- 实际上Thymeleaf官方并没有提供Shiro的标签，我们需要引入第三方实现



1. 引入thymeleaf-extras-shiro

   ```xml
   <!--thymeleaf-extras-shiro-->
   <dependency>
       <groupId>com.github.theborakompanioni</groupId>
       <artifactId>thymeleaf-extras-shiro</artifactId>
       <version>2.0.0</version>
   </dependency>
   ```

2. ShiroConfig 配置，引入依赖后，需要在ShiroConfig中配置该方言标签：

   ```java
   /*配置方言标签*/
   @Bean
   public ShiroDialect shiroDialect() {
       return new ShiroDialect();
   }
   ```

3. 更改 index.html ，用来测试 Shiro 标签的使用

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
   </body>
   </html>
   ```

   > 值得注意的是，在html页面中使用Shiro标签需要给html标签添加 `xmlns:shiro="http://www.pollix.at/thymeleaf/shiro"`



#### 更多标签

- 以下示例显示如何在Thymeleaf模板中集成标记。 这些是Apache Shiro文档的  [JSP / GSP 标记库](http://shiro.apache.org/web.html#Web-JSP%252FGSPTagLibrary) 部分中给出的示例的所有实现。标签可以用属性或元素表示法编写：

  - Attribute

    ```html
    <p shiro:anyTag>
      Goodbye cruel World!
    </p>
    ```

  - Element

    ```html
    <shiro:anyTag>
      <p>Hello World!</p>
    </shiro:anyTag>
    ```

  - The `guest` tag

    ```html
    <p shiro:guest="">
      Please <a href="login.html">Login</a>
    </p>
    ```

  - The `user` tag

    ```html
    <p shiro:user="">
      Welcome back John! Not John? Click <a href="login.html">here<a> to login.
    </p>
    ```

  - The `authenticated` tag

    ```html
    <a shiro:authenticated="" href="updateAccount.html">Update your contact information</a>
    ```

  - The `notAuthenticated` tag

    ```html
    <p shiro:notAuthenticated="">
      Please <a href="login.html">login</a> in order to update your credit card information.
    </p>
    ```

  - The `principal` tag

    ```html
    <p>Hello, <span shiro:principal=""></span>, how are you today?</p>
    ```

    or

    ```html
    <p>Hello, <shiro:principal/>, how are you today?</p>
    ```

  - The `hasRole` tag

    ```html
    <a shiro:hasRole="administrator" href="admin.html">Administer the system</a>
    ```

  - The `lacksRole` tag

    ```html
    <p shiro:lacksRole="administrator">
      Sorry, you are not allowed to administer the system.
    </p>
    ```

  - The `hasAllRoles` tag

    ```html
    <p shiro:hasAllRoles="developer, project manager">
      You are a developer and a project manager.
    </p>
    ```

  - The `hasAnyRoles` tag

    ```html
    <p shiro:hasAnyRoles="developer, project manager, administrator">
      You are a developer, project manager, or administrator.
    </p>
    ```

  - The `hasPermission` tag

    ```html
    <a shiro:hasPermission="user:create" href="createUser.html">Create a new User</a>
    ```

  - The `lacksPermission` tag

    ```html
    <p shiro:lacksPermission="user:delete">
      Sorry, you are not allowed to delete user accounts.
    </p>
    ```

  - The `hasAllPermissions` tag

    ```html
    <p shiro:hasAllPermissions="user:create, user:delete">
      You can create and delete users.
    </p>
    ```

  - The `hasAnyPermissions` tag

    ```html
    <p shiro:hasAnyPermissions="user:create, user:delete">
      You can create or delete users.
    </p>
    ```



> 参考博文：https://mrbird.cc/Spring-Boot-Themeleaf%20Shiro%20tag.html