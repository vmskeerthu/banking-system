////package com.banking.transaction_service.client;
////
////import com.banking.transaction_service.DTO.AccountResponse;
////import com.banking.transaction_service.DTO.BalanceUpdateRequest;
////import lombok.RequiredArgsConstructor;
////import org.springframework.http.*;
////import org.springframework.stereotype.Component;
////import org.springframework.web.client.RestTemplate;
////
/////**
//// * @author Keerthana
//// **/
////@Component
////@RequiredArgsConstructor
////public class AccountClient {
////    private final RestTemplate restTemplate;
//// //   private final String accountServiceUrl = "http://user-account-service/api/accounts";
////
////public double getBalance(Long accountId, String authHeader) {
////    HttpHeaders headers = new HttpHeaders();
////    headers.set("Authorization", authHeader);
////
////    HttpEntity<Void> entity = new HttpEntity<>(headers);
////
////    ResponseEntity<AccountResponse> response = restTemplate.exchange(
////            "http://user-account-service/api/accounts/" + accountId + "/balance",
////            HttpMethod.GET,
////            entity,
////            AccountResponse.class
////    );
////
////    return response.getBody().getBalance();
////}
////    public void adjustBalance(Long accountId, double amount, String authHeader) {
////        HttpHeaders headers = new HttpHeaders();
////        headers.set("Authorization", authHeader);
////        headers.setContentType(MediaType.APPLICATION_JSON);
////
////        BalanceUpdateRequest request = new BalanceUpdateRequest(accountId, amount);
////        HttpEntity<BalanceUpdateRequest> entity = new HttpEntity<>(request, headers);
////
////        restTemplate.exchange(
////                "http://user-account-service/api/accounts/" + accountId + "/balance",
////                HttpMethod.PUT,
////                entity,
////                Void.class
////        );
////    }
////
////
////}
//package com.banking.transaction_service.client;
//
//import com.banking.transaction_service.DTO.AccountResponse;
//import com.banking.transaction_service.DTO.BalanceUpdateRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.*;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//@Component
//@RequiredArgsConstructor
//public class AccountClient {
//
//    private final RestTemplate restTemplate;
//
//    // Get balance by account number
//    public double getBalance(String accountNumber, String authHeader) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", authHeader);
//
//        HttpEntity<Void> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<AccountResponse> response = restTemplate.exchange(
//                "http://user-account-service/api/accounts/" + accountNumber + "/balance",
//                HttpMethod.GET,
//                entity,
//                AccountResponse.class
//        );
//
//        if (response.getBody() == null) {
//            throw new RuntimeException("No account found for account number: " + accountNumber);
//        }
//
//        return response.getBody().getBalance();
//    }
//    public String getEmailByAccountNumber(String accountNumber, String authHeader) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", authHeader);
//        HttpEntity<Void> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                "http://user-account-service/api/accounts/" + accountNumber + "/email",
//                HttpMethod.GET,
//                entity,
//                String.class
//        );
//
//        return response.getBody();
//    }
//
//    // Adjust balance by account number
//    public void adjustBalance(String accountNumber, double amount, String authHeader) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", authHeader);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        BalanceUpdateRequest request = new BalanceUpdateRequest(amount);
//        HttpEntity<BalanceUpdateRequest> entity = new HttpEntity<>(request, headers);
//
//        restTemplate.exchange(
//                "http://user-account-service/api/accounts/" + accountNumber + "/balance",
//                HttpMethod.PUT,
//                entity,
//                Void.class
//        );
//    }
//    public Long getUserIdByAccountNumber(String accountNumber, String authHeader) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", authHeader);
//
//        HttpEntity<Void> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<Long> response = restTemplate.exchange(
//                "http://user-account-service/api/accounts/" + accountNumber + "/user-id",
//                HttpMethod.GET,
//                entity,
//                Long.class
//        );
//
//
//        return response.getBody();
//    }
//
//}
//package com.banking.transaction_service.client;
//
//import com.banking.transaction_service.DTO.AccountResponse;
//import com.banking.transaction_service.DTO.BalanceUpdateRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.*;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
///**
// * @author Keerthana
// **/
//@Component
//@RequiredArgsConstructor
//public class AccountClient {
//    private final RestTemplate restTemplate;
// //   private final String accountServiceUrl = "http://user-account-service/api/accounts";
//
//public double getBalance(Long accountId, String authHeader) {
//    HttpHeaders headers = new HttpHeaders();
//    headers.set("Authorization", authHeader);
//
//    HttpEntity<Void> entity = new HttpEntity<>(headers);
//
//    ResponseEntity<AccountResponse> response = restTemplate.exchange(
//            "http://user-account-service/api/accounts/" + accountId + "/balance",
//            HttpMethod.GET,
//            entity,
//            AccountResponse.class
//    );
//
//    return response.getBody().getBalance();
//}
//    public void adjustBalance(Long accountId, double amount, String authHeader) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", authHeader);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        BalanceUpdateRequest request = new BalanceUpdateRequest(accountId, amount);
//        HttpEntity<BalanceUpdateRequest> entity = new HttpEntity<>(request, headers);
//
//        restTemplate.exchange(
//                "http://user-account-service/api/accounts/" + accountId + "/balance",
//                HttpMethod.PUT,
//                entity,
//                Void.class
//        );
//    }
//
//
//}
package com.banking.transaction_service.client;

import com.banking.transaction_service.DTO.AccountResponse;
import com.banking.transaction_service.DTO.BalanceUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AccountClient {

    private final RestTemplate restTemplate;

    // Get balance by account number
    public double getBalance(String accountNumber, String authHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<AccountResponse> response = restTemplate.exchange(
                "http://user-account-service/api/accounts/" + accountNumber + "/balance",
                HttpMethod.GET,
                entity,
                AccountResponse.class
        );

        if (response.getBody() == null) {
            throw new RuntimeException("No account found for account number: " + accountNumber);
        }

        return response.getBody().getBalance();
    }

    // Adjust balance by account number
    public void adjustBalance(String accountNumber, double amount, String authHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        headers.setContentType(MediaType.APPLICATION_JSON);

        BalanceUpdateRequest request = new BalanceUpdateRequest(amount);
        HttpEntity<BalanceUpdateRequest> entity = new HttpEntity<>(request, headers);

        restTemplate.exchange(
                "http://user-account-service/api/accounts/" + accountNumber + "/balance",
                HttpMethod.PUT,
                entity,
                Void.class
        );
    }
    public Long getUserIdByAccountNumber(String accountNumber, String authHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Long> response = restTemplate.exchange(
                "http://user-account-service/api/accounts/" + accountNumber + "/user-id",
                HttpMethod.GET,
                entity,
                Long.class
        );

        return response.getBody();
    }
    public String getEmailByAccountNumber(String accountNumber, String authHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "http://user-account-service/api/users/email-by-account/" + accountNumber,
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map<String, String> body = response.getBody();
        if (body == null || !body.containsKey("email")) {
            throw new RuntimeException("Email not found for account number: " + accountNumber);
        }

        return body.get("email");
    }


}
