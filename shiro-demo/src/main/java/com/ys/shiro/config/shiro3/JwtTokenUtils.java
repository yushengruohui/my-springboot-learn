package com.ys.shiro.config.shiro3;

import com.ys.shiro.domain.entity.Permission;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * token 工具
 *
 * @autho yusheng
 * @create-date 2020-03-22 17:51
 **/
public class JwtTokenUtils {

    public static final String TOKEN_KEY = "Authentication";

    public static final String CLAIM_KEY_USER_ID = "userId";

    public static final String CLAIM_KEY_USERNAME = "username";

    public static final String CLAIM_KEY_EXPIRATION = "exp";

    public static final String CLAIM_KEY_SUBJECT = "sub";

    public static final String CLAIM_KEY_AUDIENCE = "aud";

    public static final String CLAIM_KEY_ROLE = "roleList";

    public static final String CLAIM_KEY_AUTH = "permissionList";

    /**
     * 私钥，推荐使用电脑生成的私钥
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
            created = claims.getIssuedAt();
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
            System.out.println("获取token过期时间异常");
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
        boolean flag = true;
        try {
            flag = expiration.before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 通过用户账号生成token
     *
     * @param account 用户账号
     * @return token
     */
    public static String selfGenerateToken(String account, String ip, String userId, String username, List<String> roleNameList, List<Permission> permissionList) {
        Map<String, Object> claims = new HashMap<>();
        // 不推荐放入引用类型对象，所有的引用型都会变成LinkedHashMap，转换回对象会变得很麻烦
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_AUDIENCE, ip);
        claims.put(CLAIM_KEY_USER_ID, userId);
        claims.put(CLAIM_KEY_SUBJECT, account);
        claims.put(CLAIM_KEY_EXPIRATION, generateExpirationDate());
        claims.put(CLAIM_KEY_ROLE, roleNameList);
        claims.put(CLAIM_KEY_AUTH, permissionList);
        return generateToken(account, ip, claims);
    }

    private static String generateToken(String account, String ip, Map<String, Object> claims) {
        // jwt 主要由 header[表示header、payload的加密算法]、payload[携带标准声明、自定义声明]、signature[加密后的header、payload、密钥] 组成
        return Jwts.builder()
                // 标准声明：jwt签发者
                // .setIssuer("当前服务器")
                // 标准声明：主体[用户]（jwt签发给该主体)
                .setSubject(account)
                // 标准声明: jwt接受方
                .setAudience(ip)
                // 标准声明: jwt过期时期
                .setExpiration(generateExpirationDate())
                // 标准声明：定义在什么时间之前，该jwt都是不可用的
                // .setNotBefore()
                // 标准声明：jwt的签发时间
                .setIssuedAt(new Date())
                // 标准声明：jwt的id，jwt的唯一身份标识
                // .setId()
                // 自定义声明：key-value
                .setClaims(claims)
                // 签名，加密header、payload、密钥
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 刷新 token
     *
     * @param token 久的token
     * @return 新的token
     */
    public static String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            refreshedToken = generateToken(claims.getSubject(), claims.getAudience(), claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证当前登陆的IP和签发的IP是否相同
     *
     * @param httpServletRequest 访问的请求
     * @return true||false
     */
    public static Boolean validateToken(HttpServletRequest httpServletRequest) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, TOKEN_KEY);
        String token = cookie != null ? cookie.getValue() : null;
        // String token = httpServletRequest.getHeader(TOKEN_KEY);
        if (token != null) {
            final String audienceInToken = getClaimsFromToken(token).getAudience();
            String loginIp = getIpAddr(httpServletRequest);
            if (audienceInToken == null || audienceInToken.trim().equals("") || loginIp == null || loginIp.trim().equals("")) {
                return false;
            }
            return (audienceInToken.equals(loginIp) && !isTokenExpired(token));
        }
        return false;
    }

    public static void responseToken(HttpServletResponse response, String token, long cookieMaxAge) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(200);
        StringBuilder buffer = new StringBuilder();
        // 设置 cookie-token
        buffer.append(JwtTokenUtils.TOKEN_KEY).append("=").append(token).append(";");
        if (cookieMaxAge <= 0) {
            buffer.append("Expires=Thu Jan 01 08:00:00 CST 1970;");
        } else {
            buffer.append("Max-Age=").append(cookieMaxAge).append(";");
        }
        // domain:cookie所在的域名。设置域名后、前端访问该域名的资源时，自动带上该cookie，默认为服务器的域名
        // buffer.append("domain=").append("localhost").append(";");
        // 指定访问路径前缀
        buffer.append("path=").append("/").append(";");
        // 加密传输
        // buffer.append("secure;");
        buffer.append("HTTPOnly;");
        response.addHeader("Set-Cookie", buffer.toString());
    }

    public static void cleanToken(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;");
        response.setHeader("Access-Control-Allow-Origin", "*");
        StringBuilder buffer = new StringBuilder();
        // 设置 cookie-token
        buffer.append(JwtTokenUtils.TOKEN_KEY).append("=").append("").append(";");
        buffer.append("Expires=Thu Jan 01 08:00:00 CST 1970;");
        // domain:cookie所在的域名。设置域名后、前端访问该域名的资源时，自动带上该cookie，默认为服务器的域名
        // buffer.append("domain=").append("localhost").append(";");
        // 指定访问路径前缀
        buffer.append("path=").append("/").append(";");
        // 加密传输
        // buffer.append("secure;");
        buffer.append("HTTPOnly;");
        response.addHeader("Set-Cookie", buffer.toString());
    }

    /**
     * 从jwt的自定义声明中获取用户权限信息
     *
     * @param claims 自定义声明
     * @return 用户权限信息
     */
    public static List<Permission> getPermissionByClaims(Claims claims) {
        @SuppressWarnings("unchecked")
        List<LinkedHashMap<String, Object>> jwtPermissionList = claims.get(CLAIM_KEY_AUTH, List.class);
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
            System.out.println("权限属性不能为空！！！！");
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
}
