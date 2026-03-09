package com.kopyn.cqrs.customer_service.command.api.controller;

import com.kopyn.cqrs.customer_service.command.domain.CustomerInfo;
import com.kopyn.cqrs.customer_service.command.service.CustomerCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerCommandController {

    private final CustomerCommandService commandService;

    @PostMapping
    public Mono<CustomerInfo> createCustomer(@RequestBody CustomerInfo customerInfo) {
        return commandService.createCustomer(customerInfo);
    }

    @PutMapping(path = "/{customerId}")
    public Mono<CustomerInfo> updateCustomer(@PathVariable("customerId") UUID customerId, @RequestBody CustomerInfo customerInfo) {
        return commandService.updateCustomer(customerId, customerInfo)
                .onErrorResume(IllegalStateException ->
                        Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping(path = "/{customerId}")
    public Mono<CustomerInfo> deleteCustomer(@PathVariable("customerId") UUID customerId) {
        return commandService.deleteCustomer(customerId)
                .onErrorResume(IllegalStateException.class, ex ->
                        Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer was already deleted!")));
    }

}
