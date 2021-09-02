package by.itechart.lastcoursetask.command;

import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.dto.TransactionStatisticsDto;
import by.itechart.lastcoursetask.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("shortInfo")
@Slf4j
@RequiredArgsConstructor
public class TransactionStatisticsCommand extends Command {
    private final TransactionService service;

    @Override
    public TransactionStatisticsDto execute() {
        log.info("Transaction statistics command");
        return getReport();
    }

    private TransactionStatisticsDto getReport() {
        TransactionDto min = service.findMinTransactions().get(0);
        TransactionDto max = service.findMaxTransactions().get(0);
        List<TransactionDto> transactions = service.findAll();
        long size = transactions.size();
        long successfulTransactions = getSuccessfulTransactions(transactions);
        return createReport(size, successfulTransactions, min, max);
    }

    private long getSuccessfulTransactions(List<TransactionDto> transactions) {
        return transactions
                .stream()
                .filter(TransactionDto::getStatus)
                .count();
    }

    private TransactionStatisticsDto createReport(long size, long successful, TransactionDto minTransaction, TransactionDto maxTransaction) {
        return new TransactionStatisticsDto(
                size, successful, size - successful,
                Long.parseLong(minTransaction.getAmount()), minTransaction.getCurrency(),
                Long.parseLong(maxTransaction.getAmount()), maxTransaction.getCurrency());
    }
}
