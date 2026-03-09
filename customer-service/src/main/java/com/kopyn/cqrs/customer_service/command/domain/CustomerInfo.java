package com.kopyn.cqrs.customer_service.command.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class CustomerInfo {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String uuid;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private String documentNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean deleted; // proper would be some status CREATED, DELETED


    public CustomerInfo(String uuid, String firstName, String middleName, String lastName, LocalDate birthDate,
                        String documentNumber, boolean deleted) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.documentNumber = documentNumber;
        this.deleted = deleted;
    }

    public CustomerInfo(CustomerInfo other) {
        this.uuid = other.uuid;
        this.firstName = other.firstName;
        this.middleName = other.middleName;
        this.lastName = other.lastName;
        this.birthDate = other.birthDate;
        this.documentNumber = other.documentNumber;
        this.deleted = other.deleted;
    }
}
