package com.uog.managerarticle.controller;

import com.uog.managerarticle.entity.AccountEntity;
import com.uog.managerarticle.repository.AccountRepository;
import com.uog.managerarticle.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("")
public class LoginController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/access-denied")
    public String AccessDenied(){
        return "authen/access-denied";
    }

    @GetMapping("/login")
    public String login(){
        AccountEntity entity = null;
        entity = accountRepository.findAccountEntityByUserName("admin@gmail.com");
        if(entity == null){
            entity = new AccountEntity();
            entity.setUserName("admin@gmail.com");
            entity.setRoles(Arrays.asList(repository.findByName("ADMIN")));
            entity.setPosition("admin");
            entity.setPassword(encoder.encode("123456789"));
            accountRepository.save(entity);
        }
        return "authen/login";
    }
}
