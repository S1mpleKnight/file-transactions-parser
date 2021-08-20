package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.dto.TransactionDTO;
import by.itechart.lastcoursetask.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionsController {
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Set<TransactionDTO>> findAll() {
        log.info("Find All");
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> findById(@PathVariable String id) {
        log.info("Find by id: " + id);
        return ResponseEntity.ok(transactionService.findById(UUID.fromString(id)));
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Set<TransactionDTO>> findByCustomerId(@PathVariable String id) {
        log.info("Find by customer id: " + id);
        return ResponseEntity.ok(transactionService.findByCustomerId(UUID.fromString(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        log.info("Delete id: " + id);
        transactionService.delete(UUID.fromString(id));
        return ResponseEntity.ok("Transaction was successfully deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestParam TransactionDTO transactionDTO) {
        log.info("Update id: " + id);
        transactionService.update(UUID.fromString(id), transactionDTO);
        return ResponseEntity.ok("Transaction was successfully updated");
    }
}
