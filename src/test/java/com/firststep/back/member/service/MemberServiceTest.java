package com.firststep.back.member.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.firststep.back.global.exception.MemberException;
import com.firststep.back.member.dto.MemberResponseDto;
import com.firststep.back.member.entity.Member;
import com.firststep.back.member.repository.MemberRepository;
import com.firststep.back.member.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
@AutoConfigureTestDatabase
public class MemberServiceTest {
    @InjectMocks
    private MemberServiceImpl memberService;
    @Mock
    private MemberRepository memberRepository;

    @Test
    public void addUser_SUCCESS() {
        // given
        String memberEmail = "test@test.com";
        String password = "testPassword";
        String memberNickname = "testNickname";
        String loginType = "testLoginType";
        when(memberRepository.save(any(Member.class))).thenReturn(Member.builder()
                .id(0L)
                .memberEmail(memberEmail)
                .password(password)
                .memberNickname(memberNickname)
                .loginType(loginType)
                .build());
        // when
        MemberResponseDto member = memberService.addMember(memberEmail, password, memberNickname, loginType);

        // then
        assertThat(member).isNotNull();
        assertThat(member.memberEmail()).isEqualTo(memberEmail);
        assertThat(member.memberNickname()).isEqualTo(memberNickname);
        assertThat(member.loginType()).isEqualTo(loginType);
    }

    @Test
    public void addUser_FAIL_DuplicateEmail() {
        // given

        String memberEmail = "test@test.com";
        String password = "testPassword";
        String memberNickname = "testNickname";
        String memberNickname2 = "testNickname2";
        String loginType = "testLoginType";

        // when
        doReturn(true).when(memberRepository).existsByMemberEmailAndMemberStatus(memberEmail, 0);

        // then
        assertThatThrownBy(
                () -> memberService.addMember(memberEmail, password, memberNickname2, loginType)).isInstanceOf(
                MemberException.class);
    }

    @Test
    public void addUser_FAIL_DuplicateNickname() {
        // given

        String memberEmail = "test@test.com";
        String memberEmail2 = "test2@test.com";
        String password = "testPassword";
        String memberNickname = "testNickname";
        String loginType = "testLoginType";

        // when
        doReturn(true).when(memberRepository).existsByMemberNicknameAndMemberStatus(memberNickname, 0);

        // then
        assertThatThrownBy(
                () -> memberService.addMember(memberEmail2, password, memberNickname, loginType)).isInstanceOf(
                MemberException.class);
    }
}
