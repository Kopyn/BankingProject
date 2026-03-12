package com.kopyn.cqrs.transaction_service.repository;

import com.kopyn.cqrs.transaction_service.domain.TransactionAggregate;
import com.kopyn.cqrs.transaction_service.domain.event_model.TransactionEventModel;
import domain.events.Event;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {

    Flux<TransactionEventModel> saveEvents(List<Event> uncommitedEvents);

    Mono<TransactionAggregate> findTransactionById(UUID uuid);
}
