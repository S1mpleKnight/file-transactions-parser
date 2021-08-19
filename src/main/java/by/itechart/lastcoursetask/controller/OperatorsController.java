package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.dto.OperatorDTO;
import by.itechart.lastcoursetask.service.OperatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/operators")
public class OperatorsController {
    private final OperatorService operatorService;

    @GetMapping
    public Set<OperatorDTO> findAll() {
        log.info("Find all");
        return operatorService.findAll();
    }

    @GetMapping("/{id}")
    public OperatorDTO findById(@PathVariable Long id) {
        log.info("Find by id: " + id);
        return operatorService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Delete by id: " + id);
        operatorService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestParam OperatorDTO operatorDTO) {
        log.info("Update by id: " + id + ", operator: " + operatorDTO);
        operatorService.update(id, operatorDTO);
    }

    @PostMapping
    public void save(@RequestParam OperatorDTO operatorDTO) {
        log.info("Save operator: " + operatorDTO);
        operatorService.save(operatorDTO);
    }
}