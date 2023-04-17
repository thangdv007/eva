package com.evadeeva.eva.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthResponse {
    private String accessToken;

    private String tokenType = "Bearer";

    private UserResponse userResponse;
    public JwtAuthResponse(String accessToken,UserResponse userResponse) {
        this.accessToken = accessToken;
        this.userResponse = userResponse;
    }
}
