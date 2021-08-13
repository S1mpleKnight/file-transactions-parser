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
class RoleRepositoryTest {
    @Autowired
    private RoleRepository repository;

    @Test
    void findByValueSuccess() {
        Assertions.assertEquals("user", repository.findByName("user").getName());
    }

    @Test
    void existByValueTrue() {
        Assertions.assertTrue(repository.existsByName("admin"));
    }
}