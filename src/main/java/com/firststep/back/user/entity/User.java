package com.firststep.back.user.entity;

import com.firststep.back.global.entity.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.RequiredArgsConstructor;


@Entity
@Builder
@RequiredArgsConstructor
public class User extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(unique = true, nullable = false)
    String userEmail;
    @Column(nullable = false)
    String password;
    @Column(unique = true, nullable = false)
    String userNickname;
    @Column
    @Default
    String userPictureUrl = null;
    @Column
    String loginType;
    @Column
    @Default
    int userLevel = 0;
    @Column
    @Default
    int userStatus = 0;
}
