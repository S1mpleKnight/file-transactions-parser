package by.itechart.lastcoursetask.service;

import by.itechart.lastcoursetask.dto.OperatorDTO;
import by.itechart.lastcoursetask.dto.TransactionDTO;
import by.itechart.lastcoursetask.entity.Transaction;
import by.itechart.lastcoursetask.exception.TransactionExistException;
import by.itechart.lastcoursetask.exception.TransactionNotFoundException;
import by.itechart.lastcoursetask.repository.TransactionRepository;
import by.itechart.lastcoursetask.util.EntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final EntityMapper mapper;

    public Set<TransactionDTO> findAll() {
        Set<Transaction> transactions = new HashSet<>();
        repository.findAll().forEach(transactions::add);
        return getTransactionDTOSet(transactions);
    }

    public TransactionDTO findById(UUID id) {
        return repository.findById(id).map(mapper::mapToTransactionDTO)
                .orElseThrow(() -> new TransactionNotFoundException(id.toString()));
    }

    public Set<TransactionDTO> findByCustomerId(UUID customerId) {
        Set<Transaction> transactions = repository.findByCustomerId(customerId);
        return getTransactionDTOSet(transactions);
    }

    public Set<TransactionDTO> findByDateAndTime(LocalDateTime dateTime) {
        Set<Transaction> transactions = repository.findByDateTime(dateTime);
        return getTransactionDTOSet(transactions);
    }

    @Transactional
    public void delete(UUID transactionId) {
        if (repository.existsById(transactionId)) {
            repository.deleteById(transactionId);
        } else {
            throw new TransactionNotFoundException(transactionId.toString());
        }
    }

    @Transactional
    public void save(TransactionDTO transactionDTO, OperatorDTO operatorDTO) {
        if (!isTransactionExist(transactionDTO.getTransactionId())) {
            Transaction transaction = mapper.mapToTransactionEntity(transactionDTO);
            transaction.setOperator(mapper.mapToOperatorEntity(operatorDTO));
            repository.save(transaction);
        } else {
            throw new TransactionExistException(transactionDTO.getTransactionId());
        }
    }

    @Transactional
    public void update(UUID oldTransactionId, TransactionDTO newTransaction) {
        if (repository.existsById(oldTransactionId)) {
            Transaction transaction = mapper.mapToTransactionEntity(newTransaction);
            transaction.setId(oldTransactionId);
            transaction.setOperator(repository.findById(oldTransactionId).get().getOperator());
            repository.save(transaction);
        } else {
            throw new TransactionNotFoundException(oldTransactionId.toString());
        }
    }

    private boolean isTransactionExist(String transactionUUID) {
        return repository.findById(UUID.fromString(transactionUUID)).isPresent();
    }

    private Set<TransactionDTO> getTransactionDTOSet(Set<Transaction> transactions) {
        return transactions.stream().map(mapper::mapToTransactionDTO).collect(Collectors.toSet());
    }
}