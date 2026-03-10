package com.kopyn.cqrs.account_service.api.commands;

import java.util.UUID;

public record CreditAccountCommand (
        UUID accountId,
        long amount,
        UUID transactionId
) implements Command {
}
