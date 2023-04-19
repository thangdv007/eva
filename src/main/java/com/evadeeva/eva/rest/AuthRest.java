package com.evadeeva.eva.rest;

import com.evadeeva.eva.mapper.UserMapper;
import com.evadeeva.eva.models.requested.LoginRequest;
import com.evadeeva.eva.models.requested.PasswordRequest;
import com.evadeeva.eva.models.requested.SignupRequest;
import com.evadeeva.eva.models.response.JwtResponse;
import com.evadeeva.eva.models.response.UserResponse;
import com.evadeeva.eva.repositories.UserRepository;
import com.evadeeva.eva.securities.JwtConfig;
import com.evadeeva.eva.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthRest {
    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JwtConfig jwtConfig;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private final UserService userService;

    public AuthRest(AuthenticationManager authenticationManager, JwtConfig jwtConfig, UserRepository userRepository, UserMapper userMapper, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //get token form tokenProvider
        String token = jwtConfig.generateToken(authentication);

        return ResponseEntity.ok(new JwtResponse(token));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        UserResponse userResponse = userService.signupUser(signupRequest);
        if(userResponse == null){
           return new ResponseEntity<>("Sign up fail!", HttpStatus.BAD_REQUEST);
        }else {
            return ResponseEntity.ok(userResponse);
        }
    }

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<?> changePassword(@PathVariable(name = "id") Long userId,
                                            @RequestBody PasswordRequest passwordRequest){
        UserResponse userResponse = userService.changePassword(userId,passwordRequest);
        if(userResponse != null){
            return ResponseEntity.ok(userResponse);
        }
        return new ResponseEntity<>("Thay đổi mật khẩu không thành công", HttpStatus.BAD_REQUEST);
    }
}
