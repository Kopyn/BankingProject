package domain.saga_commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import domain.saga_commands.account.CreditAccountSagaCommand;
import domain.saga_commands.account.DebitAccountSagaCommand;
import domain.saga_commands.account.RefundAccountSagaCommand;
import domain.saga_commands.transaction.FinalizeTransactionCommand;

import java.util.UUID;


// this is not ideal, should have 1 interface to prevent modifying this class in case new command is introduced
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreditAccountSagaCommand.class, name = "credit"),
        @JsonSubTypes.Type(value = DebitAccountSagaCommand.class, name = "debit"),
        @JsonSubTypes.Type(value = RefundAccountSagaCommand.class, name = "refund"),
        @JsonSubTypes.Type(value = FinalizeTransactionCommand.class, name = "finalizeTransaction")
})
public interface SagaCommand {
    UUID accountId();
}
