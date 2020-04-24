package com.ys.shiro.config.shiro;


import com.ys.shiro.domain.entity.Permission;
import com.ys.shiro.domain.entity.Role;
import com.ys.shiro.domain.entity.User;
import com.ys.shiro.service.RoleService;
import com.ys.shiro.service.UserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.List;

/**
 * 自定义Realm用于查询用户的角色和权限信息并保存到权限管理器
 */
public class CustomRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    /**
     * 获取身份验证信息，用于登陆认证
     *
     * @param authenticationToken 登陆口令
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String loginAccount = authenticationToken.getPrincipal().toString();
        User userQO = new User();
        userQO.setAccount(loginAccount);
        User user = userService.getUser(userQO);
        if (user == null) {
            //这里返回后会报出对应异常UnknownAccountException
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息。 getName()是AuthorizingRealm的方法，用于获取realm对象的name
            return new SimpleAuthenticationInfo(loginAccount, user.getPassword(), getName());
        }
    }

    /**
     * 获取登陆用户权限，用于鉴权认证[授权认证]
     * 注意：每次访问需要权限认证的资源都会调用该方法，所以不建议每次都去数据库中查询
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登陆账号
        String loginAccount = (String) principalCollection.getPrimaryPrincipal();
        User userQO = new User();
        userQO.setAccount(loginAccount);
        //根据用户名去数据库查询用户信息
        User user = userService.getUser(userQO);
        if (user == null) {
            return null;
        }
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 去数据库中获取当前登陆用户的角色
        List<Role> roleList = userService.listUserRoleByUserId(user.getUserId());
        if (roleList != null) {
            for (Role role : roleList) {
                simpleAuthorizationInfo.addRole(role.getRoleName());
                // 去数据库中获取当前角色的权限
                List<Permission> permissionList = roleService.listPermissionByRoleId(role.getRoleId());
                if (permissionList != null) {
                    for (Permission permission : permissionList) {
                        // 默认只能添加 url ，不能指定请求方法
                        simpleAuthorizationInfo.addStringPermission(permission.getPermissionId().toString());
                    }
                }
            }
        }
        return simpleAuthorizationInfo;
    }


}
