package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.dto.TransactionDTO;
import by.itechart.lastcoursetask.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionsController {
    private final TransactionService transactionService;

    @GetMapping
    public Set<TransactionDTO> findAll() {
        return transactionService.findAll();
    }


}
