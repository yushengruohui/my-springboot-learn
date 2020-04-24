package com.ys.shiro.config;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * xss过滤器
 * 清除 xss 脚本内容[不包含json]，如果想要清除 json 格式的 xss 内容，推荐修改springMVC调用ObjectMapper
 * 注意getInputStream、getReader、getParameter在一定的场景是互斥的。
 */
// @WebFilter(filterName = "xssFilter", urlPatterns = "/*", asyncSupported = true)
public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String contentType = request.getContentType();
        if (contentType == null || contentType.equals("application/x-www-form-urlencoded") || (contentType.equals("application/x-www-form-urlencoded;charset=UTF-8"))) {
            // get|delete|post 请求
            chain.doFilter(new ModifyParametersWrapper(request), response);
        } else {
            // multipart/form-data 文件上传 默认处理
            chain.doFilter(servletRequest, response);
        }
    }

    @Override
    public void destroy() {
    }

    /**
     * 继承HttpServletRequestWrapper，创建装饰类，以达到修改HttpServletRequest参数的目的
     */
    private static class ModifyParametersWrapper extends HttpServletRequestWrapper {

        public ModifyParametersWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getQueryString() {
            return cleanAllHtml(super.getQueryString());
        }

        /**
         * 获取指定参数名的值，如果有重复的参数名
         *
         * @param paramName 指定参数名
         * @return 指定参数名的值
         */
        @Override
        public String getParameter(String paramName) {
            String parameterValue = null;
            if (paramName != null && !"".equals(paramName.trim())) {
                parameterValue = cleanAllHtml(super.getParameter(paramName));
            }
            return parameterValue;
        }

        @Override
        public String getHeader(String name) {
            return cleanAllHtml(super.getHeader(name));
        }

        @Override
        public Cookie[] getCookies() {
            Cookie[] cookies = super.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    cookie.setValue(cleanAllHtml(cookie.getValue()));
                }
            }
            return cookies;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            Map<String, String[]> primary = super.getParameterMap();
            Map<String, String[]> result = new HashMap<>(16);
            for (Map.Entry<String, String[]> entry : primary.entrySet()) {
                result.put(entry.getKey(), filterEntryString(entry.getValue()));
            }
            return result;
        }

        private String[] filterEntryString(String[] value) {
            for (int i = 0; i < value.length; i++) {
                value[i] = cleanAllHtml(value[i]);
            }
            return value;
        }

        /**
         * SpringMVC 调用该方法获取参数值
         * 获取指定参数名的所有值的数组
         */
        @Override
        public String[] getParameterValues(String name) {
            String[] values = super.getParameterValues(name);
            if (values == null) {
                return null;
            }
            int count = values.length;
            String[] encodedValues = new String[count];
            for (int i = 0; i < count; i++) {
                encodedValues[i] = cleanAllHtml(values[i]);
            }
            return encodedValues;
        }

        /**
         * 防止XSS，清空所有前端标签及标签内容，保留[非html标签内]文本。
         * 使用该方法确保前端不需要传参前端标签
         *
         * @param paramValue 入参值
         * @return java.lang.String
         */
        private String cleanAllHtml(String paramValue) {
            if (paramValue != null) {
                return Jsoup.clean(paramValue, Whitelist.none());
            }
            return null;
        }

        /**
         * 防止XSS， 只保留基本的html标签及内容、非html内容的文本，禁止加载js、img
         *
         * @param paramValue 入参值
         * @return java.lang.String
         */
        private String cleanBase(String paramValue) {
            if (paramValue != null) {
                return Jsoup.clean(paramValue, Whitelist.basic());
            }
            return null;
        }

        /**
         * 防止XSS， 只保留一些html标签和基本样式及内容、非html内容的文本，禁止加载js，适用于一般富文本
         *
         * @param paramValue 入参值
         * @return java.lang.String
         */
        private String cleanRelaxed(String paramValue) {
            if (paramValue != null) {
                return Jsoup.clean(paramValue, Whitelist.relaxed());
            }
            return null;
        }
    }
}