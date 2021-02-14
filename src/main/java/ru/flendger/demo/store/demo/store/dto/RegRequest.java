package ru.flendger.demo.store.demo.store.dto;

import lombok.Data;

@Data
public class RegRequest {
    private String username;
    private String password;
    private String email;
}
