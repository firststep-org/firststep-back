package com.firststep.back.member.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.firststep.back.global.exception.MemberException;
import com.firststep.back.member.dto.MemberResponseDto;
import com.firststep.back.member.entity.Member;
import com.firststep.back.member.form.AddMemberForm;
import com.firststep.back.member.repository.MemberRepository;
import com.firststep.back.member.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    private static AddMemberForm addMemberForm;

    @BeforeAll
    public static void init() {
        String memberEmail = "test@test.com";
        String password = "testPassword";
        String memberNickname = "testNickname";
        String loginType = "testLoginType";
        addMemberForm = AddMemberForm.builder()
                .memberEmail(memberEmail)
                .password(password)
                .memberNickname(memberNickname)
                .loginType(loginType)
                .build();
    }

    @Test
    public void addUser_SUCCESS() {
        // given

        // when
        when(memberRepository.save(any(Member.class))).thenReturn(Member.builder()
                .id(0L)
                        .memberEmail(addMemberForm.memberEmail())
                        .password(addMemberForm.password())
                        .memberNickname(addMemberForm.memberNickname())
                        .loginType(addMemberForm.loginType())
                .build());
        MemberResponseDto member = memberService.addMember(addMemberForm);

        // then
        assertThat(member).isNotNull();
        assertThat(member.memberEmail()).isEqualTo(addMemberForm.memberEmail());
        assertThat(member.memberNickname()).isEqualTo(addMemberForm.memberNickname());
        assertThat(member.loginType()).isEqualTo(addMemberForm.loginType());
    }

    @Test
    public void addUser_FAIL_DuplicateEmail() {
        // given

        String memberNickname2 = "testNickname2";
        AddMemberForm addMemberForm1 = AddMemberForm.builder()
                .memberEmail(addMemberForm.memberEmail())
                .password(addMemberForm.password())
                .memberNickname(memberNickname2)
                .loginType(addMemberForm.loginType())
                .build();
        // when
        doReturn(true).when(memberRepository).existsByMemberEmailAndMemberStatus(addMemberForm.memberEmail(), 0);

        // then
        assertThatThrownBy(
                () -> memberService.addMember(addMemberForm1)).isInstanceOf(
                MemberException.class);
    }

    @Test
    public void addUser_FAIL_DuplicateNickname() {
        // given

        String memberEmail2 = "test2@test.com";
        AddMemberForm addMemberForm1 = AddMemberForm.builder()
                .memberEmail(memberEmail2)
                .password(addMemberForm.password())
                .memberNickname(addMemberForm.memberNickname())
                .loginType(addMemberForm.loginType())
                .build();
        // when
        doReturn(true).when(memberRepository).existsByMemberNicknameAndMemberStatus(addMemberForm.memberNickname(), 0);

        // then
        assertThatThrownBy(
                () -> memberService.addMember(addMemberForm1)).isInstanceOf(
                MemberException.class);
    }

    @Test
    public void findUserByEmail_SUCCESS() {
        // given
        String memberEmail = "test@test.test";
        Member member = Member.builder()
                .id(0L)
                .memberEmail(memberEmail)
                .password("testPassword")
                .memberNickname("testNickname")
                .loginType("testLoginType")
                .build();

        // when
        when(memberRepository.findByMemberEmailAndMemberStatus(memberEmail, 0)).thenReturn(member);
        MemberResponseDto memberResponseDto = memberService.findMemberByEmail(memberEmail);

        // then
        assertThat(memberResponseDto).isNotNull();
        assertThat(memberResponseDto.memberEmail()).isEqualTo(memberEmail);
    }
}
