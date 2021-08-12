package by.itechart.lastcoursetask.repository;

import by.itechart.lastcoursetask.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, UUID> {
    Set<Transaction> findByCustomerId(UUID customerId);
}
