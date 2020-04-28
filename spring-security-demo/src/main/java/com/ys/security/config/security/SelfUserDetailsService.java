package com.ys.security.config.security;

import com.ys.security.domain.entity.Permission;
import com.ys.security.domain.entity.Role;
import com.ys.security.domain.entity.User;
import com.ys.security.service.RoleService;
import com.ys.security.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义用户基本认证信息
 */
@Component("selfUserDetailsService")
public class SelfUserDetailsService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String loginAccount) throws UsernameNotFoundException {
        // 当前登陆账号为手机号码
        // if (loginAccount == null || "".equals(loginAccount) || Pattern.compile("^1[34578][0-9]{9}$").matcher(loginAccount).matches()) {
        if (loginAccount == null || "".equals(loginAccount)) {
            // 验证登陆账号格式，可以防治恶意登陆，应该再加上正则表达式验证
            throw new UsernameNotFoundException("账号格式有误！！！");
        }

        // 去数据库中获取当前登陆用户信息
        User userQO = new User();
        userQO.setAccount(loginAccount);
        User user = userService.getUser(userQO);
        if (user == null) {
            throw new UsernameNotFoundException("account [" + loginAccount + "] not exist！！！");
        }

        // 获取当前用户权限相关信息
        Set<MyGrantedAuthority> grantedAuthoritySet = new HashSet<>();
        Set<Permission> permissionSet = new HashSet<>();

        // 去数据库中获取当前登陆用户的角色
        List<Role> roleList = userService.listUserRoleByUserId(user.getUserId());
        if (roleList != null) {
            for (Role role : roleList) {
                grantedAuthoritySet.add(new MyGrantedAuthority(role.getRoleName()));

                // 去数据库中获取当前角色的权限
                List<Permission> permissionList = roleService.listPermissionByRoleId(role.getRoleId());
                if (permissionList != null) {
                    permissionSet.addAll(permissionList);
                }
            }
        }

        MyUserDetails myUserDetails = new MyUserDetails();
        myUserDetails.setAccount(loginAccount);
        // 一定要加密，否则security框架会抛出异常
        myUserDetails.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        myUserDetails.setUserId(user.getUserId().toString());
        myUserDetails.setUsername(user.getUsername());
        myUserDetails.setRoleNameList(new ArrayList<MyGrantedAuthority>(grantedAuthoritySet));
        myUserDetails.setPermissions(new ArrayList<Permission>(permissionSet));
        return myUserDetails;
    }
}
