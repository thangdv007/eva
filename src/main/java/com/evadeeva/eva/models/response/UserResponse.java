package com.evadeeva.eva.models.response;

import com.evadeeva.eva.mapper.RoleMapper;
import com.evadeeva.eva.models.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {

    private String username;

    private String password;

    private String email;

    private String fullname;

    private String phone;

    private String image;

    private Set<RoleResponse> roleResponses;
}
