package com.kopyn.cqrs.transaction_service.repository;

import com.kopyn.cqrs.transaction_service.domain.event_model.TransactionEventModel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EventStoreRepository extends ReactiveMongoRepository<TransactionEventModel, String> {
    Flux<TransactionEventModel> findByAggregateId(String aggregateId);
}
