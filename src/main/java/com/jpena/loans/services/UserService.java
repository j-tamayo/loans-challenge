package com.jpena.loans.services;

import com.jpena.loans.models.User;

public interface UserService {
	
	public User getUser(Long id);
	
	public void deleteUser(Long userId);

}
