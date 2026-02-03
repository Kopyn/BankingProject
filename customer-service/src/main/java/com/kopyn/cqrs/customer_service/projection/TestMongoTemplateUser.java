package com.kopyn.cqrs.customer_service.projection;

import com.kopyn.cqrs.customer_service.command.domain.events.EventModel;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestMongoTemplateUser {
    public TestMongoTemplateUser(ReactiveMongoTemplate mongoTemplate) {
        mongoTemplate.changeStream(EventModel.class)
                .watchCollection("events")
                .listen()
                .doOnSubscribe(s -> System.out.println("subscribed"))
                .doOnNext(eventModelChangeStreamEvent ->
                        System.out.println("New event was inserted into the EventStore!"))
                .doOnError(err -> System.out.println("bad things"))
                .subscribe();
    }
}
