package com.ys.shiro.config.shiro3;


import com.ys.shiro.domain.entity.Permission;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * 自定义JWT的Realm
 */
public class JWTRealm extends AuthorizingRealm {

    public static final String SEPARATION = "::";

    @Override
    public Class<?> getAuthenticationTokenClass() {
        // 此realm只支持MyJwtToken
        return MyJwtToken.class;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof MyJwtToken;
    }

    /**
     * 获取身份验证信息，用于登陆认证[身份验证]
     *
     * @param authenticationToken 登陆口令
     * @return AuthenticationInfo 身份验证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        // token登陆处理
        MyJwtToken myJwtToken = (MyJwtToken) authenticationToken;
        Object token = myJwtToken.getCredentials();
        if (token == null || token.toString().trim().equals("")) {
            throw new UnknownAccountException("空账号");
        }
        String loginIp = myJwtToken.getIpAddr();
        // 检测用户登陆IP
        final String audienceInToken = JwtTokenUtils.getClaimsFromToken(token.toString()).getAudience();
        if (audienceInToken == null || audienceInToken.trim().equals("") || loginIp == null || loginIp.trim().equals("") || !loginIp.equals(audienceInToken)) {
            // ip地址不一致，拒接访问
            throw new IncorrectCredentialsException("错误凭证：异地登陆");
        }
        if (JwtTokenUtils.isTokenExpired(token.toString())) {
            throw new ExpiredCredentialsException("过期凭证");
        }
        String loginAccount = myJwtToken.getPrincipal().toString();
        return new SimpleAuthenticationInfo(loginAccount, token, getName());
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
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Cookie cookie = WebUtils.getCookie(request, JwtTokenUtils.TOKEN_KEY);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if (cookie != null) {
            String token = cookie.getValue();
            Claims claims = JwtTokenUtils.getClaimsFromToken(token);
            List<Permission> permissions = JwtTokenUtils.getPermissionByClaims(claims);
            // 获取用户角色
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) claims.get(JwtTokenUtils.CLAIM_KEY_ROLE);
            simpleAuthorizationInfo.addRoles(roles);
            if (permissions != null) {
                permissions.forEach(permission -> {
                    // 权限为[url::method] 如: /user/money :: POST
                    simpleAuthorizationInfo.addStringPermission(permission.getAccessibleUrl() + SEPARATION + permission.getAccessibleMethod().toUpperCase());
                });
            }
        }
        return simpleAuthorizationInfo;
    }


}
