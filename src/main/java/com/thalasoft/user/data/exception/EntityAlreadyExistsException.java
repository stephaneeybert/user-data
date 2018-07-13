package com.thalasoft.user.data.exception;

@SuppressWarnings("serial")
public class EntityAlreadyExistsException extends EnrichableException {

	public EntityAlreadyExistsException() {
		super("The entity already exists.");
	}

	public EntityAlreadyExistsException(String message) {
		super(message);
	}

}