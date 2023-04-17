package com.evadeeva.eva.models.response;

import lombok.Data;

@Data
public class UserResponse {

    private String username;

    private String email;

    private String fullname;

    private String phone;

    private String image;
}
