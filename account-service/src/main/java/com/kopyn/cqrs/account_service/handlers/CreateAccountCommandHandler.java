package com.kopyn.cqrs.account_service.handlers;

import com.kopyn.cqrs.account_service.api.commands.CreateAccountCommand;
import com.kopyn.cqrs.account_service.domain.AccountInfo;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateAccountCommandHandler {
    public Mono<AccountInfo> handle(CreateAccountCommand command) {
        return Mono.empty();
    }
}
