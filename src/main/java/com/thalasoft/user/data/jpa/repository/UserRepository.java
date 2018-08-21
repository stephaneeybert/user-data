package com.thalasoft.user.data.jpa.repository;

import com.thalasoft.user.data.jpa.domain.EmailAddress;
import com.thalasoft.user.data.jpa.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    public User findByEmail(EmailAddress email);

    public User findByEmailAndPassword(EmailAddress email, String password);

    public User findByEmailAndReadablePassword(EmailAddress email, String readablePassword);

    @Query("SELECT u FROM User u ORDER BY u.firstname")
    public Page<User> all(Pageable page);

    public Page<User> findByConfirmedEmailIsTrue(Pageable page);

    public Page<User> findByConfirmedEmailIsFalse(Pageable page);

    @Query("SELECT u FROM User u WHERE LOWER(u.firstname) LIKE LOWER(CONCAT(:searchTerm, '%')) OR LOWER(u.lastname) LIKE LOWER(CONCAT(:searchTerm, '%')) ORDER BY u.firstname")
    public Page<User> searchOnName(@Param("searchTerm") String searchTerm, Pageable page);

    @Query("SELECT u FROM User u WHERE LOWER(u.firstname) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(u.lastname) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ORDER BY u.firstname")
    public Page<User> search(@Param("searchTerm") String searchTerm, Pageable page);

    @Modifying
    @Query("UPDATE User SET password = :password, passwordSalt = :passwordSalt, readablePassword = :readablePassword WHERE id = :id")
    public void updatePassword(@Param("password") String password, @Param("passwordSalt") String passwordSalt,
            @Param("readablePassword") String readablePassword, @Param("id") Long id);

}
