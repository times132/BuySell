package com.example.giveandtake;

import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class GiveandtakeApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Test
	public void createUser(){
		User user = new User();
		user.setNickname("sunny");
		user.setUsername("YOO");
		user.setEmail("y4380@naver.com");
		user.setPassword("1234");
		user.setPhone("010-2222-2222");
		user.setCreated_date(LocalDateTime.now());

		User newUser = userRepository.save(user);
		System.out.println("newUser" + newUser);
		assertThat(user.getNickname(), is("sunny"));
		assertThat(user.getEmail(), is("y4380@naver.com"));}

	@Test
	public void deleteUser(){
		Optional<User> user = userRepository.findById(4L);
		Assert.assertTrue(user.isPresent());    // false
		user.ifPresent(selectUser->{
			userRepository.delete(selectUser);
		});

		Optional<User> deleteUser = userRepository.findById(4L);

		Assert.assertFalse(deleteUser.isPresent()); // false
	}

	@Test
	public void readUser(){
		Optional<User> user = userRepository.findById(1L);

		user.ifPresent(selectUser ->{
			System.out.println("user: "+selectUser);
		});
	}
//id는 모델에서 long으로 선언 되어 있으므로 L을 붙여 id가 1번인 데이터를 찾는다.
//데이터를 찾아 user객체에 담아주고 출력한다.


	@Test
	public void updateUser(){

		Optional<User> user = userRepository.findById(2L);

		user.ifPresent(selectUser ->{
			selectUser.setNickname("sunny말고승아");
			selectUser.setUpdated_date(LocalDateTime.now());
			selectUser.setPassword("1234");
			selectUser.setPhone("010-2222-2222");
			userRepository.save(selectUser);
		});
	}



}
