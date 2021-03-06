package com.jpena.loans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.jpena.loans.models.Loan;
import com.jpena.loans.models.User;
import com.jpena.loans.repositories.LoanRepository;
import com.jpena.loans.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = LoansApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "/integrationtest.properties")
public class UsersRestControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoanRepository loanRepository;
	
	@After
	public void resetDb() {
		loanRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	@Order(1)
	public void createUser() throws Exception {
		
		/**
        *
        * Create user with id 1
        *
        * The request body is:
        * {
        *     "id": 1,
        *     "email": "jpena@test.com",
        *     "firstName": "Junior",
        *     "lastName": "Tamayo",
        * }
        */
       String json = "{\"id\": 1, \"email\": \"jpena@test.com\", \"firstName\": \"Junior\", \"lastName\": \"Tamayo\"}";
       
       mockMvc.perform(
    		   post("/users")
    		   .contentType(MediaType.APPLICATION_JSON)
               .content(json)
    	)
       .andExpect(status().is2xxSuccessful());
       
       
       /**
       *
       * Create user with id 2
       *
       * The request body is:
       * {
       *     "id": 2,
       *     "email": "arodriguez@test.com",
       *     "firstName": "Andres",
       *     "lastName": "Rodriguez",
       * }
       */
      json = "{\"id\": 2, \"email\": \"arodriguez@test.com\", \"firstName\": \"Andres\", \"lastName\": \"Rodriguez\"}";
      
      mockMvc.perform(
   		   post("/users")
   		   .contentType(MediaType.APPLICATION_JSON)
              .content(json)
   	)
      .andExpect(status().is2xxSuccessful());
		
	}
	
	@Test
	@Order(2)
	public void findUserById() throws Exception {
		 /**
        *
        * Find user by id 1
        *
        * The request response is:
        * {
        *     "id": 1,
        *     "email": "jpena@test.com",
        *     "firstName": "Junior",
        *     "lastName": "Tamayo",
        * }
        */
		createUserTest(1L, "jpena@test.com", "Junior", "Tamayo");
		Long userId = userRepository.findAll().iterator().next().getId();
		
		mockMvc.perform(
				get("/users/" + userId)
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(userId.intValue())))
		.andExpect(jsonPath("$.email", is("jpena@test.com")))
		.andExpect(jsonPath("$.firstName", is("Junior")))
		.andExpect(jsonPath("$.lastName", is("Tamayo")));
	}
	
	@Test
	@Order(3)
	public void findNonExistentgUser() throws Exception {
		mockMvc.perform(
				get("/users/1")
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	@Order(4)
	public void deleteUser() throws Exception {
		createUserTest(1L, "jpena@test.com", "mimi", "Tamayo");
		Long userId = userRepository.findAll().iterator().next().getId();
		
		mockMvc.perform(
				delete("/users/" + userId)
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isOk());
		
		assertThat(userRepository.count()).isZero();
	}
	
	@Test
	@Order(5)
	public void deleteUserNonExistent() throws Exception {
		createUserTest(1L, "jpena@test.com", "mimi", "Tamayo");
		Long userId = userRepository.findAll().iterator().next().getId() + 1;
		
		mockMvc.perform(
				delete("/users/" + userId)
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isNotFound());
		
	}
	
	@Test
	@Order(6)
	public void createLoan() throws Exception {
		/**
        *
        * Create loan
        *
        * The request body is:
        * {
        *     "total": "1000",
        * }
        */
       String json = "{\"total\": 1000}";
       
       createUserTest(1L, "jpena@test.com", "mimi", "Tamayo");
       Long userId = userRepository.findAll().iterator().next().getId();
       
       mockMvc.perform(
    		   post("/users/" + userId + "/loans")
    		   .contentType(MediaType.APPLICATION_JSON)
               .content(json)
    	)
       .andExpect(status().is2xxSuccessful());
	}
	
	@Test
	@Order(7)
	public void createLoanOfNonExistentUser() throws Exception {
		/**
        *
        * Create loan
        *
        * The request body is:
        * {
        *     "total": "1000",
        * }
        */
       String json = "{\"total\": 1000}";
       
       mockMvc.perform(
    		   post("/users/1/loans")
    		   .contentType(MediaType.APPLICATION_JSON)
               .content(json)
    	)
       .andExpect(status().isNotFound());
	}
	
	@Test
	@Order(8)
	public void getLoans() throws Exception {
		createUserTest(1L, "jpena@test.com", "mimi", "Tamayo");
		User user = userRepository.findAll().iterator().next();
		Long userId = userRepository.findAll().iterator().next().getId();
		BigDecimal loanAmount = new BigDecimal(1000);
		createLoanTest(user, loanAmount);
		
		mockMvc.perform(
				get("/loans")
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].total", is(loanAmount.doubleValue())))
		.andExpect(jsonPath("$[0].userId", is(userId.intValue())));
	}
	
	@Test
	@Order(9)
	public void getLoansInPage2() throws Exception {
		createUserTest(1L, "jpena@test.com", "mimi", "Tamayo");
		User user = userRepository.findAll().iterator().next();
		Long userId = userRepository.findAll().iterator().next().getId();
		BigDecimal loanAmount = new BigDecimal(1000);
		BigDecimal loanAmount2 = new BigDecimal(1000);
		createLoanTest(user, loanAmount);
		createLoanTest(user, loanAmount2);
		
		mockMvc.perform(
				get("/loans?page=2&size=1")
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].total", is(loanAmount2.doubleValue())))
		.andExpect(jsonPath("$[0].userId", is(userId.intValue())));
	}
	
	@Test
	@Order(10)
	public void getLoansForUser() throws Exception {
		createUserTest(1L, "jpena@test.com", "mimi", "Tamayo");
		User user = userRepository.findAll().iterator().next();
		Long userId = userRepository.findAll().iterator().next().getId();
		BigDecimal loanAmount = new BigDecimal(1000);
		BigDecimal loanAmount2 = new BigDecimal(1000);
		createLoanTest(user, loanAmount);
		createLoanTest(user, loanAmount2);
		
		mockMvc.perform(
				get("/loans?user_id=" + userId)
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].total", is(loanAmount.doubleValue())))
		.andExpect(jsonPath("$[0].userId", is(userId.intValue())))
		.andExpect(jsonPath("$[1].total", is(loanAmount2.doubleValue())))
		.andExpect(jsonPath("$[1].userId", is(userId.intValue())));
	}
	
	@Test
	@Order(11)
	public void getLoansForNonExistentUser() throws Exception {
		createUserTest(1L, "jpena@test.com", "mimi", "Tamayo");
		User user = userRepository.findAll().iterator().next();
		Long userId = userRepository.findAll().iterator().next().getId() + 1;
		BigDecimal loanAmount = new BigDecimal(1000);
		BigDecimal loanAmount2 = new BigDecimal(1000);
		createLoanTest(user, loanAmount);
		createLoanTest(user, loanAmount2);
		
		mockMvc.perform(
				get("/loans?user_id=" + userId)
				.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
	}
	
	private void createUserTest(Long id, String email, String firstName, String lastName) {
		User user = new User(id, email, firstName, lastName);
		
		userRepository.save(user);
	}
	
	private void createLoanTest(User user, BigDecimal amount) {
		Loan loan = new Loan(amount);
		loan.setUser(user);
		
		loanRepository.save(loan);
	}
}
