package com.kopyn.cqrs.customer_service.command.repository;

import com.kopyn.cqrs.customer_service.command.domain.CustomerAggregate;
import com.kopyn.cqrs.customer_service.command.domain.events.Event;
import com.kopyn.cqrs.customer_service.command.domain.events.EventModel;
import com.kopyn.cqrs.customer_service.command.mapper.EventModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Wrapper class for EventStore repository. This class is introduced to abstract specific event store
 * implementation via EventStoreRepository interface. This allows changing event store from KurrentDB to MongoDB,
 * Relational database or any other data store that is sufficient for event store.
 */
@RequiredArgsConstructor
@Repository
public class CustomerRepository {

    private final EventStoreRepository esRepository;
    private final EventModelMapper mapper;

    public Flux<EventModel> saveEvents(List<Event> uncommitedEvents) {
        return Flux.fromIterable(uncommitedEvents)
                .map(event ->
                                new EventModel(UUID.randomUUID().toString(), Instant.now(), event.getAggregateId(),
                                        event, event.getAggregateVersion()))
                .flatMap(esRepository::save);
    }

    public Mono<CustomerAggregate> findCustomerById(UUID uuid) {
        return esRepository.findByAggregateId(uuid.toString())
                .reduce(new CustomerAggregate(), (customerAggregate, eventModel) -> {
                    customerAggregate.apply(eventModel.getEventData());
                    return customerAggregate;
                })
                .onErrorMap(throwable ->
                        new RuntimeException("Couldn't read aggregate events from the EventStore"));
    }

}
