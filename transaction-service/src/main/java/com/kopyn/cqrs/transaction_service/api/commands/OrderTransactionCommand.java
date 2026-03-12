package com.kopyn.cqrs.transaction_service.api.commands;

import com.kopyn.cqrs.transaction_service.domain.TransactionInfo;

import java.util.UUID;

public record OrderTransactionCommand (
        TransactionInfo transactionInfo
) implements Command {
}
