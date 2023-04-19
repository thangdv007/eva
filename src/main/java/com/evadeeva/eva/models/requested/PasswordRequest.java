package com.evadeeva.eva.models.requested;

import lombok.Data;

@Data
public class PasswordRequest {
    private String password;

    private String cfPassword;
}
