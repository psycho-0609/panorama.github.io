package com.uog.managerarticle.service.impl;

import com.uog.managerarticle.entity.AccountEntity;
import com.uog.managerarticle.entity.GuestEntity;
import com.uog.managerarticle.repository.GuestRepository;
import com.uog.managerarticle.service.IGuestService;
import com.uog.managerarticle.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class GuestServiceImp implements IGuestService {

    @Autowired
    private GuestRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private IRoleService roleService;

    @Override
    public List<GuestEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public GuestEntity findById(String id) throws Exception {
        Optional<GuestEntity> entity = repository.findById(id);
        if(!entity.isPresent()){
            throw new Exception("Not Found Guest");
        }
        return entity.get();
    }

    @Override
    public GuestEntity update(GuestEntity entity) throws Exception {
        Optional<GuestEntity>  oldEntity = repository.findById(entity.getId());
        if (!oldEntity.isPresent()){
            throw new Exception("Not Found Guest");
        }
        GuestEntity oldGuest = oldEntity.get();
        oldGuest.getAccountEntity().setUserName(entity.getEmail());
        entity.setAccountEntity(oldGuest.getAccountEntity());
        return repository.save(entity);
    }

    @Override
    public GuestEntity save(GuestEntity entity) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserName(entity.getEmail());
        accountEntity.setPassword(encoder.encode("123456789"));
        accountEntity.setRoles(Arrays.asList(roleService.finByName("GUEST")));
        accountEntity.setPosition("guest");
        entity.setAccountEntity(accountEntity);
        return repository.save(entity);
    }

    @Override
    public GuestEntity findByUserName(String username) {
        return repository.findByEmail(username);
    }

    @Override
    public void delete(String id) throws Exception {
        Optional<GuestEntity> entity = repository.findById(id);
        if(!entity.isPresent()){
            throw new Exception("Not Found Guest");
        }
        repository.delete(entity.get());
    }

    @Override
    public GuestEntity findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
