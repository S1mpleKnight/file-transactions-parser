package by.itechart.lastcoursetask.command.impl;

import by.itechart.lastcoursetask.command.api.Command;
import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("max")
@Slf4j
public class MaxTransactionCommand extends Command {
    private final TransactionService service;

    @Autowired
    MaxTransactionCommand(TransactionService service) {
        this.service = service;
    }

    @Override
    public List<TransactionDto> execute() {
        log.info("Find max transactions command");
        return service.findMaxTransactions();
    }
}
