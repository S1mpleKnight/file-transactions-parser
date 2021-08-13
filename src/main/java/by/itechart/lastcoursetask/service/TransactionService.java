package by.itechart.lastcoursetask.service;

import by.itechart.lastcoursetask.dto.TransactionDTO;
import by.itechart.lastcoursetask.entity.Customer;
import by.itechart.lastcoursetask.entity.Operator;
import by.itechart.lastcoursetask.entity.Transaction;
import by.itechart.lastcoursetask.repository.TransactionRepository;
import by.itechart.lastcoursetask.util.EntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final EntityMapper mapper;

    public Set<TransactionDTO> findAll() {
        Set<Transaction> transactions = (Set<Transaction>) repository.findAll();
        return getTransactionDTOSet(transactions);
    }

    public TransactionDTO findById(UUID id) {
        return repository.findById(id).map(mapper::mapToTransactionDTO)
                .orElseThrow(() -> new IllegalArgumentException("Transaction is not exist, id: " + id));
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
    public void delete(TransactionDTO transactionDTO) {
        if (isTransactionExist(transactionDTO.getTransactionId())) {
            repository.delete(mapper.mapToTransactionEntity(transactionDTO));
        } else {
            throw new IllegalArgumentException("Transaction is not exist");
        }
    }

    @Transactional
    public void save(TransactionDTO transactionDTO, Operator operator, Customer customer) {
        if (!isTransactionExist(transactionDTO.getTransactionId())) {
            repository.save(fillTransactionField(transactionDTO, operator, customer));
        } else {
            throw new IllegalArgumentException("Transaction is already exist");
        }
    }

    @Transactional
    public void update(TransactionDTO oldTransaction, TransactionDTO newTransaction) {
        if (isTransactionExist(oldTransaction.getTransactionId())) {
            Transaction transaction = mapper.mapToTransactionEntity(newTransaction);
            transaction.setId(UUID.fromString(oldTransaction.getTransactionId()));
            repository.save(transaction);
        } else {
            throw new IllegalArgumentException("Transaction is already exist");
        }
    }

    private Transaction fillTransactionField(TransactionDTO transactionDTO, Operator operator, Customer customer) {
        Transaction transaction = mapper.mapToTransactionEntity(transactionDTO);
        transaction.setOperator(operator);
        fillTransactionCustomerFields(customer, transaction);
        return transaction;
    }

    private void fillTransactionCustomerFields(Customer customer, Transaction transaction) {
        if (customer != null) {
            transaction.setCustomerEmail(customer.getEmail());
            transaction.setCustomerFirstName(customer.getFirstName());
            transaction.setCustomerLastName(customer.getLastName());
        }
    }

    private boolean isTransactionExist(String transactionUUID) {
        return repository.findById(UUID.fromString(transactionUUID)).isPresent();
    }

    private Set<TransactionDTO> getTransactionDTOSet(Set<Transaction> transactions) {
        return transactions.stream().map(mapper::mapToTransactionDTO).collect(Collectors.toSet());
    }
}
