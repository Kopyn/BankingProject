package com.kopyn.cqrs.account_service.handlers;

import com.kopyn.cqrs.account_service.api.commands.UpdateAccountCommand;
import com.kopyn.cqrs.account_service.domain.AccountInfo;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UpdateAccountCommandHandler {
    public Mono<AccountInfo> handle(UpdateAccountCommand command) {
        return Mono.empty();
    }
}
