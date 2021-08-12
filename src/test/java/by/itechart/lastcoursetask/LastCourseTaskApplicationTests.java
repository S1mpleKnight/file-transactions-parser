package by.itechart.lastcoursetask;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class LastCourseTaskApplicationTests {

    @Test
    void contextLoadsSuccess() {
        LastCourseTaskApplication.main(new String[]{});
    }

}
