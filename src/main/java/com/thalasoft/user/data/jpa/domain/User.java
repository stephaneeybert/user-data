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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    @Column(length = 100)
    private String passwordSalt;
    @Column(length = 50)
    private String readablePassword;
    private String workPhone;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "user")
    private Set<UserRole> userRoles = new HashSet<>();

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
        // Deleting within a loop triggers a concurrent exception
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
