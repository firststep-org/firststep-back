package com.firststep.back.member.entity;

import com.firststep.back.global.entity.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String memberEmail;
    @Column
    private String password;
    @Column
    private String memberNickname;
    @Column
    @Builder.Default
    private String memberPictureUrl = null;
    @Column
    @NotBlank
    private String loginType; // TODO : Enum화
    @Column
    @Builder.Default
    private int memberLevel = 0; // TODO : Enum화??
    @Column
    @Builder.Default
    private int memberStatus = 0; // TODO : Enum화
}
