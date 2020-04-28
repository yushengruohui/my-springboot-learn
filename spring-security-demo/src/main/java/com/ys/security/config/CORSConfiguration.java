package com.ys.security.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * springboot项目解决跨域问题
 */
@Configuration
public class CORSConfiguration {

    @Bean
    public FilterRegistrationBean<myCORSFilter> myCORSFilterFilterRegistrationBean() {
        FilterRegistrationBean<myCORSFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new myCORSFilter());
        // 先配置跨域请求
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<>();
        //excludes用于配置不需要参数过滤的请求url
        // initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");
        //isIncludeRichText主要用于设置富文本内容是否需要过滤
        // initParameters.put("isIncludeRichText", "true");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }

    private static class myCORSFilter implements Filter {
        @Override
        public void init(FilterConfig filterConfig) {
        }

        @Override
        public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
            HttpServletResponse response = (HttpServletResponse) resp;
            HttpServletRequest request = (HttpServletRequest) req;
            // 处理简单请求
            // 跨域请求默认不携带cookie,如果要携带cookie，需要设置下边2个响应头
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));// 必选，所有有效的跨域响应都必须包含这个请求头, 没有的话会导致跨域请求失败
            response.setHeader("Access-Control-Allow-Credentials", "true");//可选，此处设置为true,对应前端 xhr.withCredentials = true;
            response.setHeader("Access-Control-Allow-Methods", "GET,OPTIONS,POST,PUT,DELETE");// 必选
            response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,LastModified,X-XSRF-TOKEN,Authorization");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("XDomainRequestAllowed", "1");
            chain.doFilter(req, resp);
        }


        @Override
        public void destroy() {
        }
    }
}
