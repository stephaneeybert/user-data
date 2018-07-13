package com.thalasoft.user.data.jpa.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import com.thalasoft.user.data.exception.EntityNotFoundException;
import com.thalasoft.user.data.jpa.domain.User;

public class UserRepositoryImpl implements UserRepositoryCustom {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public User deleteByUserId(Long id) {
		Optional<User> entity = userRepository.findById(id);
		if (entity.isPresent()) {
			userRepository.delete(entity.get());
		} else {
			throw new EntityNotFoundException("The user entity could not be found and was not deleted");
		}
		return entity.get();
	}
	
}
