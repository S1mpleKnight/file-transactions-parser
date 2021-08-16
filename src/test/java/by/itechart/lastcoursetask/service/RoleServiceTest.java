package by.itechart.lastcoursetask.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@ActiveProfiles("test")
class RoleServiceTest {
    @Autowired
    private RoleService roleService;

    @Test
    void findByValueSuccess() {
        Assertions.assertDoesNotThrow(() -> roleService.findByName("admin").getId());
    }

    @Test
    void findByIdFail() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> roleService.findById(3L));
    }
}