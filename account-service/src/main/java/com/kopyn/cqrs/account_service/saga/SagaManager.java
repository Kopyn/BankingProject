package com.kopyn.cqrs.account_service.saga;

import com.kopyn.cqrs.account_service.handlers.SagaCommandHandler;
import domain.saga_commands.SagaCommand;
import domain.saga_commands.account.CreditAccountSagaCommand;
import domain.saga_commands.account.DebitAccountSagaCommand;
import domain.saga_commands.account.RefundAccountSagaCommand;
import domain.saga_commands.transaction.ContinueTransactionSagaCommand;
import domain.saga_commands.transaction.FinalizeTransactionSagaCommand;
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

    private final ReactiveKafkaConsumerTemplate<String, String> customersKafkaConsumerTemplate;
    private final ReactiveKafkaConsumerTemplate<String, SagaCommand> accountKafkaConsumerTemplate;
    private final ReactiveKafkaProducerTemplate<String, SagaCommand> transactionsKafkaProducerTemplate;

    public SagaManager(@Qualifier("customerCommandConsumer")
                       ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate,
                       @Qualifier("accountCommandConsumer")
                       ReactiveKafkaConsumerTemplate<String, SagaCommand> accountKafkaConsumerTemplate,
                       ReactiveKafkaProducerTemplate<String, SagaCommand> transactionsKafkaProducerTemplate,
                       SagaCommandHandler sagaCommandHandler) {
        this.customersKafkaConsumerTemplate = reactiveKafkaConsumerTemplate;
        this.accountKafkaConsumerTemplate = accountKafkaConsumerTemplate;
        this.transactionsKafkaProducerTemplate = transactionsKafkaProducerTemplate;
        this.sagaCommandHandler = sagaCommandHandler;
    }

    @PostConstruct
    public void startKafkaConsumer() {
        customerMessagesFlux()
                .retry()
                .subscribe();

        accountMessagesFlux()
                .retry()
                .subscribe();
    }

    public Flux<String> customerMessagesFlux() {
        return customersKafkaConsumerTemplate.receive()
                .map(ReceiverRecord::value)
                .doOnNext(System.out::println)
                .doOnError(error -> log.error("Consumer error: {}", error.getMessage()));
    }

    public Flux<SagaCommand> accountMessagesFlux() {
        return accountKafkaConsumerTemplate.receive()
                .map(ReceiverRecord::value)
                .doOnNext(this::handleTransactionSagaCommand)
                .doOnError(error -> log.error("Transaction error: {}", error.getMessage()));
    }

    private void handleTransactionSagaCommand(SagaCommand incomingCommand) {
        switch (incomingCommand) {
            case DebitAccountSagaCommand debCmd -> handleDebitAccountSagaCommand(debCmd);
            case CreditAccountSagaCommand credCmd -> handleCreditAccountSagaCommand(credCmd);
            case RefundAccountSagaCommand refCmd -> handleRefundAccountSagaCommand(refCmd);
            default -> log.error("Unknown SAGA command");
        }
    }

    private void handleDebitAccountSagaCommand(DebitAccountSagaCommand debCmd) {
        System.out.println("debiting account");

        // load account to debit aggregate
        // check if there are sufficient funds in the account and if it can be debited
        // subtract the amount from account's balance
        sagaCommandHandler.handle(debCmd).subscribe();

        SagaCommand continueTransactionCommand = new ContinueTransactionSagaCommand(debCmd.transactionId(),
                debCmd.creditAccount());
        transactionsKafkaProducerTemplate.send("transaction_channel", continueTransactionCommand)
                .doOnSuccess(result -> System.out.println("Sent: " + continueTransactionCommand))
                .then();
    }

    private void handleCreditAccountSagaCommand(CreditAccountSagaCommand credCmd) {
        System.out.println("crediting account");

        // load account to credit aggregate
        // check if the account can be credited
        // add the amount from account's balance
        sagaCommandHandler.handle(credCmd).subscribe();

        SagaCommand finalizeTransactionCommand = new FinalizeTransactionSagaCommand(credCmd.transactionId());
        transactionsKafkaProducerTemplate.send("transaction_channel", finalizeTransactionCommand)
                .doOnSuccess(result -> System.out.println("Sent: " + finalizeTransactionCommand))
                .then();
    }

    private void handleRefundAccountSagaCommand(RefundAccountSagaCommand refCmd) {
        System.out.println("Refunding account");
        // TODO - initial implementation does not handle failed SAGAs
    }
}
