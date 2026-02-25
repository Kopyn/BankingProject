package com.kopyn.cqrs.customer_service.projection;

import com.kopyn.cqrs.customer_service.command.domain.CustomerInfo;
import com.kopyn.cqrs.customer_service.command.domain.events.*;
import com.kopyn.cqrs.customer_service.query.domain.CustomerView;
import domain.events.Event;
import domain.events.EventModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class MongoChangeStreamsProjectionHandler {
    public MongoChangeStreamsProjectionHandler(ReactiveMongoTemplate mongoTemplate) {
        mongoTemplate.changeStream(EventModel.class)
                .watchCollection("events")
                .listen()
                .doOnSubscribe(s -> log.info("Subscribed to MongoDB Change Streams with: " + s.toString()))
                .flatMap(changeEvent ->
                {
                    Event customerEvent = Objects.requireNonNull(changeEvent.getBody()).getEventData();
                    return mongoTemplate.save(updateDb(customerEvent));
                })
                .doOnError(err -> log.error(err.toString()))
                .subscribe();
    }

    private Mono<CustomerView> updateDb(Event event) {
        if (event instanceof CustomerCreatedEvent e) {
            return updateDbOnCustomerCreated(e);
        } else if (event instanceof CustomerUpdatedEvent e) {
            return updateDbOnCustomerUpdated(e);
        } else if (event instanceof CustomerDeletedEvent e) {
            return updateDbOnCustomerDeleted(e);
        } else {
            throw new IllegalArgumentException("Unknown event type: " + event);
        }
    }

    private Mono<CustomerView> updateDbOnCustomerCreated(CustomerCreatedEvent e) {
        CustomerInfo customerInfo = e.customerInfo();
        CustomerView view = new CustomerView(customerInfo.getUuid(), customerInfo.getFirstName(),
                customerInfo.getMiddleName(), customerInfo.getLastName(), customerInfo.getBirthDate(),
                customerInfo.getDocumentNumber());
        return Mono.just(view);

    }

    private Mono<CustomerView> updateDbOnCustomerUpdated(CustomerUpdatedEvent e) {
        CustomerInfo customerInfo = e.customerInfo();
        CustomerView view = new CustomerView(customerInfo.getUuid(), customerInfo.getFirstName(),
                customerInfo.getMiddleName(), customerInfo.getLastName(), customerInfo.getBirthDate(),
                customerInfo.getDocumentNumber());
        return Mono.just(view);
    }

    private Mono<CustomerView> updateDbOnCustomerDeleted(CustomerDeletedEvent e) {
        CustomerInfo customerInfo = e.customerInfo();
        CustomerView view = new CustomerView(customerInfo.getUuid(), customerInfo.getFirstName(),
                customerInfo.getMiddleName(), customerInfo.getLastName(), customerInfo.getBirthDate(),
                customerInfo.getDocumentNumber());
        return Mono.just(view);
    }
}
