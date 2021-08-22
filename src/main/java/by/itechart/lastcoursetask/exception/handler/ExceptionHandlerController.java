package by.itechart.lastcoursetask.exception.handler;

import by.itechart.lastcoursetask.exception.FileNotReadException;
import by.itechart.lastcoursetask.exception.InvalidFileExtensionException;
import by.itechart.lastcoursetask.exception.OperatorExistException;
import by.itechart.lastcoursetask.exception.OperatorNotFoundException;
import by.itechart.lastcoursetask.exception.RoleNotFoundException;
import by.itechart.lastcoursetask.exception.TransactionExistException;
import by.itechart.lastcoursetask.exception.TransactionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({OperatorExistException.class, TransactionExistException.class, FileNotReadException.class})
    public ResponseEntity<String> badRequest(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({TransactionNotFoundException.class, RoleNotFoundException.class, OperatorNotFoundException.class})
    public ResponseEntity<String> notFound(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @ExceptionHandler({InvalidFileExtensionException.class})
    public String iAmTeapot(RuntimeException e) {
        return e.getMessage();
    }
}