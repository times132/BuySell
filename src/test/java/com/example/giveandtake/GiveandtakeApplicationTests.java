package com.example.giveandtake;

import com.example.giveandtake.model.User;
import com.example.giveandtake.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
class GiveandtakeApplicationTests {

	@Autowired
	UserRepository userRepository;


	@Test
	public void userSaveAndLoad(){
		userRepository.save(User.builder()
				.nickname("times132")
				.username("Park")
				.email("times132@naver.com")
				.password("1234562")
				.phone("010-1111-2222")
				.build());

		List<User> userList = userRepository.findAll();

		User users = userList.get(0);
		assertThat(users.getNickname(), is("times132"));
		assertThat(users.getEmail(), is("times132@naver.com"));
	}
}
