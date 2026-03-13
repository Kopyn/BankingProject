package domain.saga_commands.transaction;

import domain.saga_commands.SagaCommand;

import java.util.UUID;

// Command used when the first account couldn't be debited
public record AbortTransactionSagaCommand(
        UUID transactionId,
        String reason
) implements SagaCommand {
    @Override
    public UUID aggregateId() {
        return null;
    }
}
