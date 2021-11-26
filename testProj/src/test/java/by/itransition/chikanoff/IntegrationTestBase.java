package by.itransition.chikanoff;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class IntegrationTestBase {

}
