package by.itechart.lastcoursetask.dto;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto implements Comparable<TransactionDto>{
    @NotBlank
    @Size(min = 36, max = 36)
    private String transactionId;
    @NotBlank
    @Size(min = 36, max = 36)
    private String customerId;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z]{3}")
    private String currency;
    @Positive
    private String amount;
    @NotNull
    private Boolean status;
    @Past
    private LocalDateTime dateTime;

    @Override
    public int compareTo(TransactionDto o) {
        return Long.compare(Long.parseLong(this.amount), Long.parseLong(o.getAmount()));
    }
}
