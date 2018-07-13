package com.thalasoft.user.data.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thalasoft.user.data.exception.EntityAlreadyExistsException;
import com.thalasoft.user.data.exception.EntityNotFoundException;
import com.thalasoft.user.data.jpa.domain.User;
import com.thalasoft.user.data.jpa.domain.UserRole;
import com.thalasoft.user.data.jpa.repository.UserRoleRepository;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Resource
	UserService userService;

	@Resource
	private UserRoleRepository userRoleRepository;

	@Override
	public UserRole findById(Long id) {
		Optional<UserRole> userRole = userRoleRepository.findById(id);
		if (userRole.isPresent()) {
			return userRole.get();
		} else {
			return null;
		}
	}

	@Modifying
	@Transactional(rollbackFor = EntityAlreadyExistsException.class)
	@Override
	public UserRole add(UserRole userRole) throws EntityAlreadyExistsException {
		if (findByUserAndRole(userRole.getUser().getId(), userRole.getRole()) == null) {
			// Save the returned id into the entity
			userRole = userRoleRepository.saveAndFlush(userRole);
			return userRole;
		} else {
			throw new EntityAlreadyExistsException();
		}
	}

	@Modifying
	@Transactional(rollbackFor = EntityNotFoundException.class)
	@Override
	public UserRole update(Long id, UserRole userRole) throws EntityNotFoundException {
		UserRole foundUserRole = findById(id);
		if (foundUserRole == null) {
			throw new EntityNotFoundException();
		} else {
			BeanUtils.copyProperties(userRole, foundUserRole);
			return foundUserRole;
		}
	}

	@Modifying
	@Transactional(rollbackFor = EntityNotFoundException.class)
	@Override
	public UserRole delete(Long id) throws EntityNotFoundException {
		UserRole userRole = findById(id);
		if (userRole == null) {
			throw new EntityNotFoundException();
		} else {
			userRoleRepository.delete(userRole);
			return userRole;
		}
	}

	public UserRole findByUserAndRole(Long userId, String role) {
		User user = userService.findById(userId);
		if (user != null) {
			return userRoleRepository.findByUserAndRole(user, role);
		} else {
			return null;
		}
	}

	public List<UserRole> findByUser(Long userId) {
		User user = userService.findById(userId);
		if (user != null) {
			return userRoleRepository.findByUser(user);
		} else {
			return null;
		}
	}

	public void deleteByUser(Long userId) {
		User user = userService.findById(userId);
		if (user != null) {
			userRoleRepository.deleteByUser(user);
		}
	}

}
