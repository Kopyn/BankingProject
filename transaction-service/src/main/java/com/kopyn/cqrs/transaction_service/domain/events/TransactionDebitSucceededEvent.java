package com.kopyn.cqrs.transaction_service.domain.events;

import com.kopyn.cqrs.transaction_service.domain.TransactionInfo;
import domain.events.Event;

import java.util.UUID;

public record TransactionDebitSucceededEvent(
        UUID transactionId,
        int aggregateVersion,
        TransactionInfo transactionInfo
) implements Event {
    @Override
    public String getAggregateId() {
        return transactionInfo.toString();
    }

    @Override
    public int getAggregateVersion() {
        return aggregateVersion;
    }
}
