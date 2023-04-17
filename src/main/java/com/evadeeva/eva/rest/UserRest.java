package com.evadeeva.eva.rest;

import com.evadeeva.eva.mapper.UserMapper;
import com.evadeeva.eva.models.User;
import com.evadeeva.eva.models.requested.LoginRequested;
import com.evadeeva.eva.models.requested.SignupRequested;
import com.evadeeva.eva.models.response.JwtAuthResponse;
import com.evadeeva.eva.models.response.UserResponse;
import com.evadeeva.eva.repositories.UserRepository;
import com.evadeeva.eva.securities.JwtTokenProvider;
import com.evadeeva.eva.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRest {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    public UserRest(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, UserRepository userRepository, UserMapper userMapper, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> loginUser(@RequestBody LoginRequested loginRequested){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequested.getUsername(),loginRequested.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);
        User user = userRepository.findByUsername(loginRequested.getUsername());
        UserResponse userResponse = userMapper.mapModelToResponse(user);
        return ResponseEntity.ok(new JwtAuthResponse(token,userResponse));
    }
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signupUser(@RequestBody SignupRequested signupRequested){
        return ResponseEntity.ok(userService.signupUser(signupRequested)) ;
    }
}
