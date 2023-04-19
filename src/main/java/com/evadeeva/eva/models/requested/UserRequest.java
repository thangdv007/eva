package com.evadeeva.eva.models.requested;


import lombok.Data;

@Data
public class UserRequest {

    private String email;

    private String fullname;

    private String phone;

    private String image;
}
