#### Ehcache

1. 加入 Ehcache 相关依赖

   ```xml
   <!-- shiro-ehcache -->
   <dependency>
       <groupId>org.apache.shiro</groupId>
       <artifactId>shiro-ehcache</artifactId>
       <version>1.4.0</version>
   </dependency>
   
   <!--ehchache-->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-cache</artifactId>
   </dependency>
   <!-- ehcache -->
   <dependency>
       <groupId>net.sf.ehcache</groupId>
       <artifactId>ehcache</artifactId>
       <version>2.10.4</version>
   </dependency>
   ```

2. src/main/resource/config路径下新增一个Ehcache配置——shiro-ehcache.xml：

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
            updateCheck="false">
       <diskStore path="java.io.tmpdir/Tmp_EhCache" />
       <defaultCache
               maxElementsInMemory="10000"
               eternal="false"
               timeToIdleSeconds="120"
               timeToLiveSeconds="120"
               overflowToDisk="false"
               diskPersistent="false"
               diskExpiryThreadIntervalSeconds="120" />
   
       <!-- 登录记录缓存锁定1小时 -->
       <cache name="passwordRetryCache"
              maxEntriesLocalHeap="2000"
              eternal="false"
              timeToIdleSeconds="3600"
              timeToLiveSeconds="0"
              overflowToDisk="false"
              statistics="true" />
   </ehcache>
   ```

3. ShiroConfig配置Ehcache

   ```java
   @Bean
   public EhCacheManager getEhCacheManager() {
       EhCacheManager ehCacheManager = new EhCacheManager();
       ehCacheManager.setCacheManagerConfigFile("classpath:config/shiro-ehcache.xml");
       return ehCacheManager;
   }
   ```

4. 将缓存对象注入到SecurityManager中：

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
   
       return securityManager;
   }
   ```




> 参考博文：<https://mrbird.cc/Spring-Boot-Shiro%20cache.html>