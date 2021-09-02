package by.itechart.lastcoursetask.command;

import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("allTransactions")
@Slf4j
@RequiredArgsConstructor
public class AllTransactionCommand extends Command {
    private final TransactionService service;

    @Override
    public List<TransactionDto> execute() {
        log.info("Find all command");
        return service.findAll();
    }
}
