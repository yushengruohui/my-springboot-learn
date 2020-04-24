package com.ys.shiro.config.shiro3;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * shiro 配置类
 * 当前为shiro+jwt+restful 配置
 */
@Configuration
public class ShiroConfig {

    /**
     * 动态代理，生成Shiro过滤器工厂、shiro 过滤器
     *
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        // 如果当前系统没有开启动态代理则启动动态代理，shiro底层实现需要动态代理
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * Shiro过滤器工厂
     * 配置过滤器链，安全管理器
     *
     * @return ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        //Filter工厂，设置对应的过滤条件和跳转条件，配置为自定义支持restful风格的bean工厂
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 调用默认的安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        // 登陆url。未登陆时访问需要认证的url自动跳转到该URL
        shiroFilterFactoryBean.setLoginUrl("/auth/login");
        // 验证成功后的跳转URL
        shiroFilterFactoryBean.setSuccessUrl("/auth/success");
        // 认证失败后的跳转URL
        shiroFilterFactoryBean.setUnauthorizedUrl("/auth/unAuthorized");
        // 基本访问配置、key为 请求url ，value为 过滤器bean的Name 。 表示某请求会被某过滤器处理
        Map<String, String> accessConfigMap = new LinkedHashMap<>();
        // 设置所有人都能访问。 anon是shiro内置过滤器AnonymousFilter的别名
        accessConfigMap.put("/auth/**", "anon");
        // 对其他所有请求进行认证[更加详细的认证，需要在认证方法上添加 @RequiresRoles, @RequiresPermissions]
        // accessConfigMap.put("/**", "authc");

        // 添加自己的过滤器并且取名为jwt
        LinkedHashMap<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("jwt", new JwtAuthenticatedFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        // 过滤链定义，从上向下顺序执行，一般将放在最为下边
        accessConfigMap.put("/**", "jwt");

        // 把过滤器链配置到shiroFilterFactoryBean
        shiroFilterFactoryBean.setFilterChainDefinitionMap(accessConfigMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        // 在shiro中配置安全管理器
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义的认证域
        List<Realm> realms = new LinkedList<>();
        realms.add(jwtRealm());
        realms.add(loginRealm());
        securityManager.setRealms(realms);
        // 关闭session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        // 设置默认安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        // 启动shiro相关注释功能（如@RequiresRoles,@RequiresPermissions），不用shiro相关注释可以删除该bean
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public JWTRealm jwtRealm() {
        //创建自定义验证领域，实现自定义登陆认证和鉴权认证[授权]
        return new JWTRealm();
    }

    @Bean
    public LoginRealm loginRealm() {
        //创建自定义验证领域，实现自定义登陆认证和鉴权认证[授权]
        return new LoginRealm();
    }

}
