package by.itechart.lastcoursetask.exception.handler;

import by.itechart.lastcoursetask.exception.CommandNotFoundException;
import by.itechart.lastcoursetask.exception.ErrorMessageNotFoundException;
import by.itechart.lastcoursetask.exception.FileNotReadException;
import by.itechart.lastcoursetask.exception.InvalidFileExtensionException;
import by.itechart.lastcoursetask.exception.JwtAuthenticationException;
import by.itechart.lastcoursetask.exception.OperatorExistException;
import by.itechart.lastcoursetask.exception.OperatorNotFoundException;
import by.itechart.lastcoursetask.exception.RejectAccessException;
import by.itechart.lastcoursetask.exception.RoleNotFoundException;
import by.itechart.lastcoursetask.exception.TransactionExistException;
import by.itechart.lastcoursetask.exception.TransactionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({OperatorExistException.class, TransactionExistException.class,
            FileNotReadException.class, InvalidFileExtensionException.class, IllegalArgumentException.class})
    public ResponseEntity<String> badRequest(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> badRequest(BindException e) {
        return ResponseEntity.badRequest().body(takeErrorFields(e));
    }


    @ExceptionHandler({TransactionNotFoundException.class, RoleNotFoundException.class, OperatorNotFoundException.class,
            CommandNotFoundException.class, ErrorMessageNotFoundException.class})
    public ResponseEntity<String> notFound(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({RejectAccessException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String forbidden(RuntimeException e) {
        return e.getMessage();
    }

    @ExceptionHandler({JwtAuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String unauthorized(AuthenticationException exception) {
        return exception.getMessage();
    }

    private String takeErrorFields(BindException e) {
        StringBuilder stringBuilder = new StringBuilder("Invalid data in:\n");
        for (String fieldError : getDistinctErrorFields(e)) {
            stringBuilder.append("Field - ").append(fieldError).append("\n");
        }
        return stringBuilder.toString();
    }

    private List<String> getDistinctErrorFields(BindException e) {
        return e.getFieldErrors()
                .stream()
                .map(FieldError::getField)
                .distinct()
                .collect(Collectors.toList());
    }
}
