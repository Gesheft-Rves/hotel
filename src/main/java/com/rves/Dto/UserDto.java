package com.rves.Dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDto {
    @NotNull
    private String username;

    @NotNull
    private String password;
    private String matchingPassword;
}