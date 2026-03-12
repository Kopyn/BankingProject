package com.kopyn.cqrs.account_service.domain;

import com.kopyn.cqrs.account_service.api.commands.CreateAccountCommand;
import com.kopyn.cqrs.account_service.api.commands.CreditAccountCommand;
import com.kopyn.cqrs.account_service.api.commands.DebitAccountCommand;
import com.kopyn.cqrs.account_service.api.commands.UpdateAccountCommand;
import com.kopyn.cqrs.account_service.domain.events.*;
import domain.events.Event;
import domain.saga_commands.SagaCommand;
import domain.saga_commands.account.CreditAccountSagaCommand;
import domain.saga_commands.account.DebitAccountSagaCommand;
import domain.saga_commands.account.RefundAccountSagaCommand;
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

    public List<Event> process(DebitAccountCommand debitAccountCommand) {
        if (accountInfo.isDeleted()) {
            throw new IllegalStateException("Account doesn't exist");
        }
        Event accountDebitedEvent = new AccountDebitedEvent(debitAccountCommand.accountId(), version + 1,
                debitAccountCommand.amount(), accountInfo.getBalance(),
                accountInfo.getBalance() - debitAccountCommand.amount(), null);
        changes.add(accountDebitedEvent);
        return List.of(accountDebitedEvent);
    }

    public List<Event> process(CreditAccountCommand creditAccountCommand) {
        if (accountInfo.isDeleted()) {
            throw new IllegalStateException("Account doesn't exist");
        }
        Event accountCreditedEvent = new AccountCreditedEvent(creditAccountCommand.accountId(), version + 1,
                creditAccountCommand.amount(), accountInfo.getBalance(),
                accountInfo.getBalance() + creditAccountCommand.amount(), null);
        changes.add(accountCreditedEvent);
        return List.of(accountCreditedEvent);
    }

    // SAGA handling
    public List<Event> process(SagaCommand sagaCommand) throws IllegalStateException {
        if (accountInfo.isDeleted()) {
            throw new IllegalStateException("Account is already deleted");
        }
        return switch (sagaCommand) {
            case CreditAccountSagaCommand cred -> process(cred);
            case DebitAccountSagaCommand deb -> process(deb);
            case RefundAccountSagaCommand ref -> process(ref);
            default -> throw new IllegalStateException("Unknown SAGA command");
        };
    }

    private List<Event> process(CreditAccountSagaCommand credSagaCmd) {
        return null;
    }

    private List<Event> process(DebitAccountSagaCommand debSagaCmd) {
        return null;
    }

    private List<Event> process(RefundAccountSagaCommand refSagaCmd) {
        return null;
    }

    public void apply(Event event) {
        version += 1;
        switch (event) {
            case AccountCreatedEvent e -> apply(e);
            case AccountUpdatedEvent e -> apply(e);
            case AccountDeletedEvent ignored -> apply();
            case null, default -> throw new IllegalArgumentException("Unknown event type: " + event);
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
