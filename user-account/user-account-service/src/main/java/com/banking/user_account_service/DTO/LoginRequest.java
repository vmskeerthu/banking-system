package com.banking.user_account_service.DTO;

import lombok.*;

/**
 * @author Keerthana
 **/
@Getter @Setter
public class LoginRequest {
    private String email;
    private String password;
}