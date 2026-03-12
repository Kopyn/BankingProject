package com.kopyn.cqrs.transaction_service.service;

import com.kopyn.cqrs.transaction_service.domain.TransactionInfo;
import reactor.core.publisher.Mono;

public interface TransactionCommandService {

    Mono<TransactionInfo> orderTransaction(TransactionInfo transactionInfo);

}
