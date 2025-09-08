package com.banking.events;

import lombok.*;

/**
 * @author Keerthana
 **/
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class TransferFailedEvent {
    private Long userId;
    private String recipient;
    private String reason;
}
