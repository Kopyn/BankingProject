package com.kopyn.cqrs.account_service.handlers;

import com.kopyn.cqrs.account_service.api.commands.DeleteAccountCommand;
import com.kopyn.cqrs.account_service.domain.AccountInfo;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DeleteAccountCommandHandler {
    public Mono<AccountInfo> handle(DeleteAccountCommand command) {
        return Mono.empty();
    }
}
