package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.service.FileTransferService;
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
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/files")
@PreAuthorize("hasAuthority('USER')")
public class UploadFilesController {
    private final FileTransferService transferService;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, Principal principal) {
        log.info("Save file: " + file.getOriginalFilename() + ", operator: " + principal.getName());
        List<String> errorMessages = transferService.uploadFile(file, principal.getName());
        String responseText = errorMessages.isEmpty()
                ? "Transactions have been loaded"
                : "Transactions have been loaded" + "\n" + String.join("\n", errorMessages);
        return ResponseEntity.ok(responseText);
    }
}
