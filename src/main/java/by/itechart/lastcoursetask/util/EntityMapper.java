package by.itechart.lastcoursetask.util;

import by.itechart.lastcoursetask.dto.ErrorMessageDto;
import by.itechart.lastcoursetask.dto.OperatorDto;
import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.entity.ErrorMessage;
import by.itechart.lastcoursetask.entity.Operator;
import by.itechart.lastcoursetask.entity.Role;
import by.itechart.lastcoursetask.entity.Transaction;
import by.itechart.lastcoursetask.exception.RoleNotFoundException;
import by.itechart.lastcoursetask.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class EntityMapper {
    private final static Long BASIC_OPERATOR_ROLE_POSITION = 2L;
    private final RoleService service;

    public OperatorDto mapToOperatorDTO(Operator operator) {
        OperatorDto operatorDTO = new OperatorDto();
        operatorDTO.setId(operator.getId());
        operatorDTO.setFirstName(operator.getFirstName());
        operatorDTO.setLastName(operator.getLastName());
        operatorDTO.setNickname(operator.getNickname());
        operatorDTO.setPassword(operator.getPassword());
        operatorDTO.setRole(operator.getRole().getName());
        return operatorDTO;
    }

    public Operator mapToOperatorEntity(OperatorDto operatorDTO) {
        Operator operator = new Operator();
        operator.setId(operatorDTO.getId());
        operator.setFirstName(operatorDTO.getFirstName());
        operator.setLastName(operatorDTO.getLastName());
        operator.setNickname(operatorDTO.getNickname());
        operator.setPassword(operatorDTO.getPassword());
        operator.setRole(getRole(operatorDTO.getRole()));
        return operator;
    }

    public TransactionDto mapToTransactionDTO(Transaction transaction) {
        TransactionDto transactionDTO = new TransactionDto();
        transactionDTO.setTransactionId(transaction.getId().toString());
        transactionDTO.setCustomerId(transaction.getCustomerId().toString());
        transactionDTO.setAmount(transaction.getAmount().toString());
        transactionDTO.setCurrency(transaction.getCurrency());
        transactionDTO.setDateTime(transaction.getDateTime());
        transactionDTO.setStatus(transaction.getStatus());
        return transactionDTO;
    }

    public Transaction mapToTransactionEntity(TransactionDto transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.fromString(transactionDTO.getTransactionId()));
        transaction.setCustomerId(UUID.fromString(transactionDTO.getCustomerId()));
        transaction.setAmount(Long.parseLong(transactionDTO.getAmount().replace(" ", "")));
        transaction.setCurrency(transactionDTO.getCurrency());
        transaction.setDateTime(transactionDTO.getDateTime());
        transaction.setStatus(transactionDTO.getStatus());
        return transaction;
    }

    public ErrorMessageDto mapToErrorMessageDto(ErrorMessage errorMessage) {
        ErrorMessageDto messageDto = new ErrorMessageDto();
        messageDto.setErrorMessage(errorMessage.getErrorMessage());
        messageDto.setOperatorId(errorMessage.getOperator().getId());
        messageDto.setLocalDateTime(errorMessage.getErrorTime());
        messageDto.setId(errorMessage.getId());
        return messageDto;
    }

    private Role getRole(String role) {
        try {
            return service.findByName(role);
        } catch (RoleNotFoundException exception) {
            log.error(exception.getMessage());
            return service.findById(BASIC_OPERATOR_ROLE_POSITION);
        }
    }
}
