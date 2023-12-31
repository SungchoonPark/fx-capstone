package com.capstone.fxteam.member.model;

import com.capstone.fxteam.constant.entity.BaseEntity;
import com.capstone.fxteam.constant.enums.DeleteEnum;
import com.capstone.fxteam.member.model.enums.OAuthProvider;
import com.capstone.fxteam.member.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column(nullable = false, length = 12, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 15)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private OAuthProvider provider;

    @Column(nullable = true)
    private long providerId;

    @Enumerated(EnumType.STRING)
    private DeleteEnum deleteEnum;

    public void changePassword(String password) {
        this.password = password;
    }

    public boolean isSameMember(String loginId) {
        return Objects.equals(this.loginId, loginId);
    }
}
