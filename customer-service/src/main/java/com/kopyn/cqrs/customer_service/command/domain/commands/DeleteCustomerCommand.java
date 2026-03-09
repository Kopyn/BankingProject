package com.kopyn.cqrs.customer_service.command.domain.commands;

import java.util.UUID;

public record DeleteCustomerCommand(
        UUID uuid
) implements Command {
}
