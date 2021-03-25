package com.uog.managerarticle.service.impl;

import com.uog.managerarticle.repository.MarketingCoordinatorRepository;
import com.uog.managerarticle.repository.StudentRepository;
import com.uog.managerarticle.service.IUserService;
import com.uog.managerarticle.user.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;



@Service
public class UserServiceImp implements IUserService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MarketingCoordinatorRepository coordinatorRepository;




    @Override
    public Object getUserProfile(CustomUserDetail userDetail) {
        Object user = new Object();
        if(userDetail.getPosition().equals("student")){
            user = studentRepository.findByEmail(userDetail.getUsername());
        }else if(userDetail.getPosition().equals("coordinator")){
            user = coordinatorRepository.findByEmail(userDetail.getUsername());
        }
        return user;
    }


}
