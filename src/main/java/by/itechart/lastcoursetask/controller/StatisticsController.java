package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.command.Command;
import by.itechart.lastcoursetask.command.CommandFactory;
import by.itechart.lastcoursetask.dto.CommandDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Statistics controller")
@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/statistics")
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {
    private final CommandFactory factory;

    @Operation(description = "Return statistics result of the given command")
    @GetMapping
    public ResponseEntity<Object> getStatistics(
            @RequestBody @Parameter(description = "Command representation") CommandDto commandDto) {
        log.info("Execute command: " + commandDto);
        Command command = factory.getCommand(commandDto);
        return ResponseEntity.ok(command.execute());
    }
}
