package com.kopyn.cqrs.transaction_service.service.handlers;

import com.kopyn.cqrs.transaction_service.api.commands.OrderTransactionCommand;
import com.kopyn.cqrs.transaction_service.domain.TransactionAggregate;
import com.kopyn.cqrs.transaction_service.domain.TransactionInfo;
import com.kopyn.cqrs.transaction_service.repository.TransactionRepository;
import domain.events.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderTransactionCommandHandler {

    private final TransactionRepository accountRepository;

    public Mono<TransactionInfo> handle(OrderTransactionCommand command) {
        return Mono.just(new TransactionAggregate())
                .flatMap(transactionAggregate -> {
                    List<Event> producedEvents = transactionAggregate.process(command);
                    producedEvents.forEach(transactionAggregate::apply);
                    return accountRepository.saveEvents(producedEvents)
                            .then().thenReturn(transactionAggregate);
                }).map(TransactionAggregate::getTransactionInfo);
    }
}
