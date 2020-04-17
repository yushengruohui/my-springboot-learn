package com.ys.security.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 登录拦截全局配置
 */
@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Resource(name = "urlAuthenticationEntryPoint")
    private UrlAuthenticationEntryPoint authenticationEntryPoint;

    @Resource(name = "urlAuthenticationSuccessHandler")
    private UrlAuthenticationSuccessHandler authenticationSuccessHandler;

    @Resource(name = "urlAuthenticationFailureHandler")
    private UrlAuthenticationFailureHandler authenticationFailureHandler;

    @Resource(name = "urlAccessDeniedHandler")
    private UrlAccessDeniedHandler accessDeniedHandler;

    @Resource(name = "urlLogoutSuccessHandler")
    private UrlLogoutSuccessHandler logoutSuccessHandler;

    @Resource(name = "selfAuthenticationProvider")
    private SelfAuthenticationProvider authenticationProvider;

    @Resource(name = "selfFilterInvocationSecurityMetadataSource")
    private SelfFilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    @Resource(name = "selfAccessDecisionManager")
    private SelfAccessDecisionManager accessDecisionManager;

    @Resource(name = "myAuthenticationDetailsSource")
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

    @Resource(name = "jwtAuthorizationTokenFilter")
    private JwtAuthorizationTokenFilter authorizationTokenFilter;

    /**
     * 跨域配置，会自动配置到 spring-security 的过滤器链中
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // 允许 携带以下域名的请求 跨域访问，通常设置为前端的域名或者*
        List<String> allowedOriginsUrl = new ArrayList<>();
        allowedOriginsUrl.add("localhost");
        // allowedOriginsUrl.add("https://localhost:8080");
        allowedOriginsUrl.add("127.0.0.1");
        // allowedOriginsUrl.add("https://example.com");

        CorsConfiguration config = new CorsConfiguration();
        // 允许携带cookie
        config.setAllowCredentials(true);
        // 设置允许跨域访问的 URL
        config.setAllowedOrigins(allowedOriginsUrl);
        config.setAllowedHeaders(Arrays.asList("X-XSRF-TOKEN", "Authorization", JwtTokenUtils.TOKEN_KEY));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTION"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Override
    public void configure(WebSecurity web) {
        //所有人都能访问，不进入安全框架的请求，常用于前后端未分离时放行静态资源url
        String[] ignoreUrl = {
                "/resource/**/*.jpg",
                "/resource/**/*.mp4"
        };
        web.ignoring().antMatchers(ignoreUrl);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        //自定义登录认证过程
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启跨域请求配置
        http.cors();
        // csrf 验证[跨站请求伪造攻击]开启后，所有 [put|post|delete|patch] 请求的 header 中都要带有 X-XSRF-TOKEN 的 cookie 才能访问
        http.csrf().ignoringAntMatchers("/auth/**").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        // 内部系统、测试则可以关闭csrf验证
        // http.csrf().disable();

        // JwtToken验证，成功则生成authentication身份信息，跳过登陆认证流程，直接到权限认证流程
        http.addFilterBefore(authorizationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 未登录，但当前请求url需要权限认证（授权认证）时的处理：返回状态码401
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

        // 登陆了，但无权访问时的处理：返回状态码403
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);

        // 开启自动配置的登录功能
        http.formLogin()
                // .loginPage("/login") //登录页面(前后端不分离)
                .loginProcessingUrl("/auth/login") //自定义登录请求路径(post)
                .usernameParameter("username").passwordParameter("password") //自定义登录用户名密码属性名,默认为username和password
                // .successForwardUrl("/index") //登录成功后的url(post,前后端不分离)
                // .failureForwardUrl("/error") //登录失败后的url(post,前后端不分离)
                .successHandler(authenticationSuccessHandler) //验证成功处理器(前后端分离)：生成token及响应状态码200
                .failureHandler(authenticationFailureHandler) //验证失败处理器(前后端分离)：返回状态码402
                .authenticationDetailsSource(authenticationDetailsSource) //身份验证详细信息源(登录验证中增加额外字段)
                .permitAll();

        // url权限认证处理
        http.authorizeRequests()
                // .antMatchers("/security/user/**").hasRole("ADMIN") //需要ADMIN角色才可以访问
                // .antMatchers("/connect").hasIpAddress("127.0.0.1") //只有ip[127.0.0.1]可以访问'/connect'接口
                .antMatchers("/auth/login").permitAll()
                .anyRequest() //其他任何请求
                .authenticated() //都需要权限认证
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(filterInvocationSecurityMetadataSource); //动态获取url权限配置
                        o.setAccessDecisionManager(accessDecisionManager); //权限判断
                        return o;
                    }
                });

        // 将session策略设置为无状态的,通过token进行登录认证
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 开启自动配置的注销功能
        http.logout()
                //自定义注销请求路径
                .logoutUrl("/auth/logout")
                //注销成功后的url(前后端不分离)
                // .logoutSuccessUrl("/bye")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                //注销成功处理器(前后端分离)：返回状态码200
                .logoutSuccessHandler(logoutSuccessHandler);
    }
}
