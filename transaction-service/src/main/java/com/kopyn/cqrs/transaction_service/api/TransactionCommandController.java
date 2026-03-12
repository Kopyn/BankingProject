package com.kopyn.cqrs.transaction_service.api;

import com.kopyn.cqrs.transaction_service.domain.TransactionInfo;
import com.kopyn.cqrs.transaction_service.service.TransactionCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionCommandController {

    private final TransactionCommandService transactionService;

    @PostMapping
    public Mono<TransactionInfo> orderTransaction(@RequestBody TransactionInfo transactionInfo) {
        return transactionService.orderTransaction(transactionInfo);
    }

}
