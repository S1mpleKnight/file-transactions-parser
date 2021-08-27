package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
@PreAuthorize("hasAuthority('admin')")
public class TransactionsController {
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionDto>> findAll() {
        log.info("Find All");
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> findById(@PathVariable String id) {
        log.info("Find by id: " + id);
        return ResponseEntity.ok(transactionService.findById(id));
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<List<TransactionDto>> findByCustomerId(@PathVariable String id) {
        log.info("Find by customer id: " + id);
        return ResponseEntity.ok(transactionService.findByCustomerId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        log.info("Delete id: " + id);
        transactionService.delete(UUID.fromString(id));
        return ResponseEntity.ok("Transaction was successfully deleted");
    }
}
