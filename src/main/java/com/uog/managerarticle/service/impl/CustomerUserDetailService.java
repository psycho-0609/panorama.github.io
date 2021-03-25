package com.uog.managerarticle.service.impl;

import com.uog.managerarticle.entity.AccountEntity;

import com.uog.managerarticle.entity.RoleEntity;
import com.uog.managerarticle.repository.AccountRepository;
import com.uog.managerarticle.service.ICoordinatorService;
import com.uog.managerarticle.service.IGuestService;
import com.uog.managerarticle.service.IManagerService;
import com.uog.managerarticle.service.IStudentService;
import com.uog.managerarticle.user.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerUserDetailService implements UserDetailsService {
    private final AccountRepository accountRepository;


    @Autowired
    public CustomerUserDetailService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //check user valid in database
        AccountEntity entity = accountRepository.findAccountEntityByUserName(userName);
        if(entity == null){
            throw  new UsernameNotFoundException(userName);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for(RoleEntity roleEntity:entity.getRoles()){
            authorities.add(new SimpleGrantedAuthority(roleEntity.getName()));
        }
        CustomUserDetail userDetail = new CustomUserDetail(entity.getUserName(),entity.getPassword(), true,true,true,true,authorities);
        userDetail.setPosition(entity.getPosition());

        return userDetail;

    }

}
