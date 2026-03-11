package domain.saga_commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import domain.saga_commands.account.CreditAccountSagaCommand;
import domain.saga_commands.account.DebitAccountSagaCommand;
import domain.saga_commands.account.RefundAccountSagaCommand;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreditAccountSagaCommand.class, name = "credit"),
        @JsonSubTypes.Type(value = DebitAccountSagaCommand.class, name = "debit"),
        @JsonSubTypes.Type(value = RefundAccountSagaCommand.class, name = "refund")
})
public interface SagaCommand {
}
