package by.itechart.lastcoursetask.service;

import by.itechart.lastcoursetask.dto.OperatorDto;
import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.entity.Transaction;
import by.itechart.lastcoursetask.exception.TransactionExistException;
import by.itechart.lastcoursetask.exception.TransactionNotFoundException;
import by.itechart.lastcoursetask.repository.OperatorRepository;
import by.itechart.lastcoursetask.repository.TransactionRepository;
import by.itechart.lastcoursetask.util.EntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {
    private final OperatorRepository operatorRepository;
    private final TransactionRepository transactionRepository;
    private final EntityMapper mapper;

    public Set<TransactionDto> findAll() {
        Set<Transaction> transactions = new HashSet<>(transactionRepository.findAll());
        return getTransactionDTOSet(transactions);
    }

    public TransactionDto findById(UUID id) {
        return transactionRepository.findById(id).map(mapper::mapToTransactionDTO)
                .orElseThrow(() -> new TransactionNotFoundException(id.toString()));
    }

    public Set<TransactionDto> findByCustomerId(UUID customerId) {
        Set<Transaction> transactions = transactionRepository.findByCustomerId(customerId);
        return getTransactionDTOSet(transactions);
    }

    public Set<TransactionDto> findByDateAndTime(LocalDateTime dateTime) {
        Set<Transaction> transactions = transactionRepository.findByDateTime(dateTime);
        return getTransactionDTOSet(transactions);
    }

    public Set<TransactionDto> findByOperatorId(Long id) {
        Set<Transaction> transactions = transactionRepository.findByOperator_Id(id);
        return getTransactionDTOSet(transactions);
    }

    @Transactional
    public void delete(UUID transactionId) {
        if (transactionRepository.existsById(transactionId)) {
            transactionRepository.deleteById(transactionId);
        } else {
            throw new TransactionNotFoundException(transactionId.toString());
        }
    }

    @Transactional
    public void save(TransactionDto transactionDTO, OperatorDto operatorDTO) {
        if (!isTransactionExist(transactionDTO.getTransactionId())) {
            Transaction transaction = mapper.mapToTransactionEntity(transactionDTO);
            transaction.setOperator(mapper.mapToOperatorEntity(operatorDTO));
            transactionRepository.save(transaction);
        } else {
            throw new TransactionExistException(transactionDTO.getTransactionId());
        }
    }

    @Transactional
    public void saveAll(Collection<TransactionDto> transactions, OperatorDto operatorDTO) {
        for (TransactionDto transactionDTO : transactions) {
            save(transactionDTO, operatorDTO);
        }
    }

    @Transactional
    public void update(UUID oldTransactionId, TransactionDto newTransaction) {
        if (transactionRepository.existsById(oldTransactionId)) {
            Transaction transaction = mapper.mapToTransactionEntity(newTransaction);
            transaction.setId(oldTransactionId);
            transaction.setOperator(transactionRepository.getById(oldTransactionId).getOperator());
            transactionRepository.save(transaction);
        } else {
            throw new TransactionNotFoundException(oldTransactionId.toString());
        }
    }

    @Transactional
    public void updateOperator(UUID oldTransactionId, Long operatorId) {
        if (transactionRepository.existsById(oldTransactionId)) {
            Transaction transaction = transactionRepository.getById(oldTransactionId);
            transaction.setOperator(operatorRepository.getById(operatorId));
        } else {
            throw new TransactionNotFoundException(oldTransactionId.toString());
        }
    }

    private boolean isTransactionExist(String transactionUUID) {
        return transactionRepository.findById(UUID.fromString(transactionUUID)).isPresent();
    }

    private Set<TransactionDto> getTransactionDTOSet(Set<Transaction> transactions) {
        return transactions.stream().map(mapper::mapToTransactionDTO).collect(Collectors.toSet());
    }
}