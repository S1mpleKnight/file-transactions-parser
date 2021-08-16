package by.itechart.lastcoursetask.util;

import by.itechart.lastcoursetask.dto.OperatorDTO;
import by.itechart.lastcoursetask.dto.TransactionDTO;
import by.itechart.lastcoursetask.entity.Operator;
import by.itechart.lastcoursetask.entity.Role;
import by.itechart.lastcoursetask.entity.Transaction;
import by.itechart.lastcoursetask.exception.RoleNotFoundException;
import by.itechart.lastcoursetask.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@AllArgsConstructor
public class EntityMapper {
    private final static Long BASIC_OPERATOR_ROLE_POSITION = 2L;
    private final RoleService service;

    public OperatorDTO mapToOperatorDTO(Operator operator) {
        OperatorDTO operatorDTO = new OperatorDTO();
        operatorDTO.setId(operator.getId());
        operatorDTO.setFirstName(operator.getFirstName());
        operatorDTO.setLastName(operator.getLastName());
        operatorDTO.setNickname(operator.getNickname());
        operatorDTO.setPassword(operator.getPassword());
        operatorDTO.setRole(operator.getRole().getName());
        return operatorDTO;
    }

    public Operator mapToOperatorEntity(OperatorDTO operatorDTO) {
        Operator operator = new Operator();
        operator.setId(operatorDTO.getId());
        operator.setFirstName(operatorDTO.getFirstName());
        operator.setLastName(operatorDTO.getLastName());
        operator.setNickname(operatorDTO.getNickname());
        operator.setPassword(operatorDTO.getPassword());
        operator.setRole(getRole(operatorDTO.getRole()));
        return operator;
    }

    public TransactionDTO mapToTransactionDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(transaction.getId().toString());
        transactionDTO.setCustomerId(transaction.getCustomerId().toString());
        transactionDTO.setAmount(transaction.getAmount().toString());
        transactionDTO.setCurrency(transaction.getCurrency());
        transactionDTO.setDateTime(transaction.getDateTime().toString());
        transactionDTO.setStatus(transaction.getStatus());
        return transactionDTO;
    }

    public Transaction mapToTransactionEntity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.fromString(transactionDTO.getTransactionId()));
        transaction.setCustomerId(UUID.fromString(transactionDTO.getCustomerId()));
        transaction.setAmount(new BigDecimal(transactionDTO.getAmount()));
        transaction.setCurrency(transactionDTO.getCurrency());
        transaction.setDateTime(LocalDateTime.parse(transactionDTO.getDateTime()));
        transaction.setStatus(transactionDTO.getStatus());
        return transaction;
    }

    private Role getRole(String role) {
        try {
            return service.findByName(role);
        } catch (RoleNotFoundException exception) {
            System.out.println(exception.getMessage());
            return service.findById(BASIC_OPERATOR_ROLE_POSITION);
        }
    }
}
