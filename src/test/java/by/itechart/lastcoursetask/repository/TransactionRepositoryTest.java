package by.itechart.lastcoursetask.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository repository;

    @Test
    void findByCustomerId() {
    }
}