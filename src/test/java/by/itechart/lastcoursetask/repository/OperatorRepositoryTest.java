package by.itechart.lastcoursetask.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "classpath:test.properties")
@ActiveProfiles("test")
@SpringBootTest
class OperatorRepositoryTest {
    @Autowired
    private OperatorRepository repository;

    @Test
    void findByNicknameSuccess() {
        Assertions.assertEquals("Misha", repository.findByNickname("genius").getFirstName());
    }

    @Test
    void existByNicknameTrue() {
        Assertions.assertTrue(repository.existsByNickname("adminchik"));
    }

    @Test
    void existByFirstNameAndLastNameTrue() {
        Assertions.assertTrue(repository.existsByFirstNameAndLastName("Dasha", "Deshina"));
    }

    @Test
    void findByFirstNameAndLastNameSuccess() {
        Assertions.assertEquals(5L, repository.findByFirstNameAndLastName("Boss", "Galaxy").getId());
    }
}