package by.itechart.lastcoursetask.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository repository;

    @Test
    void findByCustomerIdSuccess() {
        Assertions.assertEquals(1,
                repository.findByCustomerId(UUID.fromString("a00a7fb0-3a72-454d-865d-8f6818f8dd62")).size());
    }

    @Test
    void findByDateAndTimeSuccess() {
        Assertions.assertEquals(34623,
                repository.findByDateTime(LocalDateTime.parse("29.04.2021 08:29:14",
                        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))).iterator().next().getAmount().intValue());
    }
}