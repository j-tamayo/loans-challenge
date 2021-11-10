package com.jpena.loans.repositories;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.jpena.loans.models.Loan;
import com.jpena.loans.models.User;

public interface LoanRepository extends PagingAndSortingRepository<Loan, Long> {

	Page<Loan> findByUser_id(Long userId, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Loan l where l.user = :user")
	void deleteAllByUser(@Param("user") User user);
	
}
