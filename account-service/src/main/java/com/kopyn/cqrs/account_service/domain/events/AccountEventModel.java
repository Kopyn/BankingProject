package com.kopyn.cqrs.account_service.domain.events;

import domain.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "account_events")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountEventModel {
    private String eventId;
    private Instant eventTimestamp;
    private String aggregateId;
    @Getter
    private Event eventData;
    private int aggregateVersion;
}
