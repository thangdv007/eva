package com.evadeeva.eva.services;

import com.evadeeva.eva.models.requested.PasswordRequest;
import com.evadeeva.eva.models.requested.SignupRequest;
import com.evadeeva.eva.models.requested.UserRequest;
import com.evadeeva.eva.models.response.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserResponse> getAllUsers(int pageNumber, int pageSize, String sortBy);

    UserResponse signupUser(SignupRequest signupRequest);

    UserResponse updateUser(Long userId, UserRequest userRequest);

    UserResponse changePassword(Long userId, PasswordRequest passwordRequest);


}
