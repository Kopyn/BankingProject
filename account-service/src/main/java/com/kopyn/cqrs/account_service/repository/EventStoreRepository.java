package com.kopyn.cqrs.account_service.repository;

import com.kopyn.cqrs.account_service.domain.events.AccountEventModel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EventStoreRepository extends ReactiveMongoRepository<AccountEventModel, String> {
    Flux<AccountEventModel> findByAggregateId(String aggregateId);
}
