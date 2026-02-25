package com.kopyn.cqrs.customer_service.command.mapper;


import domain.events.Event;
import domain.events.EventModel;
import org.springframework.stereotype.Component;

@Component
public class EventModelMapper {
    public EventModel mapToEventModel(Event event) {
        return null;
    }
}
