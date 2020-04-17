package com.ys.security.config.security;

import com.ys.security.domain.entity.Permission;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * JwtToken解析并生成 authentication 身份信息 过滤器
 */
@Component("jwtAuthorizationTokenFilter")
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    /**
     * 解析token并生成authentication身份信息
     * 当前 token 存于 httpOnly cookie中
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Cookie cookie = WebUtils.getCookie(request, JwtTokenUtils.TOKEN_KEY);
        String token = cookie != null ? cookie.getValue() : null;
        if (null != token && !"".equals(token.trim())) {
            // 通过密钥解析 token ，获取声明变量对象
            if (!JwtTokenUtils.isTokenExpired(token)) {
                // 获取用户权限信息
                Claims claims = JwtTokenUtils.getClaimsFromToken(token);
                String username = claims.get(JwtTokenUtils.CLAIM_KEY_USERNAME, String.class);
                String account = claims.getSubject();
                String userId = claims.get(JwtTokenUtils.CLAIM_KEY_ID, String.class);
                @SuppressWarnings("unchecked")
                List<String> roleNameList = claims.get(JwtTokenUtils.CLAIM_KEY_ROLE, List.class);
                @SuppressWarnings("unchecked")
                List<LinkedHashMap<String, Object>> jwtPermissionList = claims.get(JwtTokenUtils.CLAIM_KEY_AUTH, List.class);
                List<Permission> permissionList = new ArrayList<>();
                for (LinkedHashMap<String, Object> permissionMap : jwtPermissionList) {
                    permissionList.add(new Permission(Long.valueOf(permissionMap.get("permissionId").toString()), permissionMap.get("description").toString(), permissionMap.get("accessibleMethod").toString(), permissionMap.get("accessibleUrl").toString()));
                }
                SelfUserDetails userInfo = new SelfUserDetails();
                userInfo.setUserId(userId);
                userInfo.setUsername(username);
                userInfo.setAccount(account);
                userInfo.setRoleNameList(AuthorityUtils.commaSeparatedStringToAuthorityList(roleNameList.toString()));
                userInfo.setPermissions(permissionList);

                // 生成 authentication 身份信息
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities());
                // 一旦在 SecurityContextHolder 生成验证信息，不走登陆认证流程，直接到访问权限验证[鉴权认证]流程
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // 不带token，正常验证，进行下一步过滤器链处理 ，获取 认证信息
        chain.doFilter(request, response);
    }
}
