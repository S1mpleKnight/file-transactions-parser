package by.itechart.lastcoursetask.command;

import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component("topAmountTransactions")
@Slf4j
@RequiredArgsConstructor
public class TopAmountTransactionCommand extends Command {
    private final static Integer NUMBER_OF_TOP = 5;
    private final TransactionService service;

    @Override
    public List<TransactionDto> execute() {
        log.info("Top 5 transactions command");
        return getTopTransactions();
    }

    private List<TransactionDto> getTopTransactions() {
        long listSize = service.findAll().size();
        List<TransactionDto> transactions = listSize > 5 ? getTopTransactions(listSize): service.findAll();
        transactions.sort(Comparator.reverseOrder());
        return transactions;
    }

    private List<TransactionDto> getTopTransactions(long listSize) {
        return service.findAll()
                .stream()
                .sorted(TransactionDto::compareTo)
                .skip(listSize - NUMBER_OF_TOP)
                .collect(Collectors.toList());
    }
}
