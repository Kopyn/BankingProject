package com.kopyn.cqrs.customer_service.command.repository;

import com.kopyn.cqrs.customer_service.command.domain.events.EventModel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
/*
  Class used for specific implementation of Event Store. It is only used for retrieving and saving Events!
  You shouldn't use domain specific classes here
 */
public interface EventStoreRepository extends ReactiveMongoRepository<EventModel, String> {
    Flux<EventModel> findByAggregateId(String aggregateId);
}
