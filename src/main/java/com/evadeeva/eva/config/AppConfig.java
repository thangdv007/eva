package com.evadeeva.eva.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.evadeeva.eva.mapper.UserMapper;
import com.evadeeva.eva.models.User;
import com.evadeeva.eva.models.requested.SignupRequest;
import com.evadeeva.eva.models.requested.UserRequest;
import com.evadeeva.eva.models.response.UserResponse;

@Configuration
public class AppConfig {
	@Bean
	public UserMapper userMapper() {
		return new UserMapper() {
			
			@Override
			public void updateModel(User user, UserRequest userRequest) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public User mapSignupToModel(SignupRequest signupRequest) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public UserResponse mapModelToResponse(User user) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
