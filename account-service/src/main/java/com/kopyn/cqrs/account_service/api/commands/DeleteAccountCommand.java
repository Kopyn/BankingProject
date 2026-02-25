package com.kopyn.cqrs.account_service.api.commands;

import java.util.UUID;

public record DeleteAccountCommand (
        UUID accountId
) implements Command {
}
