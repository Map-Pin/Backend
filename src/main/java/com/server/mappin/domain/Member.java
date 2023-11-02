package com.server.mappin.domain;

import com.server.mappin.domain.enums.ProviderType;
import com.server.mappin.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "PROVIDER_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;


    @Builder
    public Member(String email, Role role, ProviderType providerType, LocalDateTime createdAt) {
        this.email = email;
        this.role = role;
        this.providerType = providerType;
        this.createdAt = createdAt;
    }
}
