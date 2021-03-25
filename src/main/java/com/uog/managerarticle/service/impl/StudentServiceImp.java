package com.uog.managerarticle.service.impl;

import com.uog.managerarticle.entity.AccountEntity;
import com.uog.managerarticle.entity.StudentEntity;
import com.uog.managerarticle.repository.AccountRepository;
import com.uog.managerarticle.repository.StudentRepository;
import com.uog.managerarticle.service.IRoleService;
import com.uog.managerarticle.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class StudentServiceImp implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public StudentEntity findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public AccountEntity findAcountByEmail(String email) {
        return accountRepository.findAccountEntityByUserName(email);
    }

    @Override
    public StudentEntity save(StudentEntity studentEntity) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserName(studentEntity.getEmail());
        accountEntity.setPassword(encoder.encode("123456789"));
        accountEntity.setRoles(Arrays.asList(roleService.finByName("STUDENT")));
        accountEntity.setPosition("student");
        studentEntity.setAccountEntity(accountEntity);
        return studentRepository.save(studentEntity);
    }

    @Override
    public StudentEntity update(StudentEntity studentEntity) throws Exception {
        Optional<StudentEntity> entity = studentRepository.findById(studentEntity.getId());
        if (!entity.isPresent()) {
            throw new Exception("Not Found");
        }
        StudentEntity oldEntity = entity.get();
        oldEntity.getAccountEntity().setUserName(studentEntity.getEmail());
        studentEntity.setAccountEntity(oldEntity.getAccountEntity());
        return studentRepository.save(studentEntity);
    }


    @Override
    public StudentEntity findById(String id) throws Exception {
        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
        if (!studentEntity.isPresent()) {
            throw new Exception("Not Found Student");
        }
        return studentEntity.get();
    }

    @Override
    public Iterable<StudentEntity> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public void delete(String id) throws Exception {
        Optional<StudentEntity> entity = studentRepository.findById(id);
        if (!entity.isPresent()) {
            throw new Exception("Not Found Student");
        }
        studentRepository.delete(entity.get());
    }
}
