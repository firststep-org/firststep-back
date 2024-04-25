package com.firststep.back.member.service.impl;

import com.firststep.back.global.exception.MemberException;
import com.firststep.back.member.dto.MemberResponseDto;
import com.firststep.back.member.entity.Member;
import com.firststep.back.member.repository.MemberRepository;
import com.firststep.back.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public MemberResponseDto addMember(String email, String password, String nickname, String loginType) {
        boolean isDuplicateEmail = memberRepository.existsByMemberEmailAndMemberStatus(email, 0);
        boolean isDuplicateNickname = memberRepository.existsByMemberNicknameAndMemberStatus(nickname, 0);
        if (isDuplicateEmail) {
            throw new MemberException("이메일이 중복됩니다.");
        }
        if (isDuplicateNickname) {
            throw new MemberException("닉네임이 중복됩니다.");
        }

        Member member = Member.
                builder()
                .memberEmail(email)
                .password(password)
                .memberNickname(nickname)
                .loginType(loginType)
                .build();
        Member save = memberRepository.save(member);
        return MemberResponseDto.toResponseDto(save);
    }
}
