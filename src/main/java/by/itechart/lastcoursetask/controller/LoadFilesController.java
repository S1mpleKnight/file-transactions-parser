package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.service.FileTransferService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/files")
public class LoadFilesController {
    private final FileTransferService transferService;

    //todo: get operator object from Spring Security
    @PostMapping
    public void load(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
        log.info("Save file: " + file.getOriginalFilename() + ", operator: " + id);
        transferService.loadFile(file, id);
    }
}
