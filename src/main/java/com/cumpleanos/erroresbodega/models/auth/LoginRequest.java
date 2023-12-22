package com.cumpleanos.erroresbodega.models.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String Username;
    private String password;
}
