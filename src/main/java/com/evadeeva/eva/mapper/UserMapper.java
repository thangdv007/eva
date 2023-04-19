package com.evadeeva.eva.mapper;

import com.evadeeva.eva.models.User;
import com.evadeeva.eva.models.requested.SignupRequest;
import com.evadeeva.eva.models.requested.UserRequest;
import com.evadeeva.eva.models.response.UserResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface UserMapper {
    User mapSignupToModel(SignupRequest signupRequest);

    UserResponse mapModelToResponse(User user);

    //User mapRequestToModel(UserRequest userRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModel(@MappingTarget User user, UserRequest userRequest);
}
