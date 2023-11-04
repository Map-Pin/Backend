package com.server.mappin.dto;

import com.server.mappin.domain.enums.Role;
import lombok.Data;

@Data
public class MemberLoginDto {
    private String email;
    private Role role;
    private String name;
}
