package com.kopyn.cqrs.customer_service.command.domain.commands;

import com.kopyn.cqrs.customer_service.command.domain.CustomerInfo;

import java.util.UUID;

public record UpdateCustomerCommand (
        UUID uuid,
        CustomerInfo customerInfo
) implements Command {
}
