package com.kopyn.cqrs.customer_service.command.domain.commands;

import com.kopyn.cqrs.customer_service.command.domain.CustomerInfo;

public record CreateCustomerCommand(
        CustomerInfo customerInfo
) implements Command {
}
