package com.thalasoft.user.data.it.jpa;

import static com.thalasoft.user.data.assertion.UserAssert.assertThatUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.thalasoft.user.data.it.BaseTest;
import com.thalasoft.user.data.jpa.domain.EmailAddress;
import com.thalasoft.user.data.jpa.domain.User;
import com.thalasoft.user.data.jpa.repository.UserRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class UserRepositoryTest extends BaseTest {

    @Autowired
    UserRepository userRepository;

    private User user0;
    private User user1;
    private User user2;
    private User user3;
    private List<User> manyUsers;

    private Sort sort;

    public UserRepositoryTest() {
        user0 = new User();
        user0.setFirstname("Stephane");
        user0.setLastname("Eybert");
        user0.setEmail(new EmailAddress("stephane@thalasoft.com"));
        user0.setPassword("toto");
        user0.setPasswordSalt("");

        user1 = new User();
        user1.setFirstname("Marc");
        user1.setLastname("Eybert");
        user1.setEmail(new EmailAddress("marceybert@yahoo.fr"));
        user1.setPassword("marcus");
        user1.setPasswordSalt("");

        user2 = new User();
        user2.setFirstname("Cyril");
        user2.setLastname("Eybert");
        user2.setEmail(new EmailAddress("cyril@yahoo.es"));
        user2.setPassword("cyr");
        user2.setPasswordSalt("");

        user3 = new User();
        user3.setFirstname("Stefan");
        user3.setLastname("Eybert");
        user3.setEmail(new EmailAddress("stefan@thalasoft.com"));
        user3.setPassword("toto");
        user3.setPasswordSalt("");

        manyUsers = new ArrayList<User>();
        for (int i = 0; i < 39; i++) {
            String index = intToString(i, 3);

            User oneUser = new User();
            oneUser.setFirstname("spirou" + index);
            oneUser.setLastname("zlastname" + index);
            oneUser.setEmail(new EmailAddress("zemail" + index + "@thalasoft.com"));
            oneUser.setPassword("zpassword" + index);
            oneUser.setPasswordSalt("");

            manyUsers.add(oneUser);
        }

        sort = Sort.by(Sort.Order.asc("email"), Sort.Order.asc("firstname"));
    }

    @Before
    public void beforeAnyTest() throws Exception {
        user0 = userRepository.saveAndFlush(user0);
        user1 = userRepository.saveAndFlush(user1);
        user2 = userRepository.saveAndFlush(user2);
        user3 = userRepository.saveAndFlush(user3);
        for (User oneUser : manyUsers) {
            oneUser = userRepository.saveAndFlush(oneUser);
        }
    }

    @After
    public void afterAnyTest() {
        userRepository.delete(user0);
        userRepository.delete(user1);
        userRepository.delete(user2);
        userRepository.delete(user3);
        for (User oneUser : manyUsers) {
            userRepository.delete(oneUser);
        }
    }

    @Test
    public void testSaveAndRetrieve() {
        assertNotNull(user2.getId());
        Optional<User> loadedUser = userRepository.findById(user2.getId());
        assertThatUser(loadedUser.get()).hasAnIdNotNull().hasEmail(user2.getEmail()).hasPassword(user2.getPassword())
                .isSameAs(user2);
        assertEquals(user2.getUserRoles().size(), loadedUser.get().getUserRoles().size());
    }

    @Test
    public void testDeleteById() {
        Optional<User> loadedUser = userRepository.findById(user0.getId());
        assertTrue(loadedUser.isPresent());
        userRepository.deleteById(user0.getId());
        loadedUser = userRepository.findById(user0.getId());
        assertFalse(loadedUser.isPresent());
    }

    @Test
    public void testDeleteByUserId() {
        Optional<User> loadedUser = userRepository.findById(user0.getId());
        assertTrue(loadedUser.isPresent());
        userRepository.deleteByUserId(user0.getId());
        loadedUser = userRepository.findById(user0.getId());
        assertFalse(loadedUser.isPresent());
    }

    @Test
    public void testFindByEmail() {
        Optional<User> loadedUser = userRepository.findByEmail(user0.getEmail());
        assertThatUser(loadedUser.get()).hasEmail(user0.getEmail());
    }

    @Test
    public void testFindByEmailAndPassword() {
        Optional<User> loadedUser = userRepository.findByEmailAndPassword(user0.getEmail(), "toto");
        assertThatUser(loadedUser.get()).hasEmail(user0.getEmail());
    }

    @Test
    public void testFindByFirstnameStartingWithOrLastnameStartingWith() {
        Page<User> users = userRepository.searchOnName("spirou", PageRequest.of(0, 10, sort));
        assertEquals(10, users.getContent().size());
        users = userRepository.searchOnName("noname", PageRequest.of(0, 10, sort));
        assertEquals(0, users.getContent().size());
    }

    @Test
    public void testFindAll() {
        assertEquals(43, userRepository.count());
        Pageable pageRequest = PageRequest.of(0, 10, sort);
        Page<User> users = userRepository.all(pageRequest);
        assertEquals(10, users.getContent().size());
        assertEquals("Cyril", users.getContent().get(0).getFirstname());
        assertEquals("Marc", users.getContent().get(1).getFirstname());
        assertEquals("Stefan", users.getContent().get(2).getFirstname());
        assertEquals("Stephane", users.getContent().get(3).getFirstname());
        assertEquals("spirou000", users.getContent().get(4).getFirstname());
        pageRequest = PageRequest.of(1, 20, sort);
        users = userRepository.all(pageRequest);
        assertEquals(20, users.getContent().size());
        assertEquals("spirou016", users.getContent().get(0).getFirstname());
        assertEquals("spirou035", users.getContent().get(19).getFirstname());
        pageRequest = PageRequest.of(2, 20, sort);
        users = userRepository.all(pageRequest);
        assertEquals(3, users.getContent().size());
        assertEquals("spirou038", users.getContent().get(2).getFirstname());
    }

    @Test
    public void testSearchByFirstname() {
        Page<User> users = userRepository.searchOnName("spirou", PageRequest.of(0, 10, sort));
        assertEquals(10, users.getContent().size());
        users = userRepository.searchOnName("Ste", PageRequest.of(0, 10, sort));
        assertEquals(2, users.getContent().size());
        users = userRepository.searchOnName("Steph", PageRequest.of(0, 10, sort));
        assertEquals(1, users.getContent().size());
    }

    @Test
    public void testSearch() {
        Page<User> users = userRepository.search("spirou", PageRequest.of(0, 10, sort));
        assertEquals(10, users.getContent().size());
        assertEquals(39, users.getTotalElements());
        assertEquals(4, users.getTotalPages());
        assertEquals("spirou000", users.getContent().get(0).getFirstname());
        assertEquals("spirou001", users.getContent().get(1).getFirstname());
        users = userRepository.search("spirou", PageRequest.of(1, 20, sort));
        assertEquals(19, users.getContent().size());
        assertEquals("spirou020", users.getContent().get(0).getFirstname());
        assertEquals("spirou038", users.getContent().get(18).getFirstname());
    }

    @Test
    public void testUpdateUserDoesNotUpdatePassword() {
        assertNotNull(user1.getId());
        user1.setFirstname("Charlie");
        user1 = userRepository.saveAndFlush(user1);
        Optional<User> loadedUser = userRepository.findById(user1.getId());
        assertNotNull(loadedUser.get().getId());
        assertEquals(user1.getPassword(), loadedUser.get().getPassword());
    }

}