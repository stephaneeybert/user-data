package com.thalasoft.user.data.jpa.repository;

import java.util.List;

import com.thalasoft.user.data.jpa.domain.User;
import com.thalasoft.user.data.jpa.domain.UserRole;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>, UserRoleRepositoryCustom {

	public UserRole findByUserAndRole(User user, String role);

	@Query("SELECT ur FROM UserRole ur ORDER BY ur.role")
	public Page<UserRole> findAllRoles(Pageable pageable);

	@Query("SELECT ur FROM UserRole ur WHERE ur.user = :user ORDER BY ur.role")
	public List<UserRole> findByUser(@Param("user") User user);

	@Modifying
	@Query("DELETE FROM UserRole ur WHERE ur.user = :user")
	public void deleteByUser(@Param("user") User user);

}
