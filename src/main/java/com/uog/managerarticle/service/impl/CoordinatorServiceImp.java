package com.uog.managerarticle.service.impl;

import com.uog.managerarticle.entity.AccountEntity;
import com.uog.managerarticle.entity.MarketingCoordinatorEntity;
import com.uog.managerarticle.repository.MarketingCoordinatorRepository;
import com.uog.managerarticle.service.IAccountService;
import com.uog.managerarticle.service.ICoordinatorService;
import com.uog.managerarticle.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CoordinatorServiceImp implements ICoordinatorService {
    @Autowired
    private MarketingCoordinatorRepository coordinatorRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private IRoleService roleService;


    @Override
    public MarketingCoordinatorEntity findByEmail(String email) {
        return coordinatorRepository.findByEmail(email);
    }

    @Override
    public List<MarketingCoordinatorEntity> findByFacultyId(String id){

        return coordinatorRepository.findByFacultyId(id);
    }

    @Override
    public List<MarketingCoordinatorEntity> findAll() {
        return coordinatorRepository.findAll();
    }

    @Override
    public MarketingCoordinatorEntity addNew(MarketingCoordinatorEntity entity) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserName(entity.getEmail());
        accountEntity.setPosition("coordinator");
        accountEntity.setPassword(encoder.encode("123456789"));
        accountEntity.setRoles(Arrays.asList(roleService.finByName("COORDINATOR")));
        entity.setAccountEntity(accountEntity);
        return coordinatorRepository.save(entity);
    }

    @Override
    public MarketingCoordinatorEntity update(MarketingCoordinatorEntity entity) throws Exception {
        Optional<MarketingCoordinatorEntity> oldEntity = coordinatorRepository.findById(entity.getId());
        if(!oldEntity.isPresent()){
            throw new Exception("Not Found Coordinator");
        }
        MarketingCoordinatorEntity coordinatorEntity = oldEntity.get();
        coordinatorEntity.getAccountEntity().setUserName(entity.getEmail());
        entity.setAccountEntity(coordinatorEntity.getAccountEntity());
        return coordinatorRepository.save(entity);
    }

    @Override
    public MarketingCoordinatorEntity findById(String id) throws Exception {
        Optional<MarketingCoordinatorEntity> entity = coordinatorRepository.findById(id);
        if(!entity.isPresent()){
            throw new Exception("Not Found Data");
        }
        return entity.get();
    }

    @Override
    public void delete(String id) throws Exception {
        Optional<MarketingCoordinatorEntity> entity = coordinatorRepository.findById(id);
        if(!entity.isPresent()){
            throw new Exception("Not Found Coordinator");
        }
        coordinatorRepository.delete(entity.get());
    }


}
