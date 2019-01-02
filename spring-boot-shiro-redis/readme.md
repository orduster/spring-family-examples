### Spring Boot Shiro 使用缓存

> 以下操作前提为shiro 已经配置好权限控制

- 在 Shiro 中加入缓存可以使相关操作尽可能快，避免频繁的访问数据库获取权限信息，对于一个用户来说，其权限在短时间之内基本不会改变。Shiro 提供了 Cache 的抽象，并没有直接提供相应的实现。在 Shiro 中可以集成常用的缓存实现，这里实现基于 Redis 和 Ehcache 缓存的实现



#### Redis

1. 引入 Redis 的依赖

   ```xml
   <!--shiro-spring-->
   <dependency>
       <groupId>org.apache.shiro</groupId>
       <artifactId>shiro-spring</artifactId>
       <version>1.4.0</version>
   </dependency>
   <!-- shiro-redis -->
   <dependency>
       <groupId>org.crazycake</groupId>
       <artifactId>shiro-redis</artifactId>
       <version>3.2.0</version>
   </dependency>
   ```

2. 配置 Redis ：在 application.yaml 配置文件中加入以下配置：

   ```yaml
   spring:
     redis:
       host: localhost
       port: 6379
       pool:
         max-active: 8
         max-wait: -1
         max-idle: 8
         min-idle: 0
       timeout: 0
   ```

3. 接着在 ShiroConfig 中配置 Redis ，将配置好的 RedisManager 注入到 RedisCacheManager

   ```java
   //配置shiro
   public RedisManager redisManager() {
       return new RedisManager();
   }
   
   public RedisCacheManager cacheManager() {
       RedisCacheManager redisCacheManager = new RedisCacheManager();
       redisCacheManager.setRedisManager(redisManager());
       return redisCacheManager;
   }
   ```

4. ，将 RedisCacheManager 加入 SecurityManager

   ```java
   @Bean
   public SecurityManager securityManager() {
       //配置 SecurityManager，并注入 shiroRealm，注意不是DefaultSecurityManager
       DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
       securityManager.setRealm(shiroRealm());
   
       //设置 cookie 管理对象
       securityManager.setRememberMeManager(rememberMeManager());
   
       //加入 缓存对象
       securityManager.setCacheManager(cacheManager());
   
       return securityManager;
   }
   ```



> 参考博文：https://mrbird.cc/Spring-Boot-Shiro%20cache.html