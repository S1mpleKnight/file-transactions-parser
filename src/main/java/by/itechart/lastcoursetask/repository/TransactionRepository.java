package by.itechart.lastcoursetask.repository;

import by.itechart.lastcoursetask.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Set<Transaction> findByCustomerId(UUID customerId);

    Set<Transaction> findByDateTime(LocalDateTime dateTime);

    Set<Transaction> findByOperator_Id(Long id);
}
