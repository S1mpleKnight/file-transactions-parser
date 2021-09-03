package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.dto.ErrorMessageDto;
import by.itechart.lastcoursetask.service.ErrorMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/errors")
public class ErrorMessageController {
    private final ErrorMessageService messageService;

    @GetMapping
    public ResponseEntity<Page<ErrorMessageDto>> findAll(
            @RequestParam("page") int page, @RequestParam("size") int size) {
        log.info("Find all messages");
        return ResponseEntity.ok().body(messageService.findAll(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ErrorMessageDto> findMessageById(@PathVariable("id") Long id) {
        log.info("Find message by id: " + id);
        return ResponseEntity.ok().body(messageService.findMessageById(id));
    }
}
