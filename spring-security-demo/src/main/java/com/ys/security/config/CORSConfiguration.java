package com.ys.security.config;

import com.ys.security.config.security.JwtTokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * springboot项目解决跨域问题
 */
@Configuration
public class CORSConfiguration {

    @Bean
    public CorsFilter corsFilter() {
        //添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //允许来自以下域[http://example.com]的请求访问,不推荐为*，否则cookie就无法使用了
        config.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://127.0.0.1:8080", "https://localhost:8080", "http://127.0.0.1:8080"));
        //是否允许传输 Cookie 信息
        config.setAllowCredentials(true);
        //允许的请求方式
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTION"));
        //允许的头信息
        config.setAllowedHeaders(Arrays.asList(JwtTokenUtils.TOKEN_KEY, "Origin", "X-Requested-With", "Content-Type", "Accept", "LastModified", "X-XSRF-TOKEN"));
        // 配置有效时长，单位:s，数据类型:Long，当前设置时长为1小时
        config.setMaxAge(3600L);

        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        //返回新的CorsFilter.
        return new CorsFilter(configSource);
    }

}
