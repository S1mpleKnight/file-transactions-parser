package by.itechart.lastcoursetask.repository;

import by.itechart.lastcoursetask.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;
import java.util.UUID;

public interface TransactionRepository extends CrudRepository<Transaction, UUID> {
    Set<Transaction> findByCustomerId(UUID customerId);
}
