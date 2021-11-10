package com.jpena.loans;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.jpena.loans.models.Loan;
import com.jpena.loans.models.User;
import com.jpena.loans.repositories.LoanRepository;
import com.jpena.loans.repositories.UserRepository;
import com.jpena.loans.services.UserService;
import com.jpena.loans.services.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	
	private static final Long USER_1_ID = 1L;
	private static final String USER_1_EMAIL = "jpena@test.com";
	private static final String USER_1_FIRSTNAME = "Junior";
	private static final String USER_1_LASTNAME = "Peña";
	
	@TestConfiguration
	static class UserServiceTestContextConfiguration {
		@Bean
		public UserService userService() {
			return new UserServiceImpl();
		}
	}
	
	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private LoanRepository loanRepository;
	
	@Before
	public void setUp() {
		User junior = createUserWithLoans(USER_1_ID, USER_1_EMAIL, USER_1_FIRSTNAME, USER_1_LASTNAME);
		
		when(userRepository.findById(USER_1_ID)).thenReturn(Optional.of(junior));
		
		doNothing().when(loanRepository).deleteAllByUser(Mockito.any());
	}
	
	@Test
	public void getUser() {
		User user = userService.getUser(USER_1_ID);
		
		assertNotNull(user);
		assertEquals(USER_1_ID, user.getId());
		assertEquals(USER_1_EMAIL, user.getEmail());
		assertEquals(USER_1_FIRSTNAME, user.getFirstName());
		assertEquals(USER_1_LASTNAME, user.getLastName());
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void getNonExistingUser() {
		userService.getUser(2L);
	}
	
	@Test
	public void deleteUser() {
		userService.deleteUser(USER_1_ID);
		verify(userRepository, times(1)).delete(Mockito.any());
		verify(loanRepository, times(1)).deleteAllByUser(Mockito.any());
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void deleteNonExistingUser() {
		userService.deleteUser(2L);
	}
	
	private User createUserWithLoans(Long id, String email, String firstName, String lastName) {
		User user = new User(id, email, firstName, lastName);
		
		List<Loan> loans = new ArrayList<>();
		loans.add(new Loan(new BigDecimal(1000)));
		loans.add(new Loan(new BigDecimal(2000)));
		
		return user;
	}

}
