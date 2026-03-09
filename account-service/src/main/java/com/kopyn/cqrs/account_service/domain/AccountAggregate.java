package com.kopyn.cqrs.account_service.domain;

import com.kopyn.cqrs.account_service.api.commands.CreateAccountCommand;
import com.kopyn.cqrs.account_service.api.commands.UpdateAccountCommand;
import com.kopyn.cqrs.account_service.domain.events.AccountCreatedEvent;
import com.kopyn.cqrs.account_service.domain.events.AccountDeletedEvent;
import com.kopyn.cqrs.account_service.domain.events.AccountUpdatedEvent;
import domain.events.Event;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountAggregate {
    private int version = -1;
    @Getter
    private AccountInfo accountInfo;

    List<Event> changes = new ArrayList<>();

    public List<Event> process(CreateAccountCommand createAccountCommand) {
        AccountInfo commandAccountInfo = createAccountCommand.accountInfo();
        commandAccountInfo.setUuid(UUID.randomUUID().toString());
        Event customerCreatedEvent = new AccountCreatedEvent(createAccountCommand.accountInfo());
        changes.add(customerCreatedEvent);
        return List.of(customerCreatedEvent);
    }

    public List<Event> process(UpdateAccountCommand updateAccountCommand) throws IllegalStateException {
        if (accountInfo.isDeleted()) {
            throw new IllegalStateException("Account doesn't exist");
        }
        Event accountUpdatedEvent = new AccountUpdatedEvent(updateAccountCommand.accountInfo(),
                version + 1);
        changes.add(accountUpdatedEvent);
        return List.of(accountUpdatedEvent);
    }

    public List<Event> process() throws IllegalStateException {
        if (accountInfo.isDeleted()) {
            throw new IllegalStateException("Account is already deleted");
        }
        Event accountDeletedEvent = new AccountDeletedEvent(accountInfo, version + 1);
        changes.add(accountDeletedEvent);
        return List.of(accountDeletedEvent);
    }

    public void apply(Event event) {
        version += 1;
        if (event instanceof AccountCreatedEvent e) {
            apply(e);
        } else if (event instanceof AccountUpdatedEvent e) {
            apply(e);
        } else if (event instanceof AccountDeletedEvent) {
            apply();
        } else {
            throw new IllegalArgumentException("Unknown event type: " + event);
        }
    }

    public void apply(AccountCreatedEvent event) {
        accountInfo = new AccountInfo(event.accountInfo());
    }

    public void apply(AccountUpdatedEvent event) {
        accountInfo = new AccountInfo(event.accountInfo());
    }

    public void apply() {
        accountInfo.setDeleted(true);
    }
}
