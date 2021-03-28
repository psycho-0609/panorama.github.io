package com.uog.managerarticle.controller;


import com.uog.managerarticle.service.IUserService;
import com.uog.managerarticle.user.CustomUserDetail;
import com.uog.managerarticle.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/profile")
    public String userProfile(Model model) throws Exception {
        Object user = null;
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if (userDetail == null) {
            throw new Exception("Not Found User");
        }
        user = userService.getUserProfile(userDetail);
        model.addAttribute("user", user);
        return "user/user-profile";
    }
}
