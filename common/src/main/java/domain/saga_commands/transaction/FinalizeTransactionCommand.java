package domain.saga_commands.transaction;

import domain.saga_commands.SagaCommand;

import java.util.UUID;

public record FinalizeTransactionCommand (
        UUID transactionId
) implements SagaCommand {
}
