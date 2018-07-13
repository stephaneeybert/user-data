package com.thalasoft.user.data.jpa.repository;

import com.thalasoft.user.data.jpa.domain.User;

public interface UserRepositoryCustom {

	public User deleteByUserId(Long id);

}
