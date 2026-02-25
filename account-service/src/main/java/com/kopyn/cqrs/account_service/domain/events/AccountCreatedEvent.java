package com.kopyn.cqrs.account_service.domain.events;

import com.kopyn.cqrs.account_service.domain.AccountInfo;
import domain.events.Event;

public record AccountCreatedEvent (
        AccountInfo accountInfo
) implements Event {
    @Override
    public String getAggregateId() {
        return accountInfo.getUuid();
    }

    @Override
    public int getAggregateVersion() {
        return 0;
    }
}
