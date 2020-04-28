package com.ys.security.config.security;

import com.ys.security.domain.entity.Permission;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * token 工具
 *
 * @author yusheng
 * Created on 2020-03-22 17:51:26
 **/
public class JwtTokenUtils {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtils.class);

    public static final String TOKEN_KEY = "Authentication";

    //标准化声明
    public static final String CLAIM_KEY_ISSUE = "iss";
    public static final String CLAIM_KEY_SUBJECT = "sub";
    public static final String CLAIM_KEY_AUDIENCE = "aud";
    public static final String CLAIM_KEY_ISSUE_AT = "iat";
    public static final String CLAIM_KEY_EXPIRATION = "exp";
    public static final String CLAIM_KEY_NOT_BEFORE = "nbf";
    public static final String CLAIM_KEY_JWT_ID = "jti";

    //自定义声明
    public static final String CLAIM_KEY_REFRESH = "refresh";
    public static final String CLAIM_KEY_USER_ID = "userId";
    public static final String CLAIM_KEY_USERNAME = "username";
    public static final String CLAIM_KEY_ROLE = "roleList";
    public static final String CLAIM_KEY_AUTHENTICATION = "permissionList";

    /**
     * 私钥，推荐使用电脑生成的私钥
     */
    private static final String SECRET = "YourCustomSecret888";
    /**
     * 过期时长，单位为秒,当前为3天
     */
    public static final long EXPIRATION = 259200;

    /**
     * 从token中获取登陆账号
     *
     * @param token 口令
     * @return 登陆账号
     */
    public static String getAccountFromToken(String token) {
        String account;
        try {
            account = getClaimsByToken(token).getSubject();
        } catch (Exception e) {
            account = null;
            log.error("Subject is null. Error token");
        }
        return account;
    }

    private static Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            Claims claims = getClaimsByToken(token);
            created = claims.getIssuedAt();
        } catch (Exception e) {
            created = null;
            log.error("error created datetime!");
        }
        return created;
    }

    public static Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsByToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            log.error("error created datetime!");
            expiration = null;
        }
        return expiration;
    }

    /**
     * 通过解析 token 获取 token 中的自定义声明
     *
     * @param token 口令
     * @return Claims
     */
    public static Claims getClaimsByToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("error token");
            claims = null;
        }
        return claims;
    }

    /**
     * 判断token是否过期
     *
     * @param token 口令
     * @return true||false
     */
    public static Boolean isTokenExpired(String token) {
        // 判断token是否过期
        Date expiration = getExpirationDateFromToken(token);
        boolean flag = true;
        try {
            // before = expiration < new Date()
            flag = expiration.before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static Boolean isRefresh(String token) {
        // 判断token是否过期
        boolean flag = true;
        try {
            Date refreshDate = (Date) getClaimsByToken(token).get(CLAIM_KEY_REFRESH);
            flag = refreshDate.before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 生成token
     *
     * @param account        登陆账号
     * @param audience       jwt签发给的第三方，没有合作第三方，暂时为登陆IP
     * @param userId         登陆用户的id
     * @param username       登陆用户名
     * @param roleNameList   角色
     * @param permissionList 权限
     * @return token
     */
    public static String selfGenerateToken(String account, String audience, String userId, String username, List<String> roleNameList, List<Permission> permissionList) {
        // 不推荐放入引用类型对象，所有的引用型都会变成LinkedHashMap，转换回对象会变得很麻烦
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, Object> claims = new HashMap<>();
        // 标准声明
        claims.put(CLAIM_KEY_ISSUE, "servlet");
        claims.put(CLAIM_KEY_SUBJECT, account);
        claims.put(CLAIM_KEY_AUDIENCE, audience);
        claims.put(CLAIM_KEY_EXPIRATION, new Date(currentTimeMillis + EXPIRATION * 1000));
        claims.put(CLAIM_KEY_ISSUE_AT, new Date(currentTimeMillis));
        // claims.put(CLAIM_KEY_NOT_BEFORE, new Date());
        // claims.put(CLAIM_KEY_JWT_ID, userId);
        // 自定义声明
        claims.put(CLAIM_KEY_USER_ID, userId);
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_ROLE, roleNameList);
        claims.put(CLAIM_KEY_AUTHENTICATION, permissionList);
        claims.put(CLAIM_KEY_REFRESH, new Date(currentTimeMillis + EXPIRATION * 2000));
        return generateToken(claims);
    }

    private static String generateToken(Map<String, Object> claims) {
        // 设置头部信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return Jwts.builder()
                .setHeader(header)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    /**
     * 刷新 token
     *
     * @param token 久的token
     * @return 新的token
     */
    public static String refreshToken(String token) {
        String newToken;
        try {
            Claims claims = getClaimsByToken(token);
            long currentTimeMillis = System.currentTimeMillis();
            claims.put(CLAIM_KEY_ISSUE_AT, new Date(currentTimeMillis));
            claims.put(CLAIM_KEY_EXPIRATION, new Date(currentTimeMillis + EXPIRATION * 1000));
            claims.put(CLAIM_KEY_REFRESH, new Date(currentTimeMillis + EXPIRATION * 2000));
            newToken = generateToken(claims);
        } catch (Exception e) {
            newToken = null;
        }
        return newToken;
    }

    /**
     * 验证当前token是否合法
     *
     * @param httpServletRequest 访问的请求
     * @return true：通过验证||false：验证失败
     */
    public static Boolean validateToken(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, TOKEN_KEY);
        String token = cookie != null ? cookie.getValue() : null;
        // String token = httpServletRequest.getHeader(TOKEN_KEY);
        if (token != null && !token.trim().equals("")) {
            String audience = getClaimsByToken(token).getAudience();
            String loginIp = getIpAddr(httpServletRequest);
            if (audience == null || audience.trim().equals("") || loginIp == null || loginIp.trim().equals("")) {
                return false;
            }
            if (audience.equals(loginIp) && !isTokenExpired(token)) {
                return true;
            } else if (!isRefresh(token)) {
                // 仍在刷新期内，刷新token
                String refreshToken = refreshToken(token);
                responseToken(response, refreshToken, EXPIRATION);
                return true;
            } else {
                // 过期了
                response.setStatus(401);
                cleanToken(response);
                return false;
            }
        }
        return false;
    }

    /**
     * 返回包含jwt cookie给前端
     *
     * @param response     HttpServletResponse
     * @param token        token
     * @param cookieMaxAge cookieMaxAge
     */
    public static void responseToken(HttpServletResponse response, String token, long cookieMaxAge) {
        SelfCookie(response, token, cookieMaxAge);
    }

    /**
     * 清空cookie
     *
     * @param response HttpServletResponse
     */
    public static void cleanToken(HttpServletResponse response) {
        SelfCookie(response, "", 0);
    }

    /**
     * 从jwt的自定义声明中获取用户权限信息
     *
     * @param claims 自定义声明
     * @return 用户权限信息
     */
    public static List<Permission> getPermissionByClaims(Claims claims) {
        @SuppressWarnings("unchecked")
        List<LinkedHashMap<String, Object>> jwtPermissionList = claims.get(CLAIM_KEY_AUTHENTICATION, List.class);
        List<Permission> permissionList = new ArrayList<>();
        try {
            for (LinkedHashMap<String, Object> permissionMap : jwtPermissionList) {
                Permission permission = new Permission();
                permission.setPermissionId(Long.valueOf(permissionMap.get("permissionId").toString()));
                // permission.setDescription(permissionMap.get("description").toString());
                permission.setAccessibleMethod(permissionMap.get("accessibleMethod").toString());
                permission.setAccessibleUrl(permissionMap.get("accessibleUrl").toString());
                permissionList.add(permission);
            }
        } catch (Exception e) {
            //   权限的属性为空时会抛出异常 obeject.toString
            log.error("当前用户权限信息为空");
            permissionList = null;
        }
        return permissionList;
    }

    /**
     * 获取IP地址
     *
     * @param httpServletRequest 访问请求
     * @return ip地址字符串
     */
    public static String getIpAddr(HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    /**
     * 浏览器IE 6、J2EE 5、 tomcat 7.0 之后的版本的设置cookie httpOnly 的方式
     *
     * @param response     httpResponse
     * @param cookieValue  cookie值
     * @param cookieMaxAge cookie存活时间
     */
    private static void SelfCookie(HttpServletResponse response, String cookieValue, long cookieMaxAge) {
        Cookie cookie = new Cookie(JwtTokenUtils.TOKEN_KEY, cookieValue);
        cookie.setHttpOnly(true);
        // cookie.setSecure(true);
        // cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge((int) cookieMaxAge);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;");
        response.addCookie(cookie);
    }
}
