package by.itechart.lastcoursetask.command;

import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("info")
@Slf4j
public class TransactionStatisticsCommand extends Command {
    private final TransactionService service;

    @Autowired
    TransactionStatisticsCommand(TransactionService service) {
        this.service = service;
    }

    @Override
    public String execute() {
        log.info("Transaction statistics command");
        return getReport();
    }

    private String getReport() {
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

    private String createReport(long size, long successful, TransactionDto minTransaction, TransactionDto maxTransaction) {
        return String.format("""
                Total number of transactions: %d
                where successful: %d
                failed: %d
                MIN transaction: %s %s
                MAX transaction: %s %s""",
                size, successful, size - successful,
                minTransaction.getAmount(), minTransaction.getCurrency(),
                maxTransaction.getAmount(), maxTransaction.getCurrency());
    }
}
