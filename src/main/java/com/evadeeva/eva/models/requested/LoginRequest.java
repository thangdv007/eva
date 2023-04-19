package com.evadeeva.eva.models.requested;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;

    private String password;
}
