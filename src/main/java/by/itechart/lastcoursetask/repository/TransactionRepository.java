package by.itechart.lastcoursetask.repository;

import by.itechart.lastcoursetask.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByCustomerId(UUID customerId);

    List<Transaction> findByDateTime(LocalDateTime dateTime);

    List<Transaction> findByOperator_Id(Long id);

    @Query(value = "SELECT DISTINCT * FROM transactions WHERE amount = (SELECT MIN(amount) FROM transactions)",
            nativeQuery = true)
    List<Transaction> findMinTransactions();

    @Query(value = "SELECT DISTINCT * FROM transactions WHERE amount = (SELECT MAX(amount) FROM transactions)",
            nativeQuery = true)
    List<Transaction> findMaxTransactions();
}
