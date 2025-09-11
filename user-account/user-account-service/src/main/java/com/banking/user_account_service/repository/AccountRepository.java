package com.banking.user_account_service.repository;

import com.banking.user_account_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Keerthana
 **/
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
    @Query("SELECT a.user.id FROM Account a WHERE a.accountNumber = :accountNumber")
    Optional<Long> findUserIdByAccountNumber(@Param("accountNumber") String accountNumber);

}
