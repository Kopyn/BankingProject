package com.kopyn.cqrs.account_service.repository;

import domain.events.Event;
import domain.events.EventModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountRepository {

    private final EventStoreRepository esRepository;

    public Flux<EventModel> saveEvents(List<Event> uncommitedEvents) {
        return Flux.fromIterable(uncommitedEvents)
                .map(event ->
                        new EventModel(UUID.randomUUID().toString(), Instant.now(), event.getAggregateId(),
                                event, event.getAggregateVersion()))
                .flatMap(esRepository::save);
    }

    public Mono<AccountAggregate> findCustomerById(UUID uuid) {
        return esRepository.findByAggregateId(uuid.toString())
                .reduce(new AccountAggregate(), (accountAggregate, eventModel) -> {
                    accountAggregate.apply(eventModel.getEventData());
                    return accountAggregate;
                })
                .onErrorMap(throwable ->
                        new RuntimeException("Couldn't read aggregate events from the EventStore"));
    }
}
