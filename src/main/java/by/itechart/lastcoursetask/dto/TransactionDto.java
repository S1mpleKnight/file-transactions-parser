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
    private String transactionId;
    private String customerId;
    private String currency;
    private String amount;
    private Boolean status;
    private LocalDateTime dateTime;

    @Override
    public int compareTo(TransactionDto o) {
        return Long.compare(Long.parseLong(this.amount), Long.parseLong(o.getAmount()));
    }
}
