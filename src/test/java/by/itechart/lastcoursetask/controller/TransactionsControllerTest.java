package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.dto.TransactionDto;
import by.itechart.lastcoursetask.exception.TransactionNotFoundException;
import by.itechart.lastcoursetask.service.TransactionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(locations = "classpath:test.properties")
@ActiveProfiles("test")
@WebMvcTest(TransactionsController.class)
class TransactionsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransactionService transactionService;
    private static List<TransactionDto> transactions;

    @BeforeAll
    static void init() {
        transactions = getTransactions();
    }

    @Test
    void givenTransactions_whenGetAllTransactions_isEqualCurrency() throws Exception {
        given(transactionService.findAll()).willReturn(transactions);
        mockMvc.perform(get("/transactions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].currency", is("BYN")));
    }

    @Test
    void isNotFound_whenGetByInvalidIdTransactions() throws Exception {
        given(transactionService.findById("111111111"))
                .willThrow(new TransactionNotFoundException("111111111"));
        mockMvc.perform(get("/transaction/111111111").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenTransaction_whenFindByCustomerId_isEqualAmount() throws Exception {
        given(transactionService.findByCustomerId("2cc22e9a-86aa-4580-b553-5d636c91ee7a"))
                .willReturn(Collections.singletonList(transactions.get(0)));
        mockMvc.perform(get("/transactions/customers/2cc22e9a-86aa-4580-b553-5d636c91ee7a")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private static List<TransactionDto> getTransactions() {
        TransactionDto first = createTransaction("d30b3675-d4b4-46bf-8bbd-371857d23c67",
                "2cc22e9a-86aa-4580-b553-5d636c91ee7a", "27.04.2021 08:29:14", "USD",
                "78965", true);
        TransactionDto second = createTransaction("4b32597e-a465-4242-91a2-c6fade63f675",
                "100caf1c-3468-4351-8f2c-45ea6bad3825", "25.04.2021 10:56:00", "BYN",
                "898945213", false);
        List<TransactionDto> transactions = new ArrayList<>();
        transactions.add(first);
        transactions.add(second);
        return transactions;
    }

    private static TransactionDto createTransaction(String transactionId, String customerId, String dateTime,
                                                    String currency, String amount, Boolean status) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionId(transactionId);
        transactionDto.setCustomerId(customerId);
        transactionDto.setCurrency(currency);
        transactionDto.setAmount(amount);
        transactionDto.setDateTime(LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        transactionDto.setStatus(status);
        return transactionDto;
    }
}