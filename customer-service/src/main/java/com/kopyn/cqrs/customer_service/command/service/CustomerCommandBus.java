package com.kopyn.cqrs.customer_service.command.service;

import com.kopyn.cqrs.customer_service.command.domain.CustomerInfo;
import com.kopyn.cqrs.customer_service.command.domain.commands.Command;
import com.kopyn.cqrs.customer_service.command.handler.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CustomerCommandBus {

    Map<Class<Command>, CommandHandler<Command, CustomerInfo>> handlers = new HashMap<>();

    public CustomerCommandBus(List<CommandHandler<?, ?>> handlers) {
        handlers.forEach(this::registerHandler);
    }

    @SuppressWarnings("unchecked")
    public void registerHandler(CommandHandler<?, ?> handler) {
        log.info("Registering command handler: " + handler.getClass());
        handlers.put(
                (Class<Command>) handler.getCommandType(), (CommandHandler<Command, CustomerInfo>) handler
        );
    }

    public Mono<CustomerInfo> handle(Command command) {
        return handlers.get(command.getClass()).handle(command);
    }

}
