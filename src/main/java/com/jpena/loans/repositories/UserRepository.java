package com.jpena.loans.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jpena.loans.models.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
