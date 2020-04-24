package com.ys.shiro.config.shiro2;

import com.ys.shiro.domain.entity.Permission;
import com.ys.shiro.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * shiro 配置类
 * 当前为shiro+jwt 配置
 * 不能指定请求方式，但不用重复访问数据库获取权限，不符合 restful 风格
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
        //设置自定义的认证
        securityManager.setRealm(customRealm());
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

    /**
     * Shiro过滤器工厂，用于设置
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        //Filter工厂，设置对应的过滤条件和跳转条件，配置为自定义支持restful风格的bean工厂
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
        // 项目启动时，从数据库中获取权限
        List<Permission> permissions = permissionService.listAllPermission();
        for (int i = 0, len = permissions.size(); i < len; i++) {
            Permission permission = permissions.get(i);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("perms[");
            // 不支持restful风格，url不可重复，重复的话，后者覆盖前者。
            for (int j = 0; j < len; j++) {
                Permission permission1 = permissions.get(j);
                if (permission.getAccessibleUrl().equals(permission1.getAccessibleUrl())) {
                    stringBuffer.append(permission.getPermissionId()).append(",");
                }
            }
            stringBuffer.deleteCharAt(stringBuffer.lastIndexOf(","));
            stringBuffer.append("]");
            accessConfigMap.put(permission.getAccessibleUrl(), stringBuffer.toString());
        }
        // 对其他所有请求进行认证[更加详细的认证，需要在认证方法上添加 @RequiresRoles, @RequiresPermissions]
        // accessConfigMap.put("/**", "authc");
        // 添加自己的过滤器并且取名为jwt
        LinkedHashMap<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("jwt", new JwtAuthenticatedFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        // 过滤链定义，从上向下顺序执行，一般将放在最为下边
        accessConfigMap.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(accessConfigMap);


        return shiroFilterFactoryBean;
    }


}
