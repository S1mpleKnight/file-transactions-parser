package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.command.api.Command;
import by.itechart.lastcoursetask.command.impl.CommandFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/statistics")
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {
    private final CommandFactory factory;

    @GetMapping("/{command}")
    public ResponseEntity<Object> getStatistics(@PathVariable(value = "command") String commandValue) {
        log.info("Execute command: " + commandValue);
        Command command = factory.getCommand(commandValue);
        return ResponseEntity.ok(command.execute());
    }
}
