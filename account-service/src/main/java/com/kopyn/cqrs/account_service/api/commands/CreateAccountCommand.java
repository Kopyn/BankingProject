package com.kopyn.cqrs.account_service.api.commands;

import com.kopyn.cqrs.account_service.domain.AccountInfo;

public record CreateAccountCommand (
        AccountInfo accountInfo
) implements Command {
}
