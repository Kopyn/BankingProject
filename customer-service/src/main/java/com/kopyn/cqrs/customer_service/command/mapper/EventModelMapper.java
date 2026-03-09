package com.kopyn.cqrs.customer_service.command.mapper;


import com.kopyn.cqrs.customer_service.command.domain.events.CustomerEventModel;
import domain.events.Event;
import org.springframework.stereotype.Component;

@Component
public class EventModelMapper {
    public CustomerEventModel mapToEventModel(Event event) {
        return null;
    }
}
