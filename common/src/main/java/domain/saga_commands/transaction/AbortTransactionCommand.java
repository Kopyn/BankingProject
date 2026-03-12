package domain.saga_commands.transaction;

import domain.saga_commands.SagaCommand;

import java.util.UUID;

// Command used when the first account couldn't be debited
public record AbortTransactionCommand (
        UUID transactionId,
        String reason
) implements SagaCommand {
}
