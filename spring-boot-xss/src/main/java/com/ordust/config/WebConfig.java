package com.ordust.config;

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