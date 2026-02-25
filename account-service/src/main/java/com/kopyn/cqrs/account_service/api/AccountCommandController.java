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
    public Mono<Void> createAccount() {
        return null;
    }

    /**
     * This method is basically deposit and withdraw money mixed, just so it can be developed easily.
     * It's basically the same
     * @param accountId
     * @param accountInfo
     * @return
     */
    @PutMapping(path = "{accountId}")
    public Mono<Void> updateAccount(@PathVariable UUID accountId, AccountInfo accountInfo) {
        return null;
    }

    @DeleteMapping(path = "{accountId}")
    public Mono<Void> deleteAccount(@PathVariable UUID accountId) {
        return null;
    }
}
