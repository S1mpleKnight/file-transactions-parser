package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.service.FileTransferService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/files")
public class UploadFilesController {
    private final static String OK_STATUS_RESPONSE = "Transactions have been loaded";
    private final FileTransferService transferService;

    //todo: get operator object from Spring Security
    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
        log.info("Save file: " + file.getOriginalFilename() + ", operator: " + id);
        List<String> errorMessages = transferService.uploadFile(file, id);
        String responseText = errorMessages.isEmpty()
                ? OK_STATUS_RESPONSE
                : OK_STATUS_RESPONSE + "\n" + String.join("\n", errorMessages);
        return ResponseEntity.ok(responseText);
    }
}
