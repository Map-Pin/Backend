package com.server.mappin.domain;

import com.server.mappin.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Builder
    public Member(Long id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
