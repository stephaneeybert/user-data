package com.thalasoft.user.data.ut;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thalasoft.user.data.jpa.domain.EmailAddress;
import com.thalasoft.user.data.jpa.domain.User;

public class UserRepositoryTest extends AbstractRepositoryTest {

  private User user0;

  public UserRepositoryTest() {
    user0 = new User();
    user0.setFirstname("Stephane");
    user0.setLastname("Eybert");
    user0.setEmail(new EmailAddress("stephane@thalasoft.com"));
    user0.setPassword("toto");
    user0.setPasswordSalt("");
  }

  @Test
  public void testSaveAndRetrieve() {
    assertEquals("Stephane", user0.getFirstname());
  }

}