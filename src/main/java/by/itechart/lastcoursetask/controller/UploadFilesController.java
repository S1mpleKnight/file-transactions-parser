package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.service.FileTransferService;
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

@Schema(name = "Upload files controller")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/files")
@PreAuthorize("hasAuthority('USER')")
public class UploadFilesController {
    private final FileTransferService transferService;

    @Operation(description = "Save file, parse & save transactions")
    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("files") MultipartFile[] files, Principal principal) {
        log.info("Save files, operator: " + principal.getName());
        return ResponseEntity.ok(transferService.uploadFiles(files, principal));
    }
}
