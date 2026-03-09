package com.kopyn.cqrs.customer_service.command.handler;

import com.kopyn.cqrs.customer_service.command.domain.CustomerAggregate;
import com.kopyn.cqrs.customer_service.command.domain.CustomerInfo;
import com.kopyn.cqrs.customer_service.command.domain.commands.CreateCustomerCommand;
import com.kopyn.cqrs.customer_service.command.repository.CustomerRepository;
import domain.events.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateCustomerCommandHandler implements CommandHandler<CreateCustomerCommand, CustomerInfo> {

    private final CustomerRepository customerRepository;

    @Override
    public Mono<CustomerInfo> handle(CreateCustomerCommand command) {
        return Mono.just(new CustomerAggregate())
                .flatMap(customerAggregate -> {
            List<Event> producedEvents = customerAggregate.process(command);
            producedEvents.forEach(customerAggregate::apply);
            return customerRepository.saveEvents(producedEvents)
                    .then().thenReturn(customerAggregate);
        }).map(CustomerAggregate::getCustomerInfo);
    }

    @Override
    public Class<CreateCustomerCommand> getCommandType() {
        return CreateCustomerCommand.class;
    }
}
