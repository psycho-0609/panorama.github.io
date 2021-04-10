package com.uog.managerarticle.controller;


import com.uog.managerarticle.dto.ChangePass;
import com.uog.managerarticle.service.IAccountService;
import com.uog.managerarticle.service.IUserService;
import com.uog.managerarticle.user.CustomUserDetail;
import com.uog.managerarticle.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IAccountService accountService;

    @GetMapping("/profile")
    public String userProfile(Model model) throws Exception {
        Object user = null;
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        if (userDetail == null) {
            throw new Exception("Not Found User");
        }
        user = userService.getUserProfile(userDetail);
        model.addAttribute("user", user);
        model.addAttribute("newPass",new ChangePass());
        return "user/user-profile";
    }


    @PostMapping("/changePass")
    public String doChangePass(@Valid @ModelAttribute("newPass") ChangePass entity, BindingResult bindingResult, RedirectAttributes ra, Model model){
        if(bindingResult.hasErrors()){
            ra.addFlashAttribute("error","Please input full password");
            return "redirect:/user/profile";
        }
        if(!entity.getNewPass().equals(entity.getConfirmPass())){
            ra.addFlashAttribute("error","Password not math");
            return "redirect:/user/profile";
        }
        try {
            accountService.changePass(entity.getNewPass());
            ra.addFlashAttribute("message","Change password successful");

        }catch (Exception ex){
            ra.addFlashAttribute("message","Can not change password");
        }
        return "redirect:/user/profile";
    }
}
