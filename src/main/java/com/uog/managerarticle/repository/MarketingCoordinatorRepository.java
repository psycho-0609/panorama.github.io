package com.uog.managerarticle.repository;

import com.uog.managerarticle.entity.MarketingCoordinatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarketingCoordinatorRepository extends JpaRepository<MarketingCoordinatorEntity, String> {

    MarketingCoordinatorEntity findByEmail(String email);
    List<MarketingCoordinatorEntity> findByFacultyId(String id);
}
