package com.thalasoft.user.data.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

// import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@SequenceGenerator(name = "id_generator", sequenceName = "user_role_id_seq")
public class UserRole extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_account_id", nullable = false)
    // @JsonBackReference TODO
    private User user;
    @Column(nullable = false)
    private String role;

    public UserRole() {
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
