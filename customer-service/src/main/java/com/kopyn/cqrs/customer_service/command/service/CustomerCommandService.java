package com.kopyn.cqrs.customer_service.command.service;

import com.kopyn.cqrs.customer_service.command.domain.CustomerInfo;
import com.kopyn.cqrs.customer_service.command.domain.commands.CreateCustomerCommand;
import com.kopyn.cqrs.customer_service.command.domain.commands.DeleteCustomerCommand;
import com.kopyn.cqrs.customer_service.command.domain.commands.UpdateCustomerCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerCommandService {

    private final CustomerCommandBus commandBus;

    public Mono<CustomerInfo> createCustomer(CustomerInfo customerInfo) {
        CreateCustomerCommand cmd = new CreateCustomerCommand(customerInfo);
        return commandBus.handle(cmd);
    }

    public Mono<CustomerInfo> updateCustomer(UUID uuid, CustomerInfo customerInfo) {
        customerInfo.setUuid(uuid.toString());
        UpdateCustomerCommand cmd = new UpdateCustomerCommand(uuid, customerInfo);
        return commandBus.handle(cmd);
    }

    public Mono<CustomerInfo> deleteCustomer(UUID uuid) {
        DeleteCustomerCommand cmd = new DeleteCustomerCommand(uuid);
        return commandBus.handle(cmd);
    }

}
