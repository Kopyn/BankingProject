package com.kopyn.cqrs.account_service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class AccountInfo {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String uuid;
    private String customerId;
    private long balance;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean deleted; // proper would be some status CREATED, DELETED


    public AccountInfo(String uuid, String customerId, long balance, boolean deleted) {
        this.uuid = uuid;
        this.customerId = customerId;
        this.balance = balance;
        this.deleted = deleted;
    }

    public AccountInfo(AccountInfo other) {
        this.uuid = other.uuid;
        this.customerId = other.customerId;
        this.balance = other.balance;
        this.deleted = other.deleted;
    }

}
