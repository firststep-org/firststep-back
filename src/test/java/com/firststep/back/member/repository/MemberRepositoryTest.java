package com.firststep.back.member.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.throwable;

import com.firststep.back.member.entity.Member;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    public void init() {
        member = Member.builder()
                .memberEmail("email@test.com")
                .memberNickname("test")
                .password("test")
                .loginType("normal")
                .build();
        memberRepository.save(member);
    }

    @Test
    public void Repository_isNotNull() {
        assertThat(memberRepository).isNotNull();
    }

    @Test
    public void memberSave() {
        Member toSave = Member.builder()
                .memberEmail("test2@member.com")
                .memberNickname("test2")
                .password("test2")
                .loginType("normal")
                .build();
        memberRepository.save(toSave);
        assertThat(memberRepository.findAll()).contains(toSave);
    }

    @Test
    public void memberSave_FAIL_NOTEMAIL() {
        Member toSave = Member.builder()
                .memberEmail("test2")
                .memberNickname("test2")
                .password("test2")
                .loginType("normal")
                .build();
        assertThatThrownBy(() -> memberRepository.save(toSave))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void memberSave_FAIL_EMAIL_NULL() {
        Member toSave = Member.builder()
                .memberNickname("test2")
                .password("test2")
                .loginType("normal")
                .build();
        assertThatThrownBy(() -> memberRepository.save(toSave))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void findByMemberEmail() {
        String email = "email@test.com";
        int userStatus = 0;
        Member byEmailAndUserStatus = memberRepository.findByMemberEmailAndMemberStatus(email, userStatus);
        assertThat(byEmailAndUserStatus).isNotNull();
        assertThat(byEmailAndUserStatus.getMemberEmail()).isEqualTo(email);
        assertThat(byEmailAndUserStatus.getMemberStatus()).isEqualTo(userStatus);
    }

    @Test
    public void findByMemberNickname() {
        String nickname = "test";
        int userStatus = 0;
        Member byMemberNickname = memberRepository.findByMemberNicknameAndMemberStatus(nickname, userStatus);
        assertThat(byMemberNickname).isNotNull();
        assertThat(byMemberNickname.getMemberNickname()).isEqualTo(nickname);
        assertThat(byMemberNickname.getMemberStatus()).isEqualTo(userStatus);
    }

    @Test
    public void findByMemberEmail_FAIL_WrongEmail() {
        String email = "email2@test.com";
        int userStatus = 0;
        Member byEmailAndUserStatus = memberRepository.findByMemberEmailAndMemberStatus(email, userStatus);
        assertThat(byEmailAndUserStatus).isNull();
    }

    @Test
    public void findByMemberEmail_FAIL_Deleted_Member() {
        String email = "email@test.com";
        int userStatus = 4; // 나중에 enum으로 바꿀 예정.
        Member byEmailAndUserStatus = memberRepository.findByMemberEmailAndMemberStatus(email, userStatus);
        assertThat(byEmailAndUserStatus).isNull();
    }

    @Test
    public void findByMemberNickname_FAIL_WrongNickname() {
        String nickname = "test2";
        int userStatus = 0;
        Member byMemberNickname = memberRepository.findByMemberNicknameAndMemberStatus(nickname, userStatus);
        assertThat(byMemberNickname).isNull();
    }

    @Test
    public void findByMemberNickname_FAIL_Deleted_Member() {
        String nickname = "test";
        int userStatus = 4;
        Member byMemberNickname = memberRepository.findByMemberNicknameAndMemberStatus(nickname, userStatus);
        assertThat(byMemberNickname).isNull();
    }

    @Test
    public void findAllByMemberLevel() {
        int memberLevel = 0;
        List<Member> byMemberLevel = memberRepository.findAllByMemberLevel(memberLevel);
        assertThat(byMemberLevel).isNotNull();
        assertThat(byMemberLevel.size()).isEqualTo(1);
        assertThat(byMemberLevel.get(0).getMemberLevel()).isEqualTo(memberLevel);
    }

    @Test
    public void findAllByMemberLevel_FAIL_WrongLevel() {
        int memberLevel = 2;
        List<Member> byMemberLevel = memberRepository.findAllByMemberLevel(memberLevel);
        assertThat(byMemberLevel.size()).isEqualTo(0);
    }

    @Test
    public void findAllByMemberStatus() {
        int memberStatus = 0;
        List<Member> byMemberStatus = memberRepository.findAllByMemberStatus(memberStatus);
        assertThat(byMemberStatus).isNotNull();
        assertThat(byMemberStatus.size()).isEqualTo(1);
        assertThat(byMemberStatus.get(0).getMemberStatus()).isEqualTo(memberStatus);
    }
}
