package com.kopyn.cqrs.account_service.handlers;

import com.kopyn.cqrs.account_service.api.commands.CreateAccountCommand;
import com.kopyn.cqrs.account_service.api.commands.CreditAccountCommand;
import com.kopyn.cqrs.account_service.domain.AccountAggregate;
import com.kopyn.cqrs.account_service.domain.AccountInfo;
import com.kopyn.cqrs.account_service.repository.AccountRepository;
import domain.events.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreditAccountCommandHandler {
    private final AccountRepository accountRepository;

    public Mono<AccountInfo> handle(CreditAccountCommand command) {
        return accountRepository.findAccountById(command.accountId())
                .flatMap(accountAggregate -> {
                    List<Event> producedEvents = accountAggregate.process(command);
                    producedEvents.forEach(accountAggregate::apply);
                    return accountRepository.saveEvents(producedEvents)
                            .then().thenReturn(accountAggregate);
                }).map(AccountAggregate::getAccountInfo);
    }
}
