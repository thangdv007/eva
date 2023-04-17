package com.evadeeva.eva.service.impl;

import com.evadeeva.eva.mapper.UserMapper;
import com.evadeeva.eva.models.Role;
import com.evadeeva.eva.models.User;
import com.evadeeva.eva.models.requested.SignupRequested;
import com.evadeeva.eva.models.response.UserResponse;
import com.evadeeva.eva.repositories.RoleRepository;
import com.evadeeva.eva.repositories.UserRepository;
import com.evadeeva.eva.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Transactional
    @Override
    public UserResponse signupUser(SignupRequested signupRequested) {

        // Chuyển thành model
        User user = userMapper.mapSignupToModel(signupRequested);

        //Mã hoá mật khẩu
        user.setPassword(passwordEncoder().encode(signupRequested.getPassword()));

        // Lưu các giá trị mặc định: Role = USER, status = 1, createdDate, modifiedDate
        user.setStatus(1);

        Date currentDate = new Date();
        user.setCreatedDate(currentDate);
        user.setModifiedDate(currentDate);

        // Tạo default role
        // Set default role_id = 2 (ROLE_USER)
        long defaultRoleId = 2;
        Role role = roleRepository.findById(defaultRoleId);
        user.getRoles().add(role);


        if(user != null){
            UserResponse userResponse =userMapper.mapModelToResponse(userRepository.save(user));
            return userResponse;
        }
        return null;
    }
}
