package com.rves.Dto;

import com.rves.pojo.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
@Getter
@Setter
public class UserDto {
    @NotNull
    private String username;

    @NotNull
    private String password;
    private String matchingPassword;

    @NotNull
    private List<Role> authorities;

    private boolean enabled;
}