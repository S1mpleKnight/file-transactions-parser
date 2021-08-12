package by.itechart.lastcoursetask.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
    void findByFirstNameAndLastNameSuccess() {
        Assertions.assertEquals(5L, repository.findByFirstNameAndLastName("Boss", "Galaxy").getId());
    }
}