package com.uog.managerarticle.service.impl;


import com.uog.managerarticle.entity.AccountEntity;
import com.uog.managerarticle.repository.AccountRepository;
import com.uog.managerarticle.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountEntity findAccountByUserName(String userName) {
        return accountRepository.findAccountEntityByUserName(userName);
    }
}
