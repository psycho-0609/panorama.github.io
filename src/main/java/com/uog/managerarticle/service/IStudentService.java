package com.uog.managerarticle.service;

import com.uog.managerarticle.entity.StudentEntity;

import java.util.Optional;

public interface IStudentService {

    StudentEntity findByEmail(String email);
    StudentEntity save(StudentEntity studentEntity);
    StudentEntity update(StudentEntity studentEntity) throws Exception;
    StudentEntity findById(String id) throws Exception;
    Iterable<StudentEntity> findAll();
    void delete(String id) throws Exception;

}
