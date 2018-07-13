package com.thalasoft.user.data.ut;

import com.thalasoft.user.data.config.DatabaseConfiguration;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = { DatabaseConfiguration.class })
@RunWith(SpringRunner.class)
public abstract class AbstractRepositoryTest {
}
