package com.kopyn.cqrs.account_service.api;

import com.kopyn.cqrs.account_service.domain.AccountInfo;
import com.kopyn.cqrs.account_service.service.AccountCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountCommandController {

    private final AccountCommandService accountService;

    @PostMapping
    public Mono<AccountInfo> createAccount(@RequestBody AccountInfo accountInfo) {
        return accountService.createCustomer(accountInfo);
    }

    @PutMapping(path = "{accountId}")
    public Mono<AccountInfo> updateAccount(@PathVariable UUID accountId, @RequestBody AccountInfo accountInfo) {
        return accountService.updateCustomer(accountId, accountInfo);
    }

    @PutMapping(path = "{accountId}")
    public Mono<AccountInfo> depositMoney(@PathVariable UUID accountId, long amount) {
        return accountService.creditAccount(accountId, amount, null);
    }

    @PutMapping(path = "{accountId}")
    public Mono<AccountInfo> withdrawMoney(@PathVariable UUID accountId, long amount) {
        return accountService.debitAccount(accountId, amount, null);
    }

    @DeleteMapping(path = "{accountId}")
    public Mono<AccountInfo> deleteAccount(@PathVariable UUID accountId) {
        return accountService.deleteCustomer(accountId);
    }
}
