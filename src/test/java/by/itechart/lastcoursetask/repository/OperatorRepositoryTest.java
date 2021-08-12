package by.itechart.lastcoursetask.repository;

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
    void findByNickname() {
    }

    @Test
    void findByFirstNameAndLastName() {
    }
}