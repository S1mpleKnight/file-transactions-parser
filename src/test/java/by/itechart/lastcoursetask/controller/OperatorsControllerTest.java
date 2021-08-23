package by.itechart.lastcoursetask.controller;

import by.itechart.lastcoursetask.dto.OperatorDto;
import by.itechart.lastcoursetask.service.OperatorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:test.properties")
@WebMvcTest(OperatorsController.class)
class OperatorsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OperatorService operatorService;
    private static List<OperatorDto> operators;

    @BeforeAll
    static void init() {
        operators = getOperators();
    }

    @Test
    void givenOperators_whenGetAllOperators_thenReturnJsonArray() throws Exception {
        given(operatorService.findAll()).willReturn(operators);
        mockMvc.perform(get("/operators").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
    }

    @Test
    void givenOperator_whenGetOperatorById_thenReturnJsonValue() throws Exception {
        given(operatorService.findById(1L)).willReturn(operators.get(1));
        mockMvc.perform(get("/operators/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("@.firstName", is("Tanya")));
    }

    private static List<OperatorDto> getOperators() {
        OperatorDto first = getOperatorDto("Vanya", "Zelezinsky", 32L, "ivanessence");
        OperatorDto second = getOperatorDto("Tanya", "Bobrova", 45L, "bobrik");
        List<OperatorDto> operators = new ArrayList<>();
        operators.add(first);
        operators.add(second);
        return operators;
    }

    private static OperatorDto getOperatorDto(String firstName, String lastName, long id, String nickname) {
        OperatorDto second = new OperatorDto();
        second.setFirstName(firstName);
        second.setLastName(lastName);
        second.setId(id);
        second.setNickname(nickname);
        second.setPassword("123qwe123");
        second.setRole("user");
        return second;
    }
}