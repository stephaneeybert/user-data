package com.thalasoft.user.data.service;

import java.util.List;

import com.thalasoft.user.data.jpa.domain.UserRole;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRoleService {

	public UserRole add(UserRole userRole);

	public UserRole update(Long id, UserRole userRole);

	public UserRole delete(Long id);

	public UserRole findById(Long id);

	public void deleteByUser(Long userId);

	public UserRole findByUserAndRole(Long userId, String role);

	public List<UserRole> findByUser(Long userId);

}
