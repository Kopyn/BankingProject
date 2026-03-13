package com.kopyn.cqrs.transaction_service.saga;

import com.kopyn.cqrs.transaction_service.service.handlers.SagaCommandHandler;
import domain.saga_commands.SagaCommand;
import domain.saga_commands.transaction.AbortTransactionSagaCommand;
import domain.saga_commands.transaction.ContinueTransactionSagaCommand;
import domain.saga_commands.transaction.FinalizeTransactionSagaCommand;
import domain.saga_commands.transaction.RevokeTransactionSagaCommand;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverRecord;

@Slf4j
@Component
public class SagaManager {

    private final SagaCommandHandler sagaCommandHandler;

    private final ReactiveKafkaConsumerTemplate<String, SagaCommand> transactionKafkaConsumerTemplate;
    private final ReactiveKafkaProducerTemplate<String, SagaCommand> accountKafkaProducerTemplate;

    public SagaManager(@Qualifier("accountCommandConsumer")
                       ReactiveKafkaConsumerTemplate<String, SagaCommand> transactionKafkaConsumerTemplate,
                       ReactiveKafkaProducerTemplate<String, SagaCommand> accountKafkaProducerTemplate,
                       SagaCommandHandler sagaCommandHandler) {
        this.transactionKafkaConsumerTemplate = transactionKafkaConsumerTemplate;
        this.accountKafkaProducerTemplate = accountKafkaProducerTemplate;
        this.sagaCommandHandler = sagaCommandHandler;
    }

    @PostConstruct
    public void startKafkaConsumer() {
        transactionMessagesFlux()
                .retry()
                .subscribe();
    }

    public Flux<SagaCommand> transactionMessagesFlux() {
        return transactionKafkaConsumerTemplate.receive()
                .map(ReceiverRecord::value)
                .doOnNext(this::handleTransactionSagaCommand)
                .doOnError(error -> log.error("Transaction error: {}", error.getMessage()));
    }

    private void handleTransactionSagaCommand(SagaCommand incomingCommand) {
        switch (incomingCommand) {
            case AbortTransactionSagaCommand abortCmd -> handleAbortTransactionSagaCommand(abortCmd);
            case ContinueTransactionSagaCommand continueCmd -> handleContinueTransactionSagaCommand(continueCmd);
            case RevokeTransactionSagaCommand revokeCmd -> handleRevokeTransactionSagaCommand(revokeCmd);
            case FinalizeTransactionSagaCommand finalizeCmd -> handleFinalizeTransactionSagaCommand(finalizeCmd);
            default -> log.error("Unknown SAGA command");
        }
    }

    private void handleAbortTransactionSagaCommand(AbortTransactionSagaCommand abortCmd) {
        System.out.println("debiting account");

        // load account to debit aggregate
        // check if there are sufficient funds in the account and if it can be debited
        // subtract the amount from account's balance
        sagaCommandHandler.handle(abortCmd).subscribe();

//        SagaCommand abortTransactionCommand = new AbortTransactionCommand(abortCmd.transactionId(),
//                "Some reason");
//        accountKafkaProducerTemplate.send("transaction_channel", abortTransactionCommand)
//                .doOnSuccess(result -> System.out.println("Sent: " + abortTransactionCommand))
//                .then();
    }

    private void handleContinueTransactionSagaCommand(ContinueTransactionSagaCommand continueCmd) {
    }

    private void handleRevokeTransactionSagaCommand(RevokeTransactionSagaCommand revokeCmd) {
    }

    private void handleFinalizeTransactionSagaCommand(FinalizeTransactionSagaCommand finalizeCmd) {
    }
}
