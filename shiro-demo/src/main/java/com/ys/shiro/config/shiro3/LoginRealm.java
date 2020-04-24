package com.ys.shiro.config.shiro3;


import com.ys.shiro.domain.entity.User;
import com.ys.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * 自定义Realm
 * 用于获取用户的身份验证信息，并保存到安全管理的线程缓存中，以供验证登陆请求。
 *
 * @author yusheng
 */
public class LoginRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Override
    public Class<?> getAuthenticationTokenClass() {
        // 此realm只支持MyLoginToken
        return MyLoginToken.class;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof MyLoginToken;
    }

    /**
     * 获取身份验证信息，用于登陆认证
     *
     * @param authenticationToken 登陆口令[subject.login()生成 authenticationToken]
     * @return SimpleAuthenticationInfo[在shiro安全管理器中缓存身份验证信息，通过密码验证后，subject.isAuthenticated()为true] || null:抛出登陆异常UnknownAccountException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        Object account = authenticationToken.getPrincipal();
        if (account == null || account.toString().trim().equals("")) {
            throw new UnknownAccountException("空账号");
        }
        // if (!Pattern.compile("^1[34578][0-9]{9}$").matcher(account.toString()).matches()) {
        //     throw new UnknownAccountException("账号格式错误");
        // }
        Object password = authenticationToken.getCredentials();
        if (password == null || password.toString().trim().equals("")) {
            throw new IncorrectCredentialsException("空密码");
        }
        // if (!Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$").matcher(password.toString()).matches()) {
        //     // 6-18个字符，同时有数字和字母
        //     throw new IncorrectCredentialsException("密码格式错误");
        // }
        // token登陆处理
        User userQO = new User();
        userQO.setAccount(authenticationToken.getPrincipal().toString());
        User userPO = userService.getUser(userQO);
        if (userPO == null) {
            throw new UnknownAccountException("账号不存在");
            // throw new LockedAccountException("账号不可用");
        }
        return new SimpleAuthenticationInfo(userPO.getAccount(), userPO.getPassword(), getName());
    }

    /**
     * 获取登陆用户权限，用于鉴权认证[授权认证]
     * 执行该方法代表用户已经通过登陆认证，而且访问的url需要权限认证
     * 注意：每次访问需要权限认证的资源都会调用该方法，所以不建议每次都去数据库中查询。
     *
     * @param principalCollection shiro安全管理器线程中的主体
     * @return 用户权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登陆账号
        String loginAccount = (String) principalCollection.getPrimaryPrincipal();
        //添加角色和权限
        // SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // simpleAuthorizationInfo.addRoles();
        // simpleAuthorizationInfo.addStringPermissions();
        // return simpleAuthorizationInfo;
        return null;
    }


}
