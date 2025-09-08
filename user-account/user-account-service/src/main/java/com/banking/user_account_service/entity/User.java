package com.banking.user_account_service.entity;

import com.banking.user_account_service.enums.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Keerthana
 **/
@Entity
@Table(name="users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String passwordHash;
  private AccountType accountType;
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL)
    List<Account> accounts=new ArrayList<>();

}
