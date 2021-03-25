package com.uog.managerarticle.repository;

import com.uog.managerarticle.entity.GuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<GuestEntity,String> {

    GuestEntity findByEmail(String email);
}
