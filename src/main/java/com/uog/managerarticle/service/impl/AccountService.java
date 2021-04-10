package com.uog.managerarticle.service.impl;


import com.uog.managerarticle.entity.AccountEntity;
import com.uog.managerarticle.repository.AccountRepository;
import com.uog.managerarticle.service.IAccountService;
import com.uog.managerarticle.user.CustomUserDetail;
import com.uog.managerarticle.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    private final BCryptPasswordEncoder encoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, BCryptPasswordEncoder encoder) {
        this.accountRepository = accountRepository;
        this.encoder = encoder;
    }

    @Override
    public AccountEntity findAccountByUserName(String userName) {
        return accountRepository.findAccountEntityByUserName(userName);
    }

    @Override
    public void changePass(String newPass) throws Exception {
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        AccountEntity entity = accountRepository.findAccountEntityByUserName(userDetail.getUsername());
        if(entity == null) throw new Exception("Not Found Account");
        entity.setPassword(encoder.encode(newPass));
        accountRepository.save(entity);
    }
}
