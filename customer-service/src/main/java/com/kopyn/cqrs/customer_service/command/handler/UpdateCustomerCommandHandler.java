package com.kopyn.cqrs.customer_service.command.handler;

import com.kopyn.cqrs.customer_service.command.domain.CustomerAggregate;
import com.kopyn.cqrs.customer_service.command.domain.CustomerInfo;
import com.kopyn.cqrs.customer_service.command.domain.commands.UpdateCustomerCommand;
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
public class UpdateCustomerCommandHandler implements CommandHandler<UpdateCustomerCommand, CustomerInfo> {

    private final CustomerRepository customerRepository;

    @Override
    public Mono<CustomerInfo> handle(UpdateCustomerCommand command) {
        return customerRepository.findCustomerById(command.uuid())
                .flatMap(customerAggregate -> {
                    List<Event> producedEvents = customerAggregate.process(command);
                    producedEvents.forEach(customerAggregate::apply);
                    return customerRepository.saveEvents(producedEvents).then()
                            .thenReturn(customerAggregate);
                })
                .map(CustomerAggregate::getCustomerInfo);
    }

    @Override
    public Class<UpdateCustomerCommand> getCommandType() {
        return UpdateCustomerCommand.class;
    }
}
