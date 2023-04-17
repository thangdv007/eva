package com.evadeeva.eva.service;

import com.evadeeva.eva.models.requested.SignupRequested;
import com.evadeeva.eva.models.response.UserResponse;

public interface UserService {

    UserResponse signupUser(SignupRequested signupRequested);

}
