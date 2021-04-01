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

@Controller
@RequestMapping("")
public class LoginController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/access-denied")
    public String AccessDenied(){
        return "authen/access-denied";
    }

    @GetMapping("/login")
    public String login(){
        AccountEntity accountEntity  = accountRepository.findAccountEntityByUserName("admin@gmail.com");
        if(accountEntity == null){
            accountEntity = new AccountEntity();
            accountEntity.setUserName("admin@gmail.com");
            accountEntity.setPassword(encoder.encode("123456789"));
            accountEntity.setPosition("admin");
            accountEntity.setRoles(Arrays.asList(repository.findByName("ADMIN")));

            accountRepository.save(accountEntity);
        }
//        System.out.println(accountEntity.getId());
        return "authen/login";
    }
}
