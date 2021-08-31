package by.itechart.lastcoursetask.service;

import by.itechart.lastcoursetask.dto.OperatorDto;
import by.itechart.lastcoursetask.dto.TransactionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:test.properties")

class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private OperatorService operatorService;

    @Test
    void findAllSizeNotNullTrue() {
        assertNotEquals(0, transactionService.findAll().size());
    }

    @Test
    void findByIdEqualsAmountTrue() {
        assertEquals("4942", transactionService.findById("7499c7f2-c8e6-4ea2-8a02-e5e2bf5a21c8").getAmount());
    }

    @Test
    void findByCustomerIdSizeNotNullTrue() {
        assertFalse(transactionService.findByCustomerId("bebb0888-2a6f-4f38-b8e7-800a2a3145e7").isEmpty());
    }

    @Test
    void findByDateAndTimeStatusEqualsTrueSuccess() {
        assertEquals(true, transactionService.findByDateAndTime(LocalDateTime.parse("29.04.2021 09:04:13",
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))).iterator().next().getStatus());
    }

    @Test
    void saveTransactionFail() {
        TransactionDto transactionDTO = createTransaction("1c22f114-8251-404e-8c3f-73b70bd0ec80", "a00a7fb0-3a72-454d-865d-8f6818f8dd62");
        OperatorDto operatorDTO = operatorService.findById(1L);
        assertThrows(IllegalArgumentException.class, () -> transactionService.save(transactionDTO, operatorDTO));
    }

    @Test
    void deleteTransactionSuccess() {
        assertDoesNotThrow(() -> transactionService.delete(UUID.fromString("b54ca174-03f1-4a87-a1f2-dee732a8b754")));
    }

    private TransactionDto createTransaction(String transactionId, String customerId) {
        TransactionDto transactionDTO = new TransactionDto();
        transactionDTO.setStatus(true);
        transactionDTO.setCurrency("usd");
        transactionDTO.setAmount("123123");
        transactionDTO.setDateTime(LocalDateTime.now());
        transactionDTO.setTransactionId(transactionId);
        transactionDTO.setCustomerId(customerId);
        return transactionDTO;
    }
}