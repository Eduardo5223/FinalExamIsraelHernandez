package com.mayab.quality.logginunittest.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mayab.quality.loginunittest.dao.IDAOUser;
import com.mayab.quality.loginunittest.model.User;
import com.mayab.quality.loginunittest.service.UserService;

class UserServiceTest {
	
	private UserService service;
	private IDAOUser dao;
	private User user;
	
	@BeforeEach
	public void setUp() throws Exception{
		dao = mock(IDAOUser.class);
		service = new UserService(dao);
		user = mock(User.class);
	}
	@Test
	public void whenPasswordShort_test() {
		String shortPass = "123";
		String name = "user1";
		String email = "user@gmail.com";
		User user = null;
		
		when(dao.findUserByEmail(anyString())).thenReturn(user);
		when(dao.save(any(User.class))).thenReturn(1);
		
		User result = service.createUser(name, email, shortPass);
		
		assertThat(result,is(nullValue()));
	}

}
