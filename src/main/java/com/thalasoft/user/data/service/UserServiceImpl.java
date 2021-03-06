package com.thalasoft.user.data.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import com.thalasoft.user.data.exception.EntityAlreadyExistsException;
import com.thalasoft.user.data.exception.EntityNotFoundException;
import com.thalasoft.user.data.jpa.domain.EmailAddress;
import com.thalasoft.user.data.jpa.domain.User;
import com.thalasoft.user.data.jpa.repository.UserRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<User> streamAll(Pageable page) {
        List<User> mapstream = Collections.emptyList();
        try (Stream<User> stream = userRepository.streamAll(page)) {
            mapstream = stream.collect(Collectors.toList());
        }
        return mapstream;
    }

    @Override
    public Page<User> all(Pageable page) {
        Page<User> users = userRepository.all(page);
        if (users.getNumberOfElements() > 0) {
            return users;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Page<User> search(String searchTerm, Pageable page) {
        Page<User> users = userRepository.search(searchTerm, page);
        if (users.getNumberOfElements() > 0) {
            return users;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Page<User> searchOnName(String searchTerm, Pageable page) {
        return userRepository.searchOnName(searchTerm, page);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(new EmailAddress(email));
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public User findByEmailAndPassword(EmailAddress email, String password) {
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public User findByEmailAndReadablePassword(EmailAddress email, String readablePassword) {
        Optional<User> user = userRepository.findByEmailAndReadablePassword(email, readablePassword);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Page<User> findByConfirmedEmailIsTrue(Pageable page) {
        return userRepository.findByConfirmedEmailIsTrue(page);
    }

    @Override
    public Page<User> findByConfirmedEmailIsFalse(Pageable page) {
        return userRepository.findByConfirmedEmailIsFalse(page);
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Modifying
    @Transactional
    @Override
    public User add(User user) {
        try {
            findByEmail(user.getEmail().toString());
            throw new EntityAlreadyExistsException();
        } catch (EntityNotFoundException e) {
            // Save the returned id into the entity
            user = userRepository.saveAndFlush(user);
            return user;
        }
    }

    @Modifying
    @Transactional
    @Override
    public User update(Long existingUserId, User modifiedUser) {
        User existingUser = findById(existingUserId);
        if (existingUser == null) {
            throw new EntityNotFoundException();
        } else {
            existingUser.setEmail(new EmailAddress(modifiedUser.getEmail().getEmailAddress()));
            existingUser.setPassword(modifiedUser.getPassword());
            existingUser.setPasswordSalt(modifiedUser.getPasswordSalt());
            existingUser.setReadablePassword(modifiedUser.getReadablePassword());
            existingUser.setFirstname(modifiedUser.getFirstname());
            existingUser.setLastname(modifiedUser.getLastname());
            existingUser.setWorkPhone(modifiedUser.getWorkPhone());
            // Save the returned id into the entity
            existingUser = userRepository.saveAndFlush(existingUser);
            return existingUser;
        }
    }

    @Modifying
    @Transactional
    @Override
    public User partialUpdate(Long existingUserId, User modifiedUser) {
        User existingUser = findById(existingUserId);
        if (existingUser == null) {
            throw new EntityNotFoundException();
        } else {
            if (StringUtils.isNotEmpty(modifiedUser.getEmail().getEmailAddress())) {
                existingUser.setEmail(new EmailAddress(modifiedUser.getEmail().getEmailAddress()));
            }
            if (StringUtils.isNotEmpty(modifiedUser.getPassword())) {
                existingUser.setPassword(modifiedUser.getPassword());
            }
            if (StringUtils.isNotEmpty(modifiedUser.getPasswordSalt())) {
                existingUser.setPasswordSalt(modifiedUser.getPasswordSalt());
            }
            if (StringUtils.isNotEmpty(modifiedUser.getReadablePassword())) {
                existingUser.setReadablePassword(modifiedUser.getReadablePassword());
            }
            if (StringUtils.isNotEmpty(modifiedUser.getFirstname())) {
                existingUser.setFirstname(modifiedUser.getFirstname());
            }
            if (StringUtils.isNotEmpty(modifiedUser.getLastname())) {
                existingUser.setLastname(modifiedUser.getLastname());
            }
            if (StringUtils.isNotEmpty(modifiedUser.getWorkPhone())) {
                existingUser.setWorkPhone(modifiedUser.getWorkPhone());
            }
            // Save the returned id into the entity
            existingUser = userRepository.saveAndFlush(existingUser);
            return existingUser;
        }
    }

    @Modifying
    @Transactional
    @Override
    public User delete(Long id) {
        User user = findById(id);
        if (user == null) {
            throw new EntityNotFoundException();
        } else {
            userRepository.delete(user);
            return user;
        }
    }

    @Modifying
    @Transactional
    @Override
    public void clearReadablePassword(User user) {
        User foundUser = findById(user.getId());
        if (foundUser == null) {
            throw new EntityNotFoundException();
        } else {
            foundUser.setReadablePassword(null);
        }
    }

    @Modifying
    @Transactional
    @Override
    public User addRole(User user, String role) {
        User foundUser = findById(user.getId());
        if (foundUser != null) {
            foundUser.addRole(role);
            return foundUser;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Modifying
    @Transactional
    @Override
    public User removeRole(User user, String role) {
        User foundUser = findById(user.getId());
        if (foundUser != null) {
            foundUser.removeRole(role);
            return foundUser;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void addSortToPageable(Pageable page, Sort sort) {
        page.getSort().and(sort);
    }

}
