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

    /*
     * This method is basically deposit and withdraw money mixed, just so it can be developed easily.
     * It's basically the same
     */
    @PutMapping(path = "{accountId}")
    public Mono<AccountInfo> updateAccount(@PathVariable UUID accountId, @RequestBody AccountInfo accountInfo) {
        return accountService.updateCustomer(accountId, accountInfo);
    }

    @DeleteMapping(path = "{accountId}")
    public Mono<AccountInfo> deleteAccount(@PathVariable UUID accountId) {
        return accountService.deleteCustomer(accountId);
    }
}
