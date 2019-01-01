package com.ordust.xss;

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
