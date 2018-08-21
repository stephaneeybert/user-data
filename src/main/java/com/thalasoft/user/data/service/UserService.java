package com.thalasoft.user.data.service;

import com.thalasoft.user.data.exception.EntityNotFoundException;
import com.thalasoft.user.data.jpa.domain.EmailAddress;
import com.thalasoft.user.data.jpa.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    public User add(User user);

    public User update(Long id, User user) throws EntityNotFoundException;

    public User partialUpdate(Long id, User user) throws EntityNotFoundException;

    public User delete(Long id) throws EntityNotFoundException;

    public User addRole(User user, String role) throws EntityNotFoundException;

    public User removeRole(User user, String role) throws EntityNotFoundException;

    public User findById(Long id) throws EntityNotFoundException;

    public Page<User> all(Pageable page);

    public User findByEmailAndPassword(EmailAddress email, String password) throws EntityNotFoundException;

    public User findByEmailAndReadablePassword(EmailAddress email, String readablePassword);

    public User findByEmail(String email) throws EntityNotFoundException;

    public Page<User> findByConfirmedEmailIsTrue(Pageable page);

    public Page<User> findByConfirmedEmailIsFalse(Pageable page);

    public Page<User> searchOnName(String searchTerm, Pageable page);

    public Page<User> search(String searchTerm, Pageable page);

    public void clearReadablePassword(User user) throws EntityNotFoundException;

}
