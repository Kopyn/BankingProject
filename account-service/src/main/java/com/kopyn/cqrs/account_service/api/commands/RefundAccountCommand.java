package com.kopyn.cqrs.account_service.api.commands;

import java.util.UUID;

public record RefundAccountCommand (
        UUID accountId,
        long amount,
        UUID transactionId
) implements Command {
}
