package com.kopyn.cqrs.account_service.domain;

import com.kopyn.cqrs.account_service.api.commands.CreateAccountCommand;
import domain.events.Event;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountAggregate {
    private int version = -1;
    @Getter
    private AccountInfo customerInfo;

    List<Event> changes = new ArrayList<>();

    public List<Event> process(CreateAccountCommand createAccountCommand) {
        CustomerInfo commandCustomerInfo = createAccountCommand.customerInfo();
        commandCustomerInfo.setUuid(UUID.randomUUID().toString());
        Event customerCreatedEvent = new CustomerCreatedEvent(createAccountCommand.customerInfo());
        changes.add(customerCreatedEvent);
        return List.of(customerCreatedEvent);
    }

    public List<Event> process(UpdateCustomerCommand updateCustomerCommand) throws IllegalStateException {
        if (customerInfo.isDeleted()) {
            throw new IllegalStateException("Customer doesn't exist");
        }
        Event customerUpdatedEvent = new CustomerUpdatedEvent(updateCustomerCommand.customerInfo(), version + 1);
        changes.add(customerUpdatedEvent);
        return List.of(customerUpdatedEvent);
    }

    public List<Event> process() throws IllegalStateException {
        if (customerInfo.isDeleted()) {
            throw new IllegalStateException("Customer is already deleted");
        }
        Event customerDeletedEvent = new CustomerDeletedEvent(customerInfo, version + 1);
        changes.add(customerDeletedEvent);
        return List.of(customerDeletedEvent);
    }

    public void apply(Event event) {
        version += 1;
        if (event instanceof CustomerCreatedEvent e) {
            apply(e);
        } else if (event instanceof CustomerUpdatedEvent e) {
            apply(e);
        } else if (event instanceof CustomerDeletedEvent) {
            apply();
        } else {
            throw new IllegalArgumentException("Unknown event type: " + event);
        }
    }

    public void apply(CustomerCreatedEvent event) {
        customerInfo = new CustomerInfo(event.customerInfo());
    }

    public void apply(CustomerUpdatedEvent event) {
        customerInfo = new CustomerInfo(event.customerInfo());
    }

    public void apply() {
        customerInfo.setDeleted(true);
    }
}
