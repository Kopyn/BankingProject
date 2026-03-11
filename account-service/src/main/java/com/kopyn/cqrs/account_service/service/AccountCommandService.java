package com.kopyn.cqrs.account_service.service;

import com.kopyn.cqrs.account_service.api.commands.*;
import com.kopyn.cqrs.account_service.domain.AccountInfo;
import com.kopyn.cqrs.account_service.handlers.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountCommandService {

    private final CreateAccountCommandHandler createAccountCommandHandler;
    private final UpdateAccountCommandHandler updateAccountCommandHandler;
    private final DeleteAccountCommandHandler deleteAccountCommandHandler;
    private final DebitAccountCommandHandler debitAccountCommandHandler;
    private final CreditAccountCommandHandler creditAccountCommandHandler;

    public Mono<AccountInfo> createCustomer(AccountInfo accountInfo) {
        CreateAccountCommand cmd = new CreateAccountCommand(accountInfo);
        return createAccountCommandHandler.handle(cmd);
    }

    public Mono<AccountInfo> updateCustomer(UUID uuid, AccountInfo accountInfo) {
        accountInfo.setUuid(uuid.toString());
        UpdateAccountCommand cmd = new UpdateAccountCommand(uuid, accountInfo);
        return updateAccountCommandHandler.handle(cmd);
    }

    public Mono<AccountInfo> deleteCustomer(UUID uuid) {
        DeleteAccountCommand cmd = new DeleteAccountCommand(uuid);
        return deleteAccountCommandHandler.handle(cmd);
    }

    public Mono<AccountInfo> creditAccount(UUID accountId, long amount) {
        CreditAccountCommand cmd = new CreditAccountCommand(accountId, amount);
        return creditAccountCommandHandler.handle(cmd);
    }

    public Mono<AccountInfo> debitAccount(UUID accountId, long amount) {
        DebitAccountCommand cmd = new DebitAccountCommand(accountId, amount);
        return debitAccountCommandHandler.handle(cmd);
    }

}
