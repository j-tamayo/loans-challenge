package com.jpena.loans.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jpena.loans.models.Loan;
import com.jpena.loans.repositories.LoanRepository;
import com.jpena.loans.repositories.UserRepository;

@RestController
public class Loans {
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/users/{userId}/loans")
	public Loan createLoan(@PathVariable (value = "userId") Long userId,  @Valid @RequestBody Loan loan) {
		return userRepository.findById(userId).map(user -> {
			loan.setUser(user);
			return loanRepository.save(loan);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	}
	
	@GetMapping("/loans")
	public List<Loan> getLoans(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "-1") Long userId) {
		Pageable paging = PageRequest.of(page - 1, size, Sort.by("id"));
		Page<Loan> pagedLoans = userId.equals(-1L) ? loanRepository.findAll(paging) : loanRepository.findByUser_id(userId, paging);

		if (pagedLoans.hasContent()) {
			return pagedLoans.getContent();
		} else {
			return new ArrayList<>();
		}
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
