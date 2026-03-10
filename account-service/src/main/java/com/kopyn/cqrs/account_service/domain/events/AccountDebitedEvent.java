package com.kopyn.cqrs.account_service.domain.events;

import domain.events.Event;

import java.util.UUID;

public record AccountDebitedEvent (
        UUID accountId,
        int accountVersion,
        long amount,
        long previousBalance,
        long newBalance,
        UUID transactionId
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
