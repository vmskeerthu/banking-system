package com.banking.events;


/**
 * @author Keerthana
 **/
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisteredEvent {
    private Long userId;
    private String email;
    private String name;
    private String accountNumber;
    private String accountType;
}
