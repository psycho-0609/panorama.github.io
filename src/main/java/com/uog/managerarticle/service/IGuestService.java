package com.uog.managerarticle.service;

import com.uog.managerarticle.entity.GuestEntity;

import java.util.List;

public interface IGuestService {

    List<GuestEntity> findAll();
    GuestEntity findById(String id) throws Exception;
    GuestEntity update(GuestEntity entity) throws Exception;
    GuestEntity save(GuestEntity entity);
    GuestEntity findByUserName(String username);
    void delete(String id) throws Exception;

    GuestEntity findByEmail(String email);
}
