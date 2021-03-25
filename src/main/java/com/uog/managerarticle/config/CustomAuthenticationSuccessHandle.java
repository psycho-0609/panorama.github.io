package com.uog.managerarticle.config;

import com.uog.managerarticle.entity.AccountEntity;
import com.uog.managerarticle.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandle implements AuthenticationSuccessHandler {

    private final IAccountService accountService;

    @Autowired
    public CustomAuthenticationSuccessHandle(IAccountService accountService) {
        this.accountService = accountService;
    }

    // handle if login success
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // put information of user into session
//        AccountEntity account = accountService.findAccountByUserName(authentication.getName());
//
//        HttpSession session = httpServletRequest.getSession();
//        session.setAttribute("user",account);
        // redirect to home page
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/");
    }
}
