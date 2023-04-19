package com.evadeeva.eva.services.impl;

import com.evadeeva.eva.exceptions.ResourceNotFoundException;
import com.evadeeva.eva.mapper.UserMapper;
import com.evadeeva.eva.models.Role;
import com.evadeeva.eva.models.User;
import com.evadeeva.eva.models.requested.PasswordRequest;
import com.evadeeva.eva.models.requested.SignupRequest;
import com.evadeeva.eva.models.requested.UserRequest;
import com.evadeeva.eva.models.response.UserResponse;
import com.evadeeva.eva.repositories.RoleRepository;
import com.evadeeva.eva.repositories.UserRepository;
import com.evadeeva.eva.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

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

    @Override
    public Page<UserResponse> getAllUsers(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy).ascending());
        return userRepository.findAll(pageable).map(user -> userMapper.mapModelToResponse(user));
    }

    @Transactional
    @Override
    public UserResponse signupUser(SignupRequest signupRequest) {

        // Kiểm tra trong csdl đã tồn tại người sử dụng có username tương tự user của yêu cầu đăng kí chưa
        User checkUser = userRepository.findByUsername(signupRequest.getUsername());

        if(checkUser != null){
            return null;
        }else {
            // Chuyển thành model
            User user = userMapper.mapSignupToModel(signupRequest);

            //Mã hoá mật khẩu
            user.setPassword(passwordEncoder().encode(signupRequest.getPassword()));

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

            return userMapper.mapModelToResponse(userRepository.save(user));
        }

    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest userRequest) {
        //Kiểm tra cfPassword có trùng với Password nhập vào không
        if (userId != null && userRequest != null){
            //thực hiện lấy thông tin cũ
            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

            //update thông tin cho user
            user.setModifiedDate(new Date());
            userMapper.updateModel(user,userRequest);

            //trả về thông tin người sử dụng vừa cập nhật
            return userMapper.mapModelToResponse(userRepository.save(user));
        }
        return null;
    }

    @Override
    public UserResponse changePassword(Long userId, PasswordRequest passwordRequest) {
        //Kiểm tra thông tin của passrequest có hợp lệ hay không
        if (passwordRequest.getCfPassword() != null && passwordRequest.getPassword() != null){
            //kiểm tra 2 pass có trùng nhau không
            if(Objects.equals(passwordRequest.getPassword(), passwordRequest.getCfPassword())){
                //lấy thông tin user
                User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id", userId));

                //cập nhật pass mới
                user.setPassword(passwordEncoder().encode(passwordRequest.getPassword()));

                //lưu và trả về kết quả
                return userMapper.mapModelToResponse(userRepository.save(user));
            }else {
                return null;
            }
        }else {
            return null;
        }

    }
}
