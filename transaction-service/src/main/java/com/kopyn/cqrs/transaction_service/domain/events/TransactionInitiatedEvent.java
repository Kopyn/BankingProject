package com.kopyn.cqrs.transaction_service.domain.events;

import com.kopyn.cqrs.transaction_service.domain.TransactionInfo;
import domain.events.Event;

public record TransactionInitiatedEvent(
        TransactionInfo transactionInfo
) implements Event {
    @Override
    public String getAggregateId() {
        return transactionInfo.getUuid();
    }

    @Override
    public int getAggregateVersion() {
        return 0;
    }
}
