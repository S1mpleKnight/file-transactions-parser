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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {
    private final static String ID_REGEX = "[0-9a-z]{8}-([0-9a-z]{4}-){3}[0-9a-z]{12}";
    private final OperatorRepository operatorRepository;
    private final TransactionRepository transactionRepository;
    private final EntityMapper mapper;

    public Page<TransactionDto> findAll(Pageable pageable) {
        return new PageImpl<>(getTransactionDtoList(transactionRepository.findAll(pageable).stream().toList()));
    }

    public List<TransactionDto> findAll() {
        return getTransactionDtoList(transactionRepository.findAll().stream().toList());
    }

    public TransactionDto findById(String id) {
        if (id.matches(ID_REGEX)) {
            return transactionRepository.findById(UUID.fromString(id)).map(mapper::mapToTransactionDTO)
                    .orElseThrow(() -> new TransactionNotFoundException(id));
        }
        throw new TransactionNotFoundException(id);
    }

    public List<TransactionDto> findByCustomerId(String customerId) {
        if (customerId.matches(ID_REGEX)){
            List<Transaction> transactions = transactionRepository.findByCustomerId(UUID.fromString(customerId));
            return getTransactionDtoList(transactions);
        }
        throw new TransactionNotFoundException(customerId);
    }

    public List<TransactionDto> findByOperatorId(Long id) {
        List<Transaction> transactions = transactionRepository.findByOperator_Id(id);
        return getTransactionDtoList(transactions);
    }

    public List<TransactionDto> findMinTransactions() {
        List<Transaction> transactions = Collections.singletonList(transactionRepository.findTopByOrderByAmountAsc());
        return getTransactionDtoList(transactions);
    }

    public List<TransactionDto> findMaxTransactions() {
        List<Transaction> transactions = Collections.singletonList(transactionRepository.findTopByOrderByAmountDesc());
        return getTransactionDtoList(transactions);
    }

    public List<TransactionDto> findAboveTransactions(Long amount) {
        List<Transaction> transactions = transactionRepository.findAllByAmountGreaterThan(amount);
        return getTransactionDtoList(transactions);
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

    private List<TransactionDto> getTransactionDtoList(List<Transaction> transactions) {
        return transactions.stream().map(mapper::mapToTransactionDTO).collect(Collectors.toList());
    }
}