package com.jpena.loans.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpena.loans.models.User;
import com.jpena.loans.repositories.LoanRepository;
import com.jpena.loans.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LoanRepository loanRepository;
	
	@Override
	public User getUser(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new EntityNotFoundException();
		}
	}
	
	@Override
	public void deleteUser(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		
		if (user.isPresent()) {
			loanRepository.deleteAllByUser(user.get());
			userRepository.delete(user.get());
		} else {
			throw new EntityNotFoundException();
		}
	}
	
}
