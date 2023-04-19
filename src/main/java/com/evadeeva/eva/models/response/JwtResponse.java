package com.evadeeva.eva.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String accessToken;

    private String tokenType = "Bearer";
    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
