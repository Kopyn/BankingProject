package com.kopyn.cqrs.transaction_service.service;

import com.kopyn.cqrs.transaction_service.api.commands.OrderTransactionCommand;
import com.kopyn.cqrs.transaction_service.domain.TransactionInfo;
import com.kopyn.cqrs.transaction_service.service.handlers.OrderTransactionCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionCommandServiceImpl implements TransactionCommandService {

    private final OrderTransactionCommandHandler transactionCommandHandler;

    public Mono<TransactionInfo> orderTransaction(TransactionInfo transactionInfo) {
        OrderTransactionCommand cmd = new OrderTransactionCommand(transactionInfo);
        return transactionCommandHandler.handle(cmd);
    }

}
