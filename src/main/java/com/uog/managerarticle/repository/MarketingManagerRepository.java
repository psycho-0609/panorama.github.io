package com.uog.managerarticle.repository;

import com.uog.managerarticle.entity.MarketingManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketingManagerRepository extends JpaRepository<MarketingManagerEntity, String> {
    MarketingManagerEntity findByEmail(String email);
}
