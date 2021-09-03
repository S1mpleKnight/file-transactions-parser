package by.itechart.lastcoursetask.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Schema(description = "Representation of the statistics report")
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
