package com.uog.managerarticle.repository;

import com.uog.managerarticle.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, String> {

    StudentEntity findByEmail(String email);
}
