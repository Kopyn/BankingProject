package com.kopyn.cqrs.customer_service.query.api.controller;

import com.kopyn.cqrs.customer_service.query.domain.CustomerView;
import com.kopyn.cqrs.customer_service.query.service.CustomerQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerQueryController {

    private final CustomerQueryService queryService;

    @GetMapping("/{uuid}")
    public Mono<CustomerView> getCustomerById(@PathVariable UUID uuid) {
        return queryService.getCustomerByUUID(uuid);
    }

    @GetMapping
    public Flux<CustomerView> getAllCustomers() {
        return queryService.getAllCustomers();
    }
}
