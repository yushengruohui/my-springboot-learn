package com.ys.shiro.config.shiro3;


import com.ys.shiro.domain.entity.Permission;
import com.ys.shiro.domain.entity.Role;
import com.ys.shiro.domain.entity.User;
import com.ys.shiro.service.RoleService;
import com.ys.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * shiro认证处理类
 * Created on 2020-04-18 20:00
 *
 * @author yusheng
 **/
@RestController
@RequestMapping("/auth")
public class AuthHandleController {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    @GetMapping("/success")
    public String success(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(200);
        return "登陆验证成功";
    }

    @GetMapping("/login")
    public String notLogin(HttpServletResponse response) {
        // 未登陆，访问需要认证的资源时处理
        response.setStatus(401);
        JwtTokenUtils.cleanToken(response);
        return "未登录，请登陆";
    }

    @PostMapping("/login")
    public String authLogin(@RequestParam("username") String loginAccount, String password, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 从shiro安全管理的线程缓存中，根据当前访问的 sessionId 获取当前登陆账号的信息
        Subject subject = SecurityUtils.getSubject();
        Object account = subject.getPrincipal();
        if (account != null && account.toString().equals(loginAccount)) {
            response.setStatus(200);
            return "当前账号：" + account.toString() + " 已经登陆";
        } else {
            try {
                if ("".equals(loginAccount.trim()) || "".equals(password.trim())) {
                    // 可以加入正则表达式，如： Pattern.compile("^1[34578][0-9]{9}$").matcher(loginAccount).matches()
                    response.setStatus(402);
                    JwtTokenUtils.cleanToken(response);
                    return "账号或密码格式有误";
                }
                subject.login(new MyLoginToken(loginAccount, password));

                // 登陆成功后，生成token
                User userQO = new User();
                userQO.setAccount(loginAccount);
                User userPO = userService.getUser(userQO);
                // 去数据库中获取当前登陆用户的角色
                List<Role> roleList = userService.listUserRoleByUserId(userPO.getUserId());
                List<String> roleNameList = new ArrayList<>();
                List<Permission> permissionList = new ArrayList<>();
                if (roleList != null) {
                    for (Role role : roleList) {
                        roleNameList.add(role.getRoleName());
                        // 去数据库中获取当前角色的权限
                        List<Permission> permissions = roleService.listPermissionByRoleId(role.getRoleId());
                        if (permissions != null) {
                            permissionList.addAll(permissions);
                        }
                    }
                }
                String ipAddr = JwtTokenUtils.getIpAddr(request);
                String token = JwtTokenUtils.selfGenerateToken(loginAccount, ipAddr, userPO.getUserId().toString(), userPO.getUsername(), roleNameList, permissionList);
                JwtTokenUtils.responseToken(response, token, JwtTokenUtils.EXPIRATION);
                return "账号密码验证成功";
            } catch (Exception e) {
                response.setStatus(402);
                JwtTokenUtils.cleanToken(response);
                return "账号密码验证失败";
            }
        }
    }

    @GetMapping("/unAuthorized")
    public String denyAccess(HttpServletResponse response) {
        // 拒绝访问、登陆了，但没有访问权限
        response.setStatus(403);
        JwtTokenUtils.cleanToken(response);
        return "没有访问权限";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        // 注销处理
        response.setStatus(200);
        JwtTokenUtils.cleanToken(response);
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "注销成功";
    }
}
