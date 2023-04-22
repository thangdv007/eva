package com.evadeeva.eva.mapper;

import com.evadeeva.eva.models.User;
import com.evadeeva.eva.models.requested.SignupRequest;
import com.evadeeva.eva.models.requested.UserRequest;
import com.evadeeva.eva.models.response.UserResponse;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    User mapSignupToModel(SignupRequest signupRequest);

    @Mapping(target = "roleResponses",source = "roles")
    UserResponse mapModelToResponse(User user);

    //User mapRequestToModel(UserRequest userRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModel(@MappingTarget User user, UserRequest userRequest);
}
