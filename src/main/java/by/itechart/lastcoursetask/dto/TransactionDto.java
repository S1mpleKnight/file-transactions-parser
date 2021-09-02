package by.itechart.lastcoursetask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Schema(description = "Transaction representation entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto implements Comparable<TransactionDto>{
    @Schema(description = "UUID string format")
    @NotBlank
    @Size(min = 36, max = 36)
    private String transactionId;
    @Schema(description = "UUID string format")
    @NotBlank
    @Size(min = 36, max = 36)
    private String customerId;
    @Schema(description = "Represent currency in 3 letters")
    @NotBlank
    @Pattern(regexp = "[a-zA-Z]{3}")
    private String currency;
    @Positive
    private String amount;
    @Schema(description = "Status of the transaction")
    @NotNull
    private Boolean status;
    @Past
    private LocalDateTime dateTime;

    @Override
    public int compareTo(TransactionDto o) {
        return Long.compare(Long.parseLong(this.amount), Long.parseLong(o.getAmount()));
    }
}
