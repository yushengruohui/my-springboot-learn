package com.ys.shiro.config.shiro;

import com.ys.shiro.service.PermissionService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro 配置类
 * 当前为常规 shiro 配置
 * 缓存sessionId于会话，不能指定请求方式，不符合 restful 风格
 */
@Configuration
public class ShiroConfig {
    @Resource
    private PermissionService permissionService;

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        // 如果当前系统没有开启动态代理则启动动态代理，shiro底层实现需要动态代理
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    @Bean
    public CustomRealm customRealm() {
        //创建自定义验证领域，实现自定义登陆认证和鉴权认证[授权]
        return new CustomRealm();
    }

    @Bean
    public SecurityManager securityManager() {
        //在securityManager中配置自定义领域
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm());
        return securityManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        // 启动shiro相关注释功能（如@RequiresRoles,@RequiresPermissions），不用shiro相关注释可以删除该bean
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * Shiro过滤器工厂，用于设置
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        //Filter工厂，设置对应的过滤条件和跳转条件
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 调用默认的安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        // 登陆url。未登陆时访问需要认证的url自动跳转到该URL
        shiroFilterFactoryBean.setLoginUrl("/auth/login");
        //验证成功后的跳转URL
        shiroFilterFactoryBean.setSuccessUrl("/auth/success");
        //认证失败后的跳转URL
        shiroFilterFactoryBean.setUnauthorizedUrl("/auth/unAuthorized");
        // 基本访问配置
        Map<String, String> accessConfigMap = new LinkedHashMap<>();
        // 设置所有人都能访问
        accessConfigMap.put("/auth/**", "anon");
        // 对其他所有请求进行认证[更加详细的认证，需要在认证方法上添加 @RequiresRoles, @RequiresPermissions]
        accessConfigMap.put("/**", "authc");
        // 去数据库中获取所有资源URL的通行权限信息
        // List<Permission> permissions = permissionService.listAllPermission();
        // for (Permission permission : permissions) {
        //     // 注意：重复的url，后者覆盖前者的值。
        //     accessConfigMap.put(permission.getAccessibleUrl(), "perms[" + permission.getPermissionId().toString() + "]");
        // }
        shiroFilterFactoryBean.setFilterChainDefinitionMap(accessConfigMap);

        return shiroFilterFactoryBean;
    }


}