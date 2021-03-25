package com.uog.managerarticle.service;

import com.uog.managerarticle.entity.MarketingCoordinatorEntity;

import java.util.List;

public interface ICoordinatorService {

    MarketingCoordinatorEntity findByEmail(String email);
    List<MarketingCoordinatorEntity> findByFacultyId(String id);
    List<MarketingCoordinatorEntity> findAll();
    MarketingCoordinatorEntity addNew(MarketingCoordinatorEntity entity);
    MarketingCoordinatorEntity update(MarketingCoordinatorEntity entity) throws Exception;
    MarketingCoordinatorEntity findById(String id) throws Exception;
    void delete(String id) throws Exception;
}
