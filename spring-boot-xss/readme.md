[TOC]

# Jsoup 防止富文本 XSS 攻击



### XSS

- XSS（Cross Site Scripting）攻击全称为**跨站脚本攻击**，是为了不和层叠样式表（Cascading Style Sheets, CSS）的缩写混淆，故将跨站脚本攻击缩写为 XSS。
- XSS 是一种 WEB 应用中的计算机安全漏洞，它允许恶意web用户将代码植入到提供给其它用户使用的页面中。
- 富文本编辑器提交的内容会带有 HTML 标签，在这种情况下就不能对 HTML 标签进行转义，所以为了防止 XSS 攻击，过滤掉其中的 JS 代码，在 Java 中 使用 **Jsoup** 实现这一目的。



### Jsoup 组件

1. Jsoup 使用标签白名单的机制来防止 XSS 攻击，没有在白名单中的标签都会被过滤掉；假设白名单规定只能存在的标签为 `<span>` ，那么在一段 HTML 代码中，除了 `<span>` 标签之外，其他标签都会被清除掉，只保留被标签所包裹的内容。

2. Jsoup内置了几种常见的白名单供我们选择：

   | 白名单对象      | 标签                                                         | 说明                                              |
   | --------------- | ------------------------------------------------------------ | ------------------------------------------------- |
   | none            | 无                                                           | 只保留标签内文本内容                              |
   | simpleText      | b,em,i,strong,u                                              | 简单的文本标签                                    |
   | basic           | a,b,blockquote,br,cite,code,dd, dl,dt,em,i,li,ol,p,pre,q,small,span, strike,strong,sub,sup,u,ul | 基本使用的标签                                    |
   | basicWithImages | basic 的基础上添加了 img 标签 及 img 标签的 src,align,alt,height,width,title 属性 | 基本使用的加上 img 标签                           |
   | relaxed         | a,b,blockquote,br,caption,cite, code,col,colgroup,dd,div,dl,dt, em,h1,h2,h3,h4,h5,h6,i,img,li, ol,p,pre,q,small,span,strike,strong, sub,sup,table,tbody,td,tfoot,th,thead,tr,u,ul | 在 basicWithImages 的基础上又增加了一部分部分标签 |
   





### 开始使用 Jsoup

1. 新建一个简单的 Springboot 项目，在 pom.xml 中加入以下依赖：

   ```xml
   <!-- 使用 Jsoup 过滤 XSS 攻击 -->
   <dependency>
       <groupId>org.jsoup</groupId>
       <artifactId>jsoup</artifactId>
       <version>1.11.3</version>
   </dependency>
   
   <!-- 使用 StringUtils -->
   <dependency>
       <groupId>org.apache.commons</groupId>
       <artifactId>commons-lang3</artifactId>
       <version>3.8.1</version>
   </dependency>
   ```

2. 创建一个工具类过滤非法标签 — **JsoupUtil**

   ```java
   import org.jsoup.Jsoup;
   import org.jsoup.nodes.Document;
   import org.jsoup.safety.Whitelist;
   
   /**
    * XSS 过滤工具类
    */
   public class JsoupUtil {
       //这里白名单采用 basicWithImages
       private static final Whitelist WHITELIST = Whitelist.basicWithImages();
   
       //配置过滤化参数，不对代码进行格式化
       private static final Document.OutputSettings OUTPUT_SETTINGS = new Document.OutputSettings().prettyPrint(false);
   
       static {
           //富文本编辑时一些样式是采用 style 来进行实现的，如红色字体 style="color:red;" 所以需要给所有(all)标签添加 style 属性
           WHITELIST.addAttributes(":all", "style");
       }
   
       public static String clean(String content) {
           return Jsoup.clean(content, "", WHITELIST, OUTPUT_SETTINGS);
       }
   
   }
   ```

3.  创建一个类继承 HttpServletRequestWrapper 类实现对 HTTP 请求中的参数进行过滤（实现 XSS 过滤的关键），在其内重写了getParameter，getParameterValues，getHeader等方法 — **XssHttpServletRequestWrapper**

   ```java
   import com.ordust.util.JsoupUtil;
   import org.apache.catalina.servlet4preview.http.HttpServletRequestWrapper;
   import org.apache.commons.lang3.StringUtils;
   
   import javax.servlet.http.HttpServletRequest;
   
   
   /**
    * Jsoup过滤http请求，防止Xss攻击
    */
   public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
   
       private HttpServletRequest orgRequest = null;
       private boolean isIncludeRichText = false;
   
       public XssHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText) {
           super(request);
           this.orgRequest = request;
           this.isIncludeRichText = isIncludeRichText;
       }
   
       /**
        * 覆盖 getParameter 方法，将参数名和参数值都做 xss 过滤，如果需要获取原来的值，则通过 super.getParameterValues(name)
        * 来获取，getParameterNames, getParameterValues 和 getParameterMap 也可能需要覆盖
        */
       @Override
       public String getParameter(String name) {
           if (("content".equals(name) || name.endsWith("WithHtml")) && !isIncludeRichText) {
               return super.getParameter(name);
           }
           name = JsoupUtil.clean(name);
           String value = super.getParameter(name);
           if (StringUtils.isNotBlank(value)) {
               value = JsoupUtil.clean(value);
           }
           return value;
       }
   
       @Override
       public String[] getParameterValues(String name) {
           String[] arr = super.getParameterValues(name);
           if (arr != null) {
               for (int i = 0; i < arr.length; i++) {
                   arr[i] = JsoupUtil.clean(arr[i]);
               }
           }
           return arr;
       }
   
       /**
        * 覆盖 getHeader 方法，将参数名和参数值都做 xss 过滤，如果需要获取原始的值，则通过 super.getHeader(name) 来获取
        * getHeaderNames 也可能需要覆盖
        */
       @Override
       public String getHeader(String name) {
           name = JsoupUtil.clean(name);
           String value = super.getHeader(name);
           if (StringUtils.isNoneBlank(value)) {
               value = JsoupUtil.clean(value);
           }
           return value;
       }
   
       /**
        * 获取原始的 request
        */
       public HttpServletRequest getOrgRequest() {
           return orgRequest;
       }
   
       /**
        * 获取原始的 request 的静态方法
        */
       public static HttpServletRequest getOrgRequest(HttpServletRequest request) {
           if (request instanceof XssHttpServletRequestWrapper) {
               return ((XssHttpServletRequestWrapper) request).getOrgRequest();
           }
           return request;
       }
   
   }
   ```

