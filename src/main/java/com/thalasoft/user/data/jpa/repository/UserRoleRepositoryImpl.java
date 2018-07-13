package com.thalasoft.user.data.jpa.repository;

import java.util.Optional;

import com.thalasoft.user.data.exception.EntityNotFoundException;
import com.thalasoft.user.data.jpa.domain.UserRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class UserRoleRepositoryImpl implements UserRoleRepositoryCustom {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	@Transactional
	public UserRole deleteByUserRoleId(Long id) {
		Optional<UserRole> entity = userRoleRepository.findById(id);
		if (entity.isPresent()) {
			userRoleRepository.delete(entity.get());
		} else {
			throw new EntityNotFoundException("The user role entity could not be found and was not deleted");
		}
		return entity.get();
	}

}
