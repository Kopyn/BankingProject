package com.kopyn.cqrs.customer_service.command.domain.events;

import com.kopyn.cqrs.customer_service.command.domain.CustomerInfo;
import domain.events.Event;

public record CustomerUpdatedEvent(
        CustomerInfo customerInfo,
        int customerVersion
) implements Event {
    @Override
    public String getAggregateId() {
        return customerInfo.getUuid();
    }

    @Override
    public int getAggregateVersion() {
        return customerVersion;
    }
}
