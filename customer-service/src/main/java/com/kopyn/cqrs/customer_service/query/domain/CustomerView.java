package com.kopyn.cqrs.customer_service.query.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "customer_views")
@RequiredArgsConstructor
public class CustomerView {
    private final String uuid;
    private final String firstName;
    private String middleName;
    private final String lastName;
    private final LocalDate birthDate;
    private final String documentNumber;
}
