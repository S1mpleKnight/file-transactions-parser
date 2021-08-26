package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.dto.OperatorDto;
import by.itechart.lastcoursetask.service.OperatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/operators")
@PreAuthorize("hasRole('admin')")
public class OperatorsController {
    private final OperatorService operatorService;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("Delete by id: " + id);
        operatorService.delete(id);
        return ResponseEntity.ok("Operator was successfully deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestParam OperatorDto operatorDTO) {
        log.info("Update by id: " + id + ", operator: " + operatorDTO);
        operatorService.update(id, operatorDTO);
        return ResponseEntity.ok("Operator was successfully updated");
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestParam OperatorDto operatorDTO) {
        log.info("Save operator: " + operatorDTO);
        operatorService.save(operatorDTO);
        return ResponseEntity.ok("Operator was successfully saved");
    }
}
