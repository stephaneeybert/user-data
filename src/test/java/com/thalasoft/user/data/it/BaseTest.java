package com.thalasoft.user.data.it;

import com.thalasoft.user.data.config.DatabaseConfiguration;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = { DatabaseConfiguration.class })
@RunWith(SpringRunner.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:mysql/clean-up-before-each-test.sql" })
public abstract class BaseTest {

    protected String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) {
            output = "0" + output;
        }
        return output;
    }

}
