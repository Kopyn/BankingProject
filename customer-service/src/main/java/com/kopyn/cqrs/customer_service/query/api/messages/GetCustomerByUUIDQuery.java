package com.kopyn.cqrs.customer_service.query.api.messages;

import java.util.UUID;

public record GetCustomerByUUIDQuery(
        UUID uuid
) implements Query {
}
