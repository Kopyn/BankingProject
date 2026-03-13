package com.kopyn.cqrs.transaction_service.domain.events;

import domain.events.Event;

import java.util.UUID;

public record TransactionCreditFailedEvent(
        UUID transactionId,
        int aggregateVersion,
        String reason
) implements Event {
    @Override
    public String getAggregateId() {
        return transactionId.toString();
    }

    @Override
    public int getAggregateVersion() {
        return aggregateVersion;
    }
}
