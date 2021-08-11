package by.itechart.lastcoursetask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private UUID transactionId;
    private UUID customerId;
    private String currency;
    private BigDecimal amount;
    private Boolean status;
    private LocalDateTime dateTime;
}
