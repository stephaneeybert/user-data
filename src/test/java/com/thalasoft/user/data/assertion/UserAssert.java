package com.thalasoft.user.data.assertion;

import static org.assertj.core.api.Assertions.assertThat;

import com.thalasoft.user.data.jpa.domain.AbstractEntity;
import com.thalasoft.user.data.jpa.domain.EmailAddress;
import com.thalasoft.user.data.jpa.domain.User;
import com.thalasoft.user.data.jpa.domain.UserRole;

import org.assertj.core.api.AbstractAssert;

public class UserAssert extends AbstractAssert<UserAssert, User> {

	private UserAssert(User actual) {
		super(actual, UserAssert.class);
	}

	public static UserAssert assertThatUser(User actual) {
		return new UserAssert(actual);
	}

	public UserAssert hasId(Long id) {
		isNotNull();
		assertThat(actual.getId())
				.overridingErrorMessage("Expected the id to be <%s> but was <%s>.", id, actual.getId()).isEqualTo(id);
		return this;
	}

	public UserAssert hasAnIdNotNull() {
		isNotNull();
		assertThat(actual.getId()).overridingErrorMessage("Expected the id to be not null but was null.").isNotNull();
		return this;
	}

	public UserAssert hasEmail(EmailAddress email) {
		isNotNull();
		assertThat(actual.getEmail().toString())
				.overridingErrorMessage("Expected the email to be <%s> but was <%s>.", email, actual.getEmail())
				.isEqualTo(email.toString());
		return this;
	}

	public UserAssert hasPassword(String password) {
		isNotNull();
		assertThat(actual.getPassword()).overridingErrorMessage("Expected the password to be <%s> but was <%s>.",
				password, actual.getPassword()).isEqualTo(password);
		return this;
	}

	public UserAssert hasFirstname(String firstname) {
		assertThat(actual.getFirstname()).overridingErrorMessage("Expected the firstname to be <%s> but was <%s>.",
				firstname, actual.getFirstname()).isEqualTo(firstname);
		return this;
	}

	public UserAssert hasLastname(String lastname) {
		assertThat(actual.getLastname()).overridingErrorMessage("Expected the lastname to be <%s> but was <%s>.",
				lastname, actual.getLastname()).isEqualTo(lastname);
		return this;
	}

	public UserAssert hasRole(String role) {
		isNotNull();
		boolean present = false;
		for (UserRole userRole : actual.getUserRoles()) {
			if (userRole.getRole().equals(role)) {
				present = true;
			}
		}
		assertThat(present).overridingErrorMessage("Expected the user to have the role <%s> but he did not.", role)
				.isTrue();
		return this;
	}

	public UserAssert hasNotRole(String role) {
		isNotNull();
		boolean present = false;
		for (UserRole userRole : actual.getUserRoles()) {
			if (userRole.getRole().equals(role)) {
				present = true;
			}
		}
		assertThat(present).overridingErrorMessage("Expected the user not to have the role <%s> but he did.", role)
				.isFalse();
		return this;
	}

	public UserAssert isSameAs(AbstractEntity entity) {
		isNotNull();
		assertThat(actual.hashCode()).overridingErrorMessage("Expected the hash code to be <%s> but was <%s>.",
				entity.hashCode(), actual.hashCode()).isEqualTo(entity.hashCode());
		return this;
	}

	public UserAssert exists() {
		assertThat(actual).overridingErrorMessage("Expected the user to exist but it didn't.").isNotNull();
		return this;
	}

	public UserAssert doesNotExist() {
		assertThat(actual).overridingErrorMessage("Expected the user not to exist but it did.").isNull();
		return this;
	}

}
