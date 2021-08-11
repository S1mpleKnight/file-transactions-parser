package by.itechart.lastcoursetask.util;

import by.itechart.lastcoursetask.dto.TransactionDTO;
import by.itechart.lastcoursetask.dto.UserDTO;
import by.itechart.lastcoursetask.entity.Role;
import by.itechart.lastcoursetask.entity.Transaction;
import by.itechart.lastcoursetask.entity.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EntityMapper {
    public UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId().toString());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setNickname(user.getNickname());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole().getValue());
        return userDTO;
    }

    public User mapToUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(UUID.fromString(userDTO.getId()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setNickname(userDTO.getNickname());
        user.setPassword(userDTO.getPassword());
        user.setRole(getRole(userDTO.getRole()));
        return user;
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
