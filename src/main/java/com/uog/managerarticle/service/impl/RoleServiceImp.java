package com.uog.managerarticle.service.impl;

import com.uog.managerarticle.entity.RoleEntity;
import com.uog.managerarticle.repository.RoleRepository;
import com.uog.managerarticle.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp implements IRoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public RoleEntity finByName(String name) {
        return roleRepository.findByName(name);
    }
}
