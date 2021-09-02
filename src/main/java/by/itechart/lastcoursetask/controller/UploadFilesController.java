package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.entity.ErrorMessage;
import by.itechart.lastcoursetask.service.ErrorMessageService;
import by.itechart.lastcoursetask.service.FileTransferService;
import by.itechart.lastcoursetask.service.OperatorService;
import by.itechart.lastcoursetask.util.EntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(name = "Upload files controller")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/files")
@PreAuthorize("hasAuthority('USER')")
public class UploadFilesController {
    private final FileTransferService transferService;
    private final ErrorMessageService messageService;
    private final OperatorService operatorService;
    private final EntityMapper mapper;

    @Operation(description = "Save file, parse & save transactions")
    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("files") MultipartFile[] files, Principal principal) {
        log.info("Save files, operator: " + principal.getName());
        List<String> errorMessages = transferService.uploadFiles(files, principal.getName());
        String responseText = getResponseText(parseMessages(errorMessages, principal));
        return ResponseEntity.ok(responseText);
    }

    private List<ErrorMessage> parseMessages(List<String> errorMessages, Principal principal) {
        List<ErrorMessage> errors = new ArrayList<>();
        for (String errorMessage : errorMessages) {
            errors.add(createErrorMessage(errorMessage, principal));
        }
        return errors;
    }

    private ErrorMessage createErrorMessage(String errorMessageValue, Principal principal) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorMessage(errorMessageValue);
        errorMessage.setErrorTime(LocalDateTime.now());
        errorMessage.setOperator(mapper.mapToOperatorEntity(operatorService.findByNickName(principal.getName())));
        return errorMessage;
    }

    private String getResponseText(List<ErrorMessage> errorMessages) {
        if (errorMessages.isEmpty()) {
            return "Transactions have been loaded";
        } else {
            messageService.saveAll(errorMessages);
            return "Transactions have been loaded with some errors";
        }
    }
}
