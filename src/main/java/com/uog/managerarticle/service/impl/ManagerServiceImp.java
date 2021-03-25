package com.uog.managerarticle.service.impl;

import com.uog.managerarticle.entity.AccountEntity;
import com.uog.managerarticle.entity.MarketingCoordinatorEntity;
import com.uog.managerarticle.entity.MarketingManagerEntity;
import com.uog.managerarticle.repository.MarketingManagerRepository;
import com.uog.managerarticle.service.IManagerService;
import com.uog.managerarticle.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class ManagerServiceImp implements IManagerService {

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    private MarketingManagerRepository managerRepository;

    @Autowired
    private IRoleService roleService;

    @Override
    public MarketingManagerEntity save(MarketingManagerEntity manager) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserName(manager.getEmail());
        accountEntity.setPosition("manager");
        accountEntity.setPassword(encoder.encode("123456789"));
        accountEntity.setRoles(Arrays.asList(roleService.finByName("MANAGER")));
        manager.setAccountEntity(accountEntity);
        return managerRepository.save(manager);
    }

    @Override
    public MarketingManagerEntity update(MarketingManagerEntity entity) throws Exception {
        Optional<MarketingManagerEntity> oldEntity = managerRepository.findById(entity.getId());
        if(!oldEntity.isPresent()){
            throw new Exception("Not Found");
        }
        MarketingManagerEntity oldManager = oldEntity.get();
        oldManager.getAccountEntity().setUserName(entity.getEmail());
        return managerRepository.save(entity);
    }

    @Override
    public void delete(String id) throws Exception {
        Optional<MarketingManagerEntity> entity = managerRepository.findById(id);
        if(!entity.isPresent()){
            throw new Exception("Not Found Coordinator");
        }
        managerRepository.delete(entity.get());
    }

    @Override
    public MarketingManagerEntity findById(String id) throws Exception {
        Optional<MarketingManagerEntity> entity = managerRepository.findById(id);
        if(!entity.isPresent()){
            throw new Exception("Not Found Manager");
        }

        return entity.get();
    }

    @Override
    public Iterable<MarketingManagerEntity> findAll() {
        return managerRepository.findAll();
    }
}
