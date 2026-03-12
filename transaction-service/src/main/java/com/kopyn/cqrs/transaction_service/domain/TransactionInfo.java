package com.kopyn.cqrs.transaction_service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class TransactionInfo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String uuid;
    private String customerId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long balance;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean deleted; // proper would be some status CREATED, DELETED

    public TransactionInfo(String uuid, String customerId, long balance, boolean deleted) {
        this.uuid = uuid;
        this.customerId = customerId;
        this.balance = balance;
        this.deleted = deleted;
    }

    public TransactionInfo(TransactionInfo other) {
        this.uuid = other.uuid;
        this.customerId = other.customerId;
        this.balance = other.balance;
        this.deleted = other.deleted;
    }
}
