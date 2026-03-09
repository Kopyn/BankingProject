package com.kopyn.cqrs.account_service.domain.events;

import com.kopyn.cqrs.account_service.domain.AccountInfo;
import domain.events.Event;

public record AccountUpdatedEvent (
        AccountInfo accountInfo,
        int accountVersion
) implements Event {
    @Override
    public String getAggregateId() {
        return accountInfo.getUuid();
    }

    @Override
    public int getAggregateVersion() {
        return accountVersion;
    }
}
