package com.kopyn.cqrs.customer_service.query.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "customer_views")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CustomerView {
    @Id
    private String customerId;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private String documentNumber;
}
