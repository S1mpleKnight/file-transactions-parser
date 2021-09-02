package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.dto.OperatorDto;
import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.service.OperatorService;
import by.itechart.lastcoursetask.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "Operator controller", description = "Common CRUD")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/operators")
@PreAuthorize("hasAuthority('ADMIN')")
public class OperatorsController {
    private final OperatorService operatorService;
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<OperatorDto>> findAll() {
        log.info("Find all");
        return ResponseEntity.ok(operatorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperatorDto> findById(@PathVariable Long id) {
        log.info("Find by id: " + id);
        return ResponseEntity.ok().body(operatorService.findById(id));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionDto>> findTransactionsByOperator(@PathVariable("id") Long id) {
        log.info("Find transactions by operator, id: " + id);
        return ResponseEntity.ok().body(transactionService.findByOperatorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, Principal principal) {
        log.info("Delete by id: " + id);
        operatorService.delete(id, principal);
        return ResponseEntity.ok("Operator was successfully deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(
            @PathVariable @Parameter(description = "Old operator id") Long id,
            @RequestBody @Parameter(description = "New operator object") OperatorDto operatorDTO) {
        log.info("Update by id: " + id + ", operator: " + operatorDTO);
        operatorService.update(id, operatorDTO);
        return ResponseEntity.ok("Operator was successfully updated");
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody OperatorDto operatorDTO) {
        log.info("Save operator: " + operatorDTO);
        operatorService.save(operatorDTO);
        return ResponseEntity.ok("Operator was successfully saved");
    }
}
