package com.thalasoft.user.data.it.service;

import static com.thalasoft.user.data.assertion.UserAssert.assertThatUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import com.thalasoft.user.data.it.BaseTest;
import com.thalasoft.user.data.jpa.domain.EmailAddress;
import com.thalasoft.user.data.jpa.domain.User;
import com.thalasoft.user.data.service.UserService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class UserServiceTest extends BaseTest {

	@Autowired
	private UserService userService;

	private User user0;
	private List<User> manyUsers;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserServiceTest() {
	}

	@Before
	public void beforeAnyTest() throws Exception {
		user0 = new User();
		user0.setFirstname("Stephane");
		user0.setLastname("Eybert");
		user0.setEmail(new EmailAddress("stephane@thalasoft.com"));
		user0.setPassword("toto");
		user0.setPasswordSalt("");
		userService.add(user0);

		manyUsers = new ArrayList<User>();
		for (int i = 0; i < 40; i++) {
			String index = intToString(i, 3);

			User oneUser = new User();
			oneUser.setFirstname("spirou" + index);
			oneUser.setFirstname("zfirstname" + index);
			oneUser.setLastname("zlastname" + index);
			oneUser.setEmail(new EmailAddress("zemail" + index
					+ "@thalasoft.com"));
			oneUser.setPassword("zpassword" + index);
			oneUser.setPasswordSalt("");
			userService.add(oneUser);
			manyUsers.add(oneUser);
		}
	}

	@After
	public void afterAnyTest() {
		userService.delete(user0.getId());
		for (User oneUser : manyUsers) {
			userService.delete(oneUser.getId());
		}
	}

	@Test
	public void testFindByLogin() {
		User user = userService.findByEmail(user0.getEmail().toString());
		assertThatUser(user).hasAnIdNotNull().hasEmail(user0.getEmail());
	}

	@Test
	public void testAdd() {
		User user = new User();
		user.setFirstname("Marc");
		user.setLastname("Lopez");
		user.setEmail(new EmailAddress("marco@thalasoft.com"));
		user.setPassword("pireloup");
		user.setPasswordSalt("");

		user = userService.add(user);
		assertNotNull(user.getId());
	}

	@Test
	public void testAddRole() {
		user0 = userService.addRole(user0, "admin");
		assertThatUser(user0).hasRole("admin");
		user0 = userService.addRole(user0, "manager");
		assertThatUser(user0).hasRole("manager");
	}

	@Test
	public void testDeleteRole() {
		user0 = userService.addRole(user0, "admin");
		user0 = userService.addRole(user0, "manager");
		user0 = userService.removeRole(user0, "admin");
		assertThatUser(user0).hasNotRole("admin");
		user0 = userService.removeRole(user0, "manager");
		assertThatUser(user0).hasNotRole("manager");
	}

	@Test
	public void testUpdate() {
		String firstname = "Marcus";
		user0.setFirstname(firstname);
		user0.setLastname("");
		user0 = userService.update(user0.getId(), user0);
		assertThatUser(user0).hasFirstname(firstname);
		assertThatUser(user0).hasLastname("");
	}

	@Test
	public void testPartialUpdate() {
		String firstname = "Marcus";
		user0.setFirstname(firstname);
		String originalLastname = user0.getLastname();
		user0.setLastname("");
		user0 = userService.partialUpdate(user0.getId(), user0);
		assertThatUser(user0).hasFirstname(firstname);
		assertThatUser(user0).hasLastname(originalLastname);
	}

	@Test
	public void testSearch() {
		Pageable pageRequest = buildPageRequest(0, 10);
		Page<User> users = userService.search("zfirstname", pageRequest);
		assertEquals(10, users.getContent().size());
		assertEquals(manyUsers.get(0).getFirstname(), users.getContent().get(0)
				.getFirstname());
		assertEquals(manyUsers.get(1).getFirstname(), users.getContent().get(1)
				.getFirstname());
		pageRequest = buildPageRequest(1, 20);
		users = userService.search("zfirstname", pageRequest);
		assertEquals(20, users.getContent().size());
		assertEquals(manyUsers.get(20).getFirstname(), users.getContent()
				.get(0).getFirstname());
		assertEquals(manyUsers.get(39).getFirstname(),
				users.getContent().get(19).getFirstname());

		pageRequest = buildPageRequest(0, 10);
		users = userService.search("zfirstname", pageRequest);
		assertEquals(10, users.getNumberOfElements());
		assertEquals(manyUsers.get(0).getFirstname(), users.getContent().get(0)
				.getFirstname());
		pageRequest = buildPageRequest(1, 20);
		users = userService.search("zfirstname", pageRequest);
		assertEquals(20, users.getNumberOfElements());
		assertEquals(manyUsers.get(20).getFirstname(), users.getContent()
				.get(0).getFirstname());
		assertEquals(manyUsers.get(39).getFirstname(),
				users.getContent().get(19).getFirstname());
	}

	private Pageable buildPageRequest(int pageIndex, int pageSize) {
		Sort sort = Sort.by(Sort.Order.asc("lastname"), Sort.Order.asc("firstname"));
		return PageRequest.of(pageIndex, pageSize, sort);
	}

}