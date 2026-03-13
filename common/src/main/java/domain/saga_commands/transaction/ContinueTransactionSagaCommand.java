package domain.saga_commands.transaction;

import domain.saga_commands.SagaCommand;

import java.util.UUID;

// Command used when first account was debited successfully
public record ContinueTransactionSagaCommand(
        UUID transactionId,
        UUID accountToCreditId
) implements SagaCommand {
    @Override
    public UUID aggregateId() {
        return accountToCreditId;
    }
}
