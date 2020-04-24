package com.ys.shiro.config.shiro2;


import com.ys.shiro.domain.entity.Permission;
import com.ys.shiro.domain.entity.User;
import com.ys.shiro.service.UserService;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * 自定义Realm用于查询用户的角色和权限信息并保存到权限管理器
 */
public class CustomRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Override
    public Class<?> getAuthenticationTokenClass() {
        // 此realm只支持MyJwtToken
        return MyJwtToken.class;
    }

    /**
     * 获取身份验证信息，用于登陆认证
     *
     * @param authenticationToken 登陆口令
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        // token登陆处理
        MyJwtToken myJwtToken = (MyJwtToken) authenticationToken;
        String token = myJwtToken.getToken();
        if (token != null) {
            return new SimpleAuthenticationInfo(token, token, getName());
        }
        //获取用户信息
        String loginAccount = authenticationToken.getPrincipal().toString();
        if (loginAccount == null || loginAccount.trim().equals("")) {
            return null;
        }
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
     * 执行该方法代表用户已经通过登陆认证
     * 注意：每次访问需要权限认证的资源都会调用该方法，所以不建议每次都去数据库中查询
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登陆账号
        String loginAccount = (String) principalCollection.getPrimaryPrincipal();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Cookie cookie = WebUtils.getCookie(request, JwtTokenUtils.TOKEN_KEY);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if (cookie != null) {
            String token = cookie.getValue();
            Claims claims = JwtTokenUtils.getClaimsFromToken(token);
            List<Permission> permissions = JwtTokenUtils.getPermissionByClaims(claims);
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) claims.get(JwtTokenUtils.CLAIM_KEY_ROLE);
            simpleAuthorizationInfo.addRoles(roles);
            if (permissions != null) {
                permissions.forEach(permission -> {
                    // 权限为[url :: method] 如: /user/money :: POST
                    simpleAuthorizationInfo.addStringPermission(permission.getAccessibleUrl() + " :: " + permission.getAccessibleMethod().toUpperCase());
                });
            }
        }
        System.out.println(simpleAuthorizationInfo.toString());
        return simpleAuthorizationInfo;
    }


}
