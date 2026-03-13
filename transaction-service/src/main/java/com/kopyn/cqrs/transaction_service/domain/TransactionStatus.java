package com.kopyn.cqrs.transaction_service.domain;

public enum TransactionStatus {
    INITIALIZED, ACCOUNT_DEBITED, FINISHED, CANCELLED, REVOKED
}
