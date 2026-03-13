package com.kopyn.cqrs.transaction_service.domain;

import com.kopyn.cqrs.transaction_service.api.commands.OrderTransactionCommand;
import com.kopyn.cqrs.transaction_service.domain.events.TransactionInitiatedEvent;
import domain.events.Event;
import domain.saga_commands.SagaCommand;
import domain.saga_commands.account.CreditAccountSagaCommand;
import domain.saga_commands.account.DebitAccountSagaCommand;
import domain.saga_commands.account.RefundAccountSagaCommand;
import domain.saga_commands.transaction.AbortTransactionSagaCommand;
import domain.saga_commands.transaction.ContinueTransactionSagaCommand;
import domain.saga_commands.transaction.FinalizeTransactionSagaCommand;
import domain.saga_commands.transaction.RevokeTransactionSagaCommand;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionAggregate {
    private int version = -1;
    @Getter
    private TransactionInfo transactionInfo;

    List<Event> changes = new ArrayList<>();

    public List<Event> process(OrderTransactionCommand orderTransactionCommand) {
        TransactionInfo commandTransactionInfo = orderTransactionCommand.transactionInfo();
        commandTransactionInfo.setUuid(UUID.randomUUID().toString());
        Event transactionOrderedEvent = new TransactionInitiatedEvent(commandTransactionInfo);
        changes.add(transactionOrderedEvent);
        return List.of(transactionOrderedEvent);
    }

    // SAGA handling
    public List<Event> process(SagaCommand sagaCommand) throws IllegalStateException {
        return switch (sagaCommand) {
            case AbortTransactionSagaCommand abortTransaction -> process(abortTransaction);
            case ContinueTransactionSagaCommand continueTransaction -> process(continueTransaction);
            case FinalizeTransactionSagaCommand finalizeTransaction -> process(finalizeTransaction);
            case RevokeTransactionSagaCommand revokeTransaction -> process(revokeTransaction);
            default -> throw new IllegalStateException("Unknown SAGA command");
        };
    }

    // probably here should be "SomethingFailedEvent" to know the reason and return some result
    private List<Event> process(CreditAccountSagaCommand credSagaCmd) {
//        Event accountCreditedEvent = new AccountCreditedEvent(credSagaCmd.aggregateId(), version + 1,
//                credSagaCmd.amount(), transactionInfo.getBalance(),
//                transactionInfo.getBalance() + credSagaCmd.amount(), credSagaCmd.transactionId());
//        changes.add(accountCreditedEvent);
//        return List.of(accountCreditedEvent);
        return List.of();
    }

    private List<Event> process(DebitAccountSagaCommand debSagaCmd) {
//        Event accountDebitedEvent = new AccountDebitedEvent(debSagaCmd.aggregateId(), version + 1,
//                debSagaCmd.amount(), transactionInfo.getBalance(),
//                transactionInfo.getBalance() - debSagaCmd.amount(), debSagaCmd.transactionId());
//        changes.add(accountDebitedEvent);
//        return List.of(accountDebitedEvent);
        return List.of();
    }

    private List<Event> process(RefundAccountSagaCommand refSagaCmd) {
        return null;
    }

    public void apply(Event event) {
        version += 1;
        switch (event) {
//            case AccountCreatedEvent e -> apply(e);
//            case AccountUpdatedEvent e -> apply(e);
//            case AccountDeletedEvent ignored -> apply();
            case null, default -> throw new IllegalArgumentException("Unknown event type: " + event);
        }
    }

//    public void apply(AccountCreatedEvent event) {
//        transactionInfo = new TransactionInfo(event.tra());
//    }
//
//    public void apply(AccountUpdatedEvent event) {
//        transactionInfo = new AccountInfo(event.accountInfo());
//    }

}
