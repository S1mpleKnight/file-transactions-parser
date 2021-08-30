package by.itechart.lastcoursetask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto implements Comparable<TransactionDto>{
    private String transactionId;
    private String customerId;
    private String currency;
    private String amount;
    private Boolean status;
    private String dateTime;

    @Override
    public int compareTo(TransactionDto o) {
        return new BigDecimal(this.amount).compareTo(new BigDecimal(o.getAmount()));
    }
}
