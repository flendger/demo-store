package ru.flendger.demo.store.demo.store.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
