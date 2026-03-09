package com.kopyn.cqrs.account_service.api.commands;

import com.kopyn.cqrs.account_service.domain.AccountInfo;

import java.util.UUID;

public record UpdateAccountCommand (
        UUID accountId,
        AccountInfo accountInfo
) implements Command {
}
