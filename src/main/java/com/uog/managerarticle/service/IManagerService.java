package com.uog.managerarticle.service;

import com.uog.managerarticle.entity.MarketingManagerEntity;

public interface IManagerService {

    MarketingManagerEntity save(MarketingManagerEntity manager);
    MarketingManagerEntity findById(String id) throws Exception;
    Iterable<MarketingManagerEntity> findAll();
    MarketingManagerEntity update(MarketingManagerEntity managerEntity) throws Exception;
    void delete(String id) throws Exception;
}
