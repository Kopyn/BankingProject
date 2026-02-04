package com.kopyn.cqrs.customer_service.query.service;

import com.kopyn.cqrs.customer_service.query.api.messages.GetAllCustomersQuery;
import com.kopyn.cqrs.customer_service.query.api.messages.GetCustomerByUUIDQuery;
import com.kopyn.cqrs.customer_service.query.domain.CustomerView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerQueryService {

    private final CustomerQueryBus queryBus;

    public Mono<CustomerView> getCustomerByUUID(UUID uuid) {
        return Mono.from(queryBus.handleQuery(new GetCustomerByUUIDQuery(uuid)));
    }

    public Flux<CustomerView> getAllCustomers() {
        return Flux.from(queryBus.handleQuery(new GetAllCustomersQuery()));
    }
}
