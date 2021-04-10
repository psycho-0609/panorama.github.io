package com.uog.managerarticle.repository;

import com.uog.managerarticle.entity.FacultyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<FacultyEntity, String> {
    FacultyEntity findAllByName(String name);
    FacultyEntity findAllByCode(String code);
}
