package com.uog.managerarticle.service;

import com.uog.managerarticle.entity.AccountEntity;

public interface IAccountService {
   AccountEntity findAccountByUserName(String userName);
}
