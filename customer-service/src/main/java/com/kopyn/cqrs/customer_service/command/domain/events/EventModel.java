package com.kopyn.cqrs.customer_service.command.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * This class is representation of events in the event store - other events are internal application representation
 * this will be unified for all events
 */
@Document(collection = "events")
@AllArgsConstructor
@ToString
public class EventModel {
    private String eventId;
    private Instant eventTimestamp;
    private String aggregateId;
    @Getter
    private Event eventData;
    private int aggregateVersion;

}
