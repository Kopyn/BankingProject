package com.kopyn.cqrs.account_service.handlers;

import com.kopyn.cqrs.account_service.repository.AccountRepository;
import domain.events.Event;
import domain.saga_commands.SagaCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SagaCommandHandler {
    private final AccountRepository accountRepository;

    // can still use AccountAggregate for logging purposes
    public Mono<Void> handle(SagaCommand command) {
        return accountRepository.findAccountById(command.accountId())
                .flatMap(accountAggregate -> {
                    List<Event> producedEvents = accountAggregate.process(command);
                    producedEvents.forEach(accountAggregate::apply);
                    return accountRepository.saveEvents(producedEvents)
                            .then();
                });
    }


}
