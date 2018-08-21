package com.thalasoft.user.data.service;

import com.thalasoft.user.data.jpa.domain.EmailAddress;
import com.thalasoft.user.data.jpa.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    public User add(User user);

    public User update(Long id, User user);

    public User partialUpdate(Long id, User user);

    public User delete(Long id);

    public User addRole(User user, String role);

    public User removeRole(User user, String role);

    public User findById(Long id);

    public Page<User> all(Pageable page);

    public User findByEmailAndPassword(EmailAddress email, String password);

    public User findByEmailAndReadablePassword(EmailAddress email, String readablePassword);

    public User findByEmail(String email);

    public Page<User> findByConfirmedEmailIsTrue(Pageable page);

    public Page<User> findByConfirmedEmailIsFalse(Pageable page);

    public Page<User> searchOnName(String searchTerm, Pageable page);

    public Page<User> search(String searchTerm, Pageable page);

    public void clearReadablePassword(User user);

}
