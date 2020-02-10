package com.example.giveandtake;

import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class GiveandtakeApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Test
	public void createUser(){
		userRepository.save(User.builder()
				.username("Park")
				.nickname("times132")
				.password("123456")
				.phone("010-1234-5678")
				.email("times132@naver.com")
				.build());

		List<User> usersList = userRepository.findAll();

		User users = usersList.get(0);
		assertThat(users.getNickname(), is("times132"));
		assertThat(users.getEmail(), is("times132@naver.com"));
	}

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


//	@Test
//	public void updateUser(){
//
//		Optional<User> user = userRepository.findById(2L);
//
//		user.ifPresent(selectUser ->{
//			selectUser.setNickname("sunny말고승아");
//			selectUser.setUpdated_date(LocalDateTime.now());
//			selectUser.setPassword("1234");
//			selectUser.setPhone("010-2222-2222");
//			userRepository.save(selectUser);
//		});
//	}

	@Test
	public void AuditTest(){
		LocalDateTime now = LocalDateTime.now();
		userRepository.save(User.builder()
				.username("Park")
				.nickname("times132")
				.password("123456")
				.phone("010-1234-5678")
				.email("times132@naver.com")
				.build());
	}
}
