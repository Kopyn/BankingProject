package domain.saga_commands.transaction;

import domain.saga_commands.SagaCommand;

import java.util.UUID;

// Command used when transaction was completed successfully
public record FinalizeTransactionSagaCommand(
        UUID transactionId
) implements SagaCommand {
    @Override
    public UUID aggregateId() {
        return null;
    }
}
