package by.itechart.lastcoursetask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionStatisticsDto {
    private Long transactionsNumber;
    private Long successfulTransactionsAmount;
    private Long failedTransactionsAmount;
    private Long minTransactionAmount;
    private String maxTransactionCurrency;
    private Long maxTransactionAmount;
    private String minTransactionCurrency;
}
