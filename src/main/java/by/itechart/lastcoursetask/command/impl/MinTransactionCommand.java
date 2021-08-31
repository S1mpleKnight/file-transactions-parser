package by.itechart.lastcoursetask.command.impl;

import by.itechart.lastcoursetask.command.api.Command;
import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("min")
@Slf4j
public class MinTransactionCommand extends Command {
    private final TransactionService service;

    @Autowired
    MinTransactionCommand(TransactionService service) {
        this.service = service;
    }

    @Override
    public List<TransactionDto> execute() {
        log.info("Find min transactions command");
        return service.findMinTransactions();
    }
}
