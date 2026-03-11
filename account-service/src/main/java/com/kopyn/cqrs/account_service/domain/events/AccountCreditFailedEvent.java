package com.kopyn.cqrs.account_service.domain.events;

import domain.events.Event;

import java.util.UUID;

public record AccountCreditFailedEvent (
        UUID accountId,
        int accountVersion,
        long amount,
        UUID transactionId,
        String reason
) implements Event {
    @Override
    public String getAggregateId() {
        return accountId.toString();
    }

    @Override
    public int getAggregateVersion() {
        return accountVersion;
    }
}
