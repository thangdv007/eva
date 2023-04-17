package com.evadeeva.eva.mapper;

import com.evadeeva.eva.models.User;
import com.evadeeva.eva.models.requested.SignupRequested;
import com.evadeeva.eva.models.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User mapSignupToModel(SignupRequested signupRequested);

    UserResponse mapModelToResponse(User user);
}
