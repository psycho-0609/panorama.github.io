package com.uog.managerarticle.user;


import com.uog.managerarticle.entity.AccountEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


public class UserInfor {

    public static CustomUserDetail getPrincipal(){
        CustomUserDetail userDetail = (CustomUserDetail) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
        return userDetail;
    }

    @SuppressWarnings("unckeced")
    public static List<String> getAuthorities(){
        List<String> result  = new ArrayList<>();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            result.add(authority.getAuthority());
        }
        return result;
    }
}
