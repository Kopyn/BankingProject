package com.kopyn.cqrs.account_service.repository;

import domain.events.EventModel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EventStoreRepository extends ReactiveMongoRepository<EventModel, String> {
    Flux<EventModel> findByAggregateId(String aggregateId);
}
