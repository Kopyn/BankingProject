package com.kopyn.cqrs.transaction_service.service.handlers;

import com.kopyn.cqrs.transaction_service.repository.TransactionRepository;
import domain.events.Event;
import domain.saga_commands.SagaCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SagaCommandHandler {
    private final TransactionRepository transactionRepository;

    // can still use AccountAggregate for logging purposes
    public Mono<Void> handle(SagaCommand command) {
        return transactionRepository.findTransactionById(command.aggregateId())
                .flatMap(transactionAggregate -> {
                    List<Event> producedEvents = transactionAggregate.process(command);
                    producedEvents.forEach(transactionAggregate::apply);
                    return transactionRepository.saveEvents(producedEvents)
                            .then();
                });
    }

}
