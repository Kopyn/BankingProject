package com.kopyn.cqrs.account_service.repository;

import com.kopyn.cqrs.account_service.domain.AccountAggregate;
import com.kopyn.cqrs.account_service.domain.events.AccountEventModel;
import domain.events.Event;
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

    public Flux<AccountEventModel> saveEvents(List<Event> uncommitedEvents) {
        return Flux.fromIterable(uncommitedEvents)
                .map(event ->
                        new AccountEventModel(UUID.randomUUID().toString(), Instant.now(), event.getAggregateId(),
                                event, event.getAggregateVersion()))
                .flatMap(esRepository::save);
//        return Flux.empty();
    }

    public Mono<AccountAggregate> findAccountById(UUID uuid) {
        return esRepository.findByAggregateId(uuid.toString())
                .reduce(new AccountAggregate(), (accountAggregate, eventModel) -> {
                    accountAggregate.apply(eventModel.getEventData());
                    return accountAggregate;
                })
                .onErrorMap(throwable ->
                        new RuntimeException("Couldn't read aggregate events from the EventStore"));
    }
}