4. 创建 XSS 过滤器 — XssFilter

   - XssFilter 是过滤 XSS 请求的入口，在这里通过上面的 XssHttpServletRequestWrapper 对 HttpServletRequest 进行封装，重写其中的 `filterChain.doFilter(xssRequest, response)` 方法，保证后续代码执行 `request.getParameter`，`request.getParameterValues`，`request.getHeader` 时，获取到的内容都经过了标签过滤。

     ```java
     import org.apache.commons.lang3.BooleanUtils;
     import org.apache.commons.lang3.StringUtils;
     import org.slf4j.Logger;
     import org.slf4j.LoggerFactory;
     
     import javax.servlet.*;
     import javax.servlet.http.HttpServletRequest;
     import javax.servlet.http.HttpServletResponse;
     import java.io.IOException;
     import java.util.ArrayList;
     import java.util.List;
     import java.util.regex.Matcher;
     import java.util.regex.Pattern;
     
     /**
      * XSS 攻击拦截器
      */
     public class XssFilter implements Filter {
     
         private static Logger logger = LoggerFactory.getLogger(XssFilter.class);
     
         //是否过滤富文本内容
         private static boolean IS_INCLUDE_RICH_TEXT = false;
     
         public List<String> excludes = new ArrayList<>();
     
         @Override
         public void init(FilterConfig filterConfig) throws ServletException {
             logger.info("-------------- xss filter init --------------");
             String isIncludeRichText = filterConfig.getInitParameter("isIncludeRichText");
             if (StringUtils.isNoneBlank(isIncludeRichText)) {
                 IS_INCLUDE_RICH_TEXT = BooleanUtils.toBoolean(isIncludeRichText);
             }
             String temp = filterConfig.getInitParameter("excludes");
             if (temp != null) {
                 String[] url = temp.split(",");
                 if (url != null) {
                     for (int i = 0, j = url.length; i < j; i++) {
                         excludes.add(url[i]);
                     }
                 }
             }
         }
     
         @Override
         public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
     
             HttpServletRequest req = (HttpServletRequest) request;
             HttpServletResponse resp = (HttpServletResponse) response;
             if (handleExcludeURL(req, resp)) {
                 chain.doFilter(request, response);
                 return;
             }
             XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request, IS_INCLUDE_RICH_TEXT);
             chain.doFilter(xssRequest, response);
         }
     
         @Override
         public void destroy() {
     
         }
     
         private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
             if (excludes == null || excludes.isEmpty()) {
                 return false;
             }
             String url = request.getServletPath();
             for (String pattern : excludes) {
                 Pattern compile = Pattern.compile("^" + pattern);
                 Matcher matcher = compile.matcher(url);
                 if (matcher.find()) {
                     return true;
                 }
             }
             return false;
         }
     }
     ```

5. 注册 XssFilter

   - 将 XssFilter 注入进 spring boot 容器中，使其生效

     ```java
     import com.ordust.xss.XssFilter;
     import org.springframework.boot.web.servlet.FilterRegistrationBean;
     import org.springframework.context.annotation.Bean;
     import org.springframework.context.annotation.Configuration;
     
     import javax.servlet.Filter;
     import java.util.HashMap;
     
     @Configuration
     public class WebConfig {
     
         @Bean
         public FilterRegistrationBean xssFilterRegistrationBean() {
             FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
             filterFilterRegistrationBean.setFilter(new XssFilter());
             filterFilterRegistrationBean.setOrder(1);
             filterFilterRegistrationBean.setEnabled(true);
             filterFilterRegistrationBean.addUrlPatterns("/*");
             HashMap<String, String> initParameters = new HashMap<>();
             initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");
             initParameters.put("isIncludeRichText", "true");
             filterFilterRegistrationBean.setInitParameters(initParameters);
             return filterFilterRegistrationBean;
         }
     }
     ```



> 参考博客：
>
> 1. https://blog.csdn.net/u014411966/article/details/78164752
> 2. https://www.jianshu.com/p/32abc12a175a?nomobile=yes
> 3. https://mrbird.cc/Jsoup%20XSS.html



