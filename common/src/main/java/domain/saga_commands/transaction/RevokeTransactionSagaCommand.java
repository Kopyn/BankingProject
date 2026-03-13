package domain.saga_commands.transaction;

import domain.saga_commands.SagaCommand;

import java.util.UUID;

// Command used when first account was debited and needs to be refunded
public record RevokeTransactionSagaCommand(
        UUID transactionId,
        UUID accountToRefundId,
        String reason
) implements SagaCommand {
    @Override
    public UUID aggregateId() {
        return accountToRefundId;
    }
}
