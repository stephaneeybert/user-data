package com.thalasoft.user.data.it.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.thalasoft.user.data.jpa.domain.EmailAddress;
import com.thalasoft.user.data.jpa.domain.User;
import com.thalasoft.user.data.jpa.domain.UserRole;
import com.thalasoft.user.data.jpa.repository.UserRepository;
import com.thalasoft.user.data.jpa.repository.UserRoleRepository;

import org.junit.Before;
import org.junit.Test;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class UserRoleRepositoryTest extends AbstractRepositoryTest {

	// private static Logger logger = LoggerFactory.getLogger(UserRoleRepositoryTest.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	private User user0;
	private User user1;
	private UserRole userRole0;
	private UserRole userRole1;
	private UserRole userRole2;
	private List<UserRole> manyUserRoles;

	public UserRoleRepositoryTest() {

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

		userRole0 = new UserRole();
		userRole0.setUser(user0);
		userRole0.setRole("webpage");

		userRole1 = new UserRole();
		userRole1.setUser(user1);
		userRole1.setRole("elearning");

		userRole2 = new UserRole();
		userRole2.setUser(user1);
		userRole2.setRole("mail");

		manyUserRoles = new ArrayList<UserRole>();
		for (int i = 0; i < 40; i++) {
			UserRole oneUserRole = new UserRole();
			String index = intToString(i, 2);
			oneUserRole.setUser(user1);
			oneUserRole.setRole("role" + index);
			manyUserRoles.add(oneUserRole);
		}
	}

	@Before
	public void beforeAnyTest() throws Exception {
		user0 = userRepository.saveAndFlush(user0);
		user1 = userRepository.saveAndFlush(user1);
		userRole0 = userRoleRepository.saveAndFlush(userRole0);
		userRole1 = userRoleRepository.saveAndFlush(userRole1);
		userRole2 = userRoleRepository.saveAndFlush(userRole2);
		for (UserRole oneUserRole : manyUserRoles) {
			oneUserRole = userRoleRepository.saveAndFlush(oneUserRole);
		}
	}

	@Test
	public void testSaveAndRetrieve() {
		assertNotNull(userRole2.getId());
		userRole2.setRole("myrole");
		userRole2 = userRoleRepository.saveAndFlush(userRole2);
		Optional<UserRole> loadedUserRole = userRoleRepository.findById(userRole2.getId());
		assertNotNull(loadedUserRole.get().getId());
		assertEquals(userRole2.getUser(), loadedUserRole.get().getUser());
		assertEquals(userRole2.getRole(), loadedUserRole.get().getRole());
		assertNotSame(userRole2.hashCode(), 0L);
		assertEquals(userRole2.hashCode(), loadedUserRole.hashCode());
		assertFalse(userRole2.toString().equals(""));
	}

	@Test
	public void testDeleteById() {
		Optional<UserRole> loadedUserRole = userRoleRepository.findById(userRole0.getId());
		assertNotNull(loadedUserRole.get());
		userRoleRepository.deleteByUserRoleId(userRole0.getId());
		Optional<UserRole> myloadedUserRole = userRoleRepository.findById(userRole0.getId());
		assertFalse(myloadedUserRole.isPresent());
	}

	@Test
	public void testFindAllRoles() {
		assertEquals(43, userRoleRepository.count());
		Pageable pageRequest = PageRequest.of(0, 10);
		Page<UserRole> userRoles = userRoleRepository.findAllRoles(pageRequest);
		assertEquals(10, userRoles.getContent().size());
		assertEquals(userRole1.getRole(), userRoles.getContent().get(0).getRole());
		assertEquals(userRole2.getRole(), userRoles.getContent().get(1).getRole());
		assertEquals(manyUserRoles.get(0).getRole(), userRoles.getContent().get(2).getRole());
		assertEquals(manyUserRoles.get(1).getRole(), userRoles.getContent().get(3).getRole());
		pageRequest = PageRequest.of(1, 20);
		userRoles = userRoleRepository.findAllRoles(pageRequest);
		assertEquals(20, userRoles.getContent().size());
		assertEquals(manyUserRoles.get(18).getRole(), userRoles.getContent().get(0).getRole());
		assertEquals(manyUserRoles.get(19).getRole(), userRoles.getContent().get(1).getRole());
	}

	@Test
	public void testFindByUser() {
		List<UserRole> userRoles = userRoleRepository.findByUser(user0);
		assertEquals(1, userRoles.size());
		assertEquals(userRole0.getRole(), userRoles.get(0).getRole());
		userRoles = userRoleRepository.findByUser(user1);
		assertEquals(42, userRoles.size());
		assertEquals(userRole1.getRole(), userRoles.get(0).getRole());
		assertEquals(userRole2.getRole(), userRoles.get(1).getRole());
	}

	@Test
	public void testFindByUserAndRole() {
		UserRole userRole = userRoleRepository.findByUserAndRole(user0, userRole0.getRole());
		assertEquals(userRole0.getRole(), userRole.getRole());
		userRole = userRoleRepository.findByUserAndRole(user1, userRole1.getRole());
		assertEquals(userRole1.getRole(), userRole.getRole());
		userRole = userRoleRepository.findByUserAndRole(user1, userRole0.getRole());
		assertNull(userRole);
	}

}