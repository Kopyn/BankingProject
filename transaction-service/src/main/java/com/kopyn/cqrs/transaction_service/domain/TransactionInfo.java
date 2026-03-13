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
    private String debitAccountId;
    private String creditAccountId;
    private long amount;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private TransactionStatus status;

    public TransactionInfo(String uuid, String debitAccountId, String creditAccountId, long amount,
                           TransactionStatus status) {
        this.uuid = uuid;
        this.debitAccountId = debitAccountId;
        this.creditAccountId = creditAccountId;
        this.amount = amount;
        this.status = status;
    }

    public TransactionInfo(TransactionInfo other) {
        this.uuid = other.uuid;
        this.debitAccountId = other.debitAccountId;
        this.creditAccountId = other.creditAccountId;
        this.amount = other.amount;
        this.status = other.status;
    }
}
