package by.itechart.lastcoursetask.util;

import by.itechart.lastcoursetask.dto.OperatorDTO;
import by.itechart.lastcoursetask.dto.TransactionDTO;
import by.itechart.lastcoursetask.entity.Operator;
import by.itechart.lastcoursetask.entity.Role;
import by.itechart.lastcoursetask.entity.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class EntityMapper {
    public OperatorDTO mapToUserDTO(Operator operator) {
        OperatorDTO operatorDTO = new OperatorDTO();
        operatorDTO.setId(operator.getId());
        operatorDTO.setFirstName(operator.getFirstName());
        operatorDTO.setLastName(operator.getLastName());
        operatorDTO.setNickname(operator.getNickname());
        operatorDTO.setPassword(operator.getPassword());
        operatorDTO.setRole(operator.getRole().getValue());
        return operatorDTO;
    }

    public Operator mapToUserEntity(OperatorDTO operatorDTO) {
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
        //todo: use RoleService to get correct role
        return null;
    }
}
