package com.firststep.back.member.entity;

import com.firststep.back.global.entity.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    @NonNull
    private String userEmail;
    @Column(nullable = false)
    @NonNull
    private String password;
    @Column(unique = true, nullable = false)
    @NonNull
    private String userNickname;
    @Column
    private String userPictureUrl = null;
    @Column
    @NonNull
    private String loginType;
    @Column
    private int userLevel = 0;
    @Column
    private int userStatus = 0;
    @Builder
    public Member(@NonNull String userEmail, @NonNull String password, @NonNull String userNickname, @NonNull String loginType) {
        this.userEmail = userEmail;
        this.password = password;
        this.userNickname = userNickname;
        this.loginType = loginType;
    }
}
