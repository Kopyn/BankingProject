package com.kopyn.cqrs.customer_service.command.handler;

import com.kopyn.cqrs.customer_service.command.domain.CustomerAggregate;
import com.kopyn.cqrs.customer_service.command.domain.CustomerInfo;
import com.kopyn.cqrs.customer_service.command.domain.commands.DeleteCustomerCommand;
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
public class DeleteCustomerCommandHandler implements CommandHandler<DeleteCustomerCommand, CustomerInfo> {

    private final CustomerRepository customerRepository;

    @Override
    public Mono<CustomerInfo> handle(DeleteCustomerCommand command) {
        return customerRepository.findCustomerById(command.uuid())
                .flatMap(customerAggregate -> {
                    List<Event> producedEvents = customerAggregate.process();
                    producedEvents.forEach(customerAggregate::apply);
                    return customerRepository.saveEvents(producedEvents).then()
                            .thenReturn(customerAggregate);
                })
                .map(CustomerAggregate::getCustomerInfo);
    }

    @Override
    public Class<DeleteCustomerCommand> getCommandType() {
        return DeleteCustomerCommand.class;
    }
}
