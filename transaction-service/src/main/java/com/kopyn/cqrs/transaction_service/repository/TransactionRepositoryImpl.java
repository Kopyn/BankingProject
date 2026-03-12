package com.kopyn.cqrs.transaction_service.repository;

import com.kopyn.cqrs.transaction_service.domain.TransactionAggregate;
import com.kopyn.cqrs.transaction_service.domain.event_model.TransactionEventModel;
import domain.events.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

// TODO
@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final EventStoreRepository esRepository;

    @Override
    public Flux<TransactionEventModel> saveEvents(List<Event> uncommitedEvents) {
        return Flux.fromIterable(uncommitedEvents)
                .map(event ->
                        new TransactionEventModel())
                .flatMap(esRepository::save);
    }

    @Override
    public Mono<TransactionAggregate> findTransactionById(UUID uuid) {
        return esRepository.findByAggregateId(uuid.toString())
                .reduce(new TransactionAggregate(), (transactionAggregate,
                                                     eventModel) -> {
//                    transactionAggregate.apply(eventModel.getEventData());
                    return transactionAggregate;
                })
                .onErrorMap(throwable ->
                        new RuntimeException("Couldn't read aggregate events from the EventStore"));
    }
}
