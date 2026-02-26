package com.kopyn.cqrs.customer_service.projection;

import com.kopyn.cqrs.customer_service.command.domain.CustomerInfo;
import com.kopyn.cqrs.customer_service.command.domain.events.*;
import com.kopyn.cqrs.customer_service.query.domain.CustomerView;
import domain.events.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@Component
public class MongoChangeStreamsProjectionHandler {

    private final ReactiveMongoTemplate mongoTemplate;

    public MongoChangeStreamsProjectionHandler(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;

        mongoTemplate.changeStream(CustomerEventModel.class)
                .watchCollection("customer_events")
                .listen()
                .doOnSubscribe(s -> log.info("Subscribed to MongoDB Change Streams"))
                .flatMap(this::handleChangeEvent)
                .doOnError(err -> log.error("Error in change stream", err))
                .subscribe();
    }

    private Mono<?> handleChangeEvent(ChangeStreamEvent<CustomerEventModel> changeEvent) {

        CustomerEventModel body = changeEvent.getBody();
        if (body == null) {
            return Mono.empty();
        }

        Event event = body.getEventData();
        String aggregateId = event.getAggregateId();

        if (event instanceof CustomerDeletedEvent) {
            return mongoTemplate.remove(
                    Query.query(Criteria.where("_id").is(aggregateId)),
                    CustomerView.class
            ).doOnSuccess(r -> log.info("Deleted read model for {}", aggregateId));
        }

        return mongoTemplate.findById(aggregateId, CustomerView.class)
                .defaultIfEmpty(new CustomerView())
                .map(existing -> applyEvent(existing, event))
                .flatMap(mongoTemplate::save)
                .doOnSuccess(saved -> log.info("Updated read model for {}", aggregateId));
    }

    private CustomerView applyEvent(CustomerView model, Event event) {

        if (event instanceof CustomerCreatedEvent) {
            CustomerInfo info = ((CustomerCreatedEvent) event).customerInfo();
            model.setFirstName(info.getFirstName());
            model.setMiddleName(info.getMiddleName());
            model.setLastName(info.getLastName());
            model.setBirthDate(info.getBirthDate());
            model.setCustomerId(info.getUuid());
            model.setDocumentNumber(info.getDocumentNumber());
        }

        if (event instanceof CustomerUpdatedEvent) {
            CustomerInfo info = ((CustomerUpdatedEvent) event).customerInfo();
            model.setFirstName(info.getFirstName());
            model.setMiddleName(info.getMiddleName());
            model.setLastName(info.getLastName());
            model.setBirthDate(info.getBirthDate());
            model.setCustomerId(info.getUuid());
            model.setDocumentNumber(info.getDocumentNumber());

        }

        return model;
    }
}

