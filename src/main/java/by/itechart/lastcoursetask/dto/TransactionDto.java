package by.itechart.lastcoursetask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
