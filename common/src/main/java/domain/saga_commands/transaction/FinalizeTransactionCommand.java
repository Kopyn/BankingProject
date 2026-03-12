package domain.saga_commands.transaction;

import domain.saga_commands.SagaCommand;

import java.util.UUID;

// Command used when transaction was completed successfully
public record FinalizeTransactionCommand (
        UUID transactionId
) implements SagaCommand {
    @Override
    public UUID accountId() {
        return null;
    }
}
