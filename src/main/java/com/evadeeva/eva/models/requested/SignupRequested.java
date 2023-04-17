package com.evadeeva.eva.models.requested;

import lombok.Data;

@Data
public class SignupRequested {

    private String username;

    private String password;

    private String fullname;

    private String email;

    private String phone;
}
