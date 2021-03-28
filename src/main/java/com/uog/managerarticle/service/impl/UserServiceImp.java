package com.uog.managerarticle.service.impl;

import com.uog.managerarticle.entity.*;
import com.uog.managerarticle.repository.GuestRepository;
import com.uog.managerarticle.repository.MarketingCoordinatorRepository;
import com.uog.managerarticle.repository.MarketingManagerRepository;
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

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private MarketingManagerRepository managerRepository;
//    @Override
//    public User getUserProfile(CustomUserDetail userDetail) {
//        User user = new User();
//
//        if(userDetail.getPosition().equals("student")){
//            user = studentRepository.findByEmail(userDetail.getUsername());
//        }else if(userDetail.getPosition().equals("coordinator")){
//            user = coordinatorRepository.findByEmail(userDetail.getUsername());
//        }
//        return user;
//    }

    @Override
    public Object getUserProfile(CustomUserDetail userDetail) {
//        Object user = new Object();
        if (userDetail.getPosition().equals("student")) {
            StudentEntity user = studentRepository.findByEmail(userDetail.getUsername());
            return user;
        } else if (userDetail.getPosition().equals("coordinator")) {
            MarketingCoordinatorEntity user = coordinatorRepository.findByEmail(userDetail.getUsername());
            return user;
        } else if (userDetail.getPosition().equals("guest")) {
            GuestEntity user = guestRepository.findByEmail(userDetail.getUsername());
            return user;
        } else if (userDetail.getPosition().equals("manager")) {
            MarketingManagerEntity user = managerRepository.findByEmail(userDetail.getUsername());
            return user;
        }
        return null;
    }


}
