package domain.saga_commands.account;

import domain.saga_commands.SagaCommand;

import java.util.UUID;

public record DebitAccountSagaCommand (
        UUID aggregateId,
        UUID creditAccount,
        long amount,
        UUID transactionId
) implements SagaCommand {
}
