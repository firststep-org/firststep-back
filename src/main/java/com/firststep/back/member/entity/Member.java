package com.firststep.back.member.entity;

import com.firststep.back.global.entity.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    @NotBlank
    @Email
    private String memberEmail;
    @Column(nullable = false)
    @NotBlank
    private String password;
    @Column(unique = true, nullable = false)
    @NotNull
    private String memberNickname;
    @Column
    private String memberPictureUrl = null;
    @Column
    @NotBlank
    private String loginType; // TODO : Enum화
    @Column
    private int memberLevel = 0; // TODO : Enum화??
    @Column
    private int memberStatus = 0; // TODO : Enum화
    @Builder
    public Member(String memberEmail, String password, String memberNickname, String loginType) {
        this.memberEmail = memberEmail;
        this.password = password;
        this.memberNickname = memberNickname;
        this.loginType = loginType;
    }
}
