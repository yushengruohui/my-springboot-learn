package com.ys.shiro.config.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * shiro认证处理类
 * Created on 2020-04-18 20:00
 *
 * @author yusheng
 **/
@RestController
@RequestMapping("/auth")
public class AuthHanderController {

    @GetMapping("/success")
    public String success(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(200);
        return "登陆验证成功";
    }

    @GetMapping("/login")
    public String notLogin(HttpServletResponse response) {
        // 未登陆，访问需要认证的资源时处理
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(401);
        return "未登录，请登陆";
    }

    @PostMapping("/login")
    public String loginFault(@RequestParam("username") String loginAccount, String password, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 从 shiro 缓存中根据当前 sessionId 获取当前登陆账号的信息
        Subject subject = SecurityUtils.getSubject();
        Object account = subject.getPrincipal();
        if (account != null && account.equals(loginAccount)) {
            response.setStatus(200);
            return "当前账号：" + account.toString() + " 已经登陆";
        } else {
            try {
                if ("".equals(loginAccount.trim()) || "".equals(password.trim())) {
                    // 可以加入正则表达式，如： Pattern.compile("^1[34578][0-9]{9}$").matcher(loginAccount).matches()
                    response.setStatus(402);
                    return "账号或密码格式有误";
                }
                response.setStatus(200);
                subject.login(new UsernamePasswordToken(loginAccount, password));
                // 设置缓存时间 Shiro的Session接口有一个setTimeout()方法，登录后，可以用如下方式取得session
                // 这里设置的时间单位是:ms，当前时长1小时
                SecurityUtils.getSubject().getSession().setTimeout(3600000L);
                // 设置的最大时间，正负都可以，为负数时表示永不超时。shiro会转换为:s，不能低于-1000，否则会转成0 s
                // SecurityUtils.getSubject().getSession().setTimeout(-100l);
                return "账号密码验证成功";
            } catch (Exception e) {
                response.setStatus(402);
                return "账号密码验证失败";
            }
        }
    }

    @GetMapping("/unAuthorized")
    public String denyAccess(HttpServletResponse response) {
        // 拒绝访问、登陆了，但没有访问权限
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(403);
        return "没有访问权限";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        // 注销处理
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(200);
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "注销成功";
    }
}
