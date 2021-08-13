package by.itechart.lastcoursetask.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class RoleRepositoryTest {
    @Autowired
    private RoleRepository repository;

    @Test
    void findByValueSuccess() {
        Assertions.assertEquals("user", repository.findByValue("user").getValue());
    }

    @Test
    void existByValueTrue() {
        Assertions.assertTrue(repository.existsByValue("admin"));
    }
}