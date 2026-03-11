package com.kopyn.cqrs.account_service.api.commands;

import java.util.UUID;

public record DebitAccountCommand (
        UUID accountId,
        long amount
) implements Command {
}
