package com.kopyn.cqrs.account_service.api.commands;

public record ContinueTransactionCommand(
        String transactionId
) implements TransactionContinuationCommand {}
