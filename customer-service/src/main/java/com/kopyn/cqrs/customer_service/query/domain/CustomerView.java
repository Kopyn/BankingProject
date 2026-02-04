package com.kopyn.cqrs.customer_service.query.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "customer_views")
@AllArgsConstructor
@ToString
@Getter
public class CustomerView {
    private final String uuid;
    private final String firstName;
    private String middleName;
    private final String lastName;
    private final LocalDate birthDate;
    private final String documentNumber;
}
