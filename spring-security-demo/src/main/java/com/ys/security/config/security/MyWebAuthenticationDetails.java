package com.ys.security.config.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 自定义web身份验证详细信息(用于登录验证中增加额外参数)
 * 登陆成功后，默认authentication中的detail就是WebAuthenticationDetails，自定义后就是自定义的CustomWebAuthenticationDetails
 */
class MyWebAuthenticationDetails extends WebAuthenticationDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    private String macAddress;
    private String validatedCode;

    MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        // 通过这个方式获取请求中的参数
        macAddress = request.getParameter("macAddress");
        validatedCode = request.getParameter("validatedCode");
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getValidatedCode() {
        return validatedCode;
    }
}
