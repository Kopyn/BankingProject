package domain.saga_commands.account;

import domain.saga_commands.SagaCommand;

import java.util.UUID;

public record CreditAccountSagaCommand (
        UUID aggregateId,
        long amount,
        UUID transactionId
) implements SagaCommand {
}