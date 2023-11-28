package com.server.mappin.dto.Login;

import com.server.mappin.domain.enums.Role;
import lombok.Data;

@Data
public class MemberLoginDto {
    private String email;
    private String name;
}
