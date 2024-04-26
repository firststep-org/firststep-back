package com.firststep.back.member.service.impl;

import com.firststep.back.global.exception.ExceptionResult;
import com.firststep.back.global.exception.MemberException;
import com.firststep.back.member.dto.MemberResponseDto;
import com.firststep.back.member.entity.Member;
import com.firststep.back.member.form.AddMemberForm;
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
    public MemberResponseDto addMember(AddMemberForm addMemberForm) throws MemberException {
        boolean isDuplicateEmail = memberRepository.existsByMemberEmailAndMemberStatus(addMemberForm.memberEmail(), 0);
        boolean isDuplicateNickname = memberRepository.existsByMemberNicknameAndMemberStatus(addMemberForm.memberNickname(), 0);
        if (isDuplicateEmail) {
            throw new MemberException(ExceptionResult.DUPLACATE_EMAIL);
        }
        if (isDuplicateNickname) {
            throw new MemberException(ExceptionResult.DUPLACATE_NICKNAME);
        }

        Member member = Member.
                builder()
                .memberEmail(addMemberForm.memberEmail())
                .password(addMemberForm.password())
                .memberNickname(addMemberForm.memberNickname())
                .loginType(addMemberForm.loginType())
                .build();
        Member save = memberRepository.save(member);
        return MemberResponseDto.toResponseDto(save);
    }
}
