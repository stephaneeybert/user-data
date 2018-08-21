package com.thalasoft.user.data.jpa.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user_account")
@SequenceGenerator(name = "id_generator", sequenceName = "user_account_id_seq", allocationSize = 10)
public class User extends AbstractEntity {

    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false, unique = true)
    private EmailAddress email;
    @Column(nullable = false)
    private boolean confirmedEmail;
    @Column(length = 100)
    private String password;
    @Column(length = 50)
    private String passwordSalt;
    @Column(length = 50)
    private String readablePassword;
    private String workPhone;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "user")
    private Set<UserRole> userRoles = new HashSet<>();

    public User() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public void setEmail(EmailAddress email) {
        this.email = email;
    }

    public boolean isConfirmedEmail() {
        return confirmedEmail;
    }

    public void setConfirmedEmail(boolean confirmedEmail) {
        this.confirmedEmail = confirmedEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getReadablePassword() {
        return readablePassword;
    }

    public void setReadablePassword(String readablePassword) {
        this.readablePassword = readablePassword;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void addRole(String role) {
        boolean present = false;
        for (UserRole userRole : this.userRoles) {
            if (userRole.getRole().equals(role)) {
                present = true;
            }
        }
        if (!present) {
            UserRole userRole = new UserRole();
            userRole.setRole(role);
            userRole.setUser(this);
            this.userRoles.add(userRole);
        }
    }

    public void removeRole(String role) {
        // Deleting within a loop triigers a concurrent exception
        UserRole userRoleToRemove = null;
        for (UserRole userRole : this.userRoles) {
            if (userRole.getRole().equals(role)) {
                userRoleToRemove = userRole;
            }
        }
        if (null != userRoleToRemove) {
            userRoleToRemove.setUser(null);
            this.userRoles.remove(userRoleToRemove);
        }
    }

}
