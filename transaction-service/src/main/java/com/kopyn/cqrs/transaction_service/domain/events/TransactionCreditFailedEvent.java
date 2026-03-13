package com.kopyn.cqrs.transaction_service.domain.events;

import domain.events.Event;

public record TransactionCreditFailedEvent(

) implements Event {
    @Override
    public String getAggregateId() {
        return "";
    }

    @Override
    public int getAggregateVersion() {
        return 0;
    }
}
