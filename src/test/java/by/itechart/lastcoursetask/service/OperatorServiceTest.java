package by.itechart.lastcoursetask.service;

import by.itechart.lastcoursetask.dto.OperatorDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@ActiveProfiles("test")
class OperatorServiceTest {
    private static final Long OPERATOR_DTO_ID_UPDATE = 4L;
    private static final Long OPERATOR_DTO_ID_DELETE = 3L;
    private static final Long OPERATOR_DTO_ID_SAVE = 3L;
    @Autowired
    private OperatorService operatorService;

    @Test
    void findAllSizeNotNullTrue() {
        assertNotEquals(0, operatorService.findAll().size());
    }

    @Test
    void findByIdSuccess() {
        assertEquals("Boss", operatorService.findById(5L).getFirstName());
    }

    @Test
    void findByNickNameSuccess() {
        assertEquals("user", operatorService.findByNickName("deshda").getRole());
    }

    @Test
    void findByFirstNameAndLastNameSuccess() {
        assertEquals(1L, operatorService.findByFirstNameAndLastName("Petya", "Hechiparenko").getId());
    }

    @Test
    void saveOperatorSuccess() {
        OperatorDTO operatorDTO = createOperatorDTO("Vanya", "admin","fly");
        operatorDTO.setId(OPERATOR_DTO_ID_SAVE);
        Assertions.assertDoesNotThrow(() -> operatorService.save(operatorDTO));
    }

    @Test
    void updateOperatorSuccess() {
        OperatorDTO operatorDTO = createOperatorDTO("Muslim", "user", "suicide");
        Assertions.assertDoesNotThrow(() -> operatorService.update(OPERATOR_DTO_ID_UPDATE, operatorDTO));
    }

    @Test
    void deleteOperatorSuccess() {
        //todo: fix
        Assertions.assertDoesNotThrow(() -> operatorService.delete(OPERATOR_DTO_ID_DELETE));
    }

    private OperatorDTO createOperatorDTO(String name, String role, String nickname) {
        OperatorDTO operatorDTO = new OperatorDTO();
        operatorDTO.setFirstName(name);
        operatorDTO.setLastName(name);
        operatorDTO.setRole(role);
        operatorDTO.setNickname(nickname);
        operatorDTO.setPassword("qwerty123");
        return operatorDTO;
    }
}