package com.ys.security.config.security;

import com.ys.security.domain.entity.Permission;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * token 工具
 *
 * @autho yusheng
 * @create-date 2020-03-22 17:51
 **/
public class JwtTokenUtils {
    public static final String TOKEN_KEY = "cookie-token";
    /**
     * 账号
     */
    private static final String CLAIM_KEY_ACCOUNT = "account";
    /**
     * token生成时间，纯数字时间戳，long类型
     */
    private static final String CLAIM_KEY_CREATED = "createdTime";

    public static final String CLAIM_KEY_ID = "userId";

    public static final String CLAIM_KEY_USERNAME = "username";

    public static final String CLAIM_KEY_ROLE = "roleList";

    public static final String CLAIM_KEY_AUTH = "permissionList";

    /**
     * 私钥
     */
    private static final String SECRET = "YourCustomSecret888";
    /**
     * 过期时长，单位为秒,当前为1周
     */
    public static final int EXPIRATION = 604800;

    /**
     * 从token中获取登陆账号
     *
     * @param token 口令
     * @return 登陆账号
     */
    public static String getAccountFromToken(String token) {
        String account;
        try {
            account = getClaimsFromToken(token).getSubject();
        } catch (Exception e) {
            account = null;
        }
        return account;
    }

    private static Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public static Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /**
     * 通过解析 token 获取 token 中的声明
     * 声明中包含了预先设置的属性
     *
     * @param token 口令
     * @return Claims
     */
    public static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private static Date generateExpirationDate() {
        // 生成 token 的过期时间
        return new Date(System.currentTimeMillis() + EXPIRATION * 1000);
    }

    /**
     * 判断token是否过期
     *
     * @param token 口令
     * @return true||false
     */
    public static Boolean isTokenExpired(String token) {
        // 判断token是否过期
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 通过用户账号生成token
     *
     * @param account 用户账号
     * @return token
     */
    public static String selfGenerateToken(String account, String userId, String username, List<String> roleNameList, List<Permission> permissionList) {
        Map<String, Object> claims = new HashMap<>();
        // 不要放入引用类型对象，所有的引用型都会变成LinkedHashMap，转换回对象会变得很麻烦
        claims.put(CLAIM_KEY_ACCOUNT, account);
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_ID, userId);
        claims.put(CLAIM_KEY_ROLE, roleNameList);
        claims.put(CLAIM_KEY_AUTH, permissionList);
        return generateToken(account, claims);
    }

    private static String generateToken(String account, Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject(account)
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 刷新 token
     *
     * @param token 久的token
     * @return 新的token
     */
    public static String refreshToken(String token, String account) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(account, claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证 token （账号是否匹配，是否未过期）
     *
     * @param token   请求头中的token
     * @param account 当前登陆账号
     * @return true||false
     */
    public static Boolean validateToken(String token, String account) {
        final String accountFromToken = getAccountFromToken(token);
        return (accountFromToken.equals(account) && !isTokenExpired(token));
    }


}
