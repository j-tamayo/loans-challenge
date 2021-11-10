package com.jpena.loans.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jpena.loans.models.Loan;

public interface LoanRepository extends PagingAndSortingRepository<Loan, Long> {

	Page<Loan> findByUser_id(Long userId, Pageable pageable);
	
}
