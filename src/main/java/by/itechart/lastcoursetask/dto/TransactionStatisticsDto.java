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
    @Positive
    private Long transactionsNumber;
    @Positive
    private Long successfulTransactionsAmount;
    @Positive
    private Long failedTransactionsAmount;
    @Positive
    private Long minTransactionAmount;
    @Pattern(regexp = "[a-zA-Z]{3}")
    private String maxTransactionCurrency;
    @Positive
    private Long maxTransactionAmount;
    @Pattern(regexp = "[a-zA-Z]{3}")
    private String minTransactionCurrency;
}
