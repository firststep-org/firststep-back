package com.firststep.back.member.service;

import com.firststep.back.member.dto.MemberResponseDto;

public interface MemberService {

    MemberResponseDto addMember(String email, String password, String nickname, String loginType);
}
