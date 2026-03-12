package domain.saga_commands.transaction;

import domain.saga_commands.SagaCommand;

import java.util.UUID;

// Command used when first account was debited successfully
public record ContinueTransactionCommand (
        UUID transactionId,
        UUID accountToCreditId
) implements SagaCommand {
    @Override
    public UUID accountId() {
        return accountToCreditId;
    }
}
