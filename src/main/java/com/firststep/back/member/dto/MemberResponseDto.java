package com.firststep.back.member.dto;

import com.firststep.back.member.entity.Member;
import lombok.Builder;


@Builder

public record MemberResponseDto(
        long memberId,
        String memberEmail,
        String memberNickname,
        String memberPictureUrl,
        String loginType,
        int memberLevel,
        int memberStatus
) {
    public static MemberResponseDto toResponseDto(Member member) {
        return MemberResponseDto.builder()
                .memberId(member.getId())
                .memberEmail(member.getMemberEmail())
                .memberNickname(member.getMemberNickname())
                .memberPictureUrl(member.getMemberPictureUrl())
                .loginType(member.getLoginType())
                .memberLevel(member.getMemberLevel())
                .memberStatus(member.getMemberStatus())
                .build();
    }
}
