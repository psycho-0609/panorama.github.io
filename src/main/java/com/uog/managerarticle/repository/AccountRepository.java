package com.uog.managerarticle.repository;

import com.uog.managerarticle.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findAccountEntityByUserName(String userName);
}
