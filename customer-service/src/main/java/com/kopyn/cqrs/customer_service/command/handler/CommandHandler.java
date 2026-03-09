package com.kopyn.cqrs.customer_service.command.handler;

import reactor.core.publisher.Mono;

public interface CommandHandler<C, R> {
    Mono<R> handle(C command);
    Class<C> getCommandType();
}
