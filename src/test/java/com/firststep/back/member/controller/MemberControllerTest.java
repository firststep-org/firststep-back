package com.firststep.back.member.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static reactor.core.publisher.Mono.when;

import com.firststep.back.global.exception.GlobalExceptionHandler;
import com.firststep.back.member.dto.MemberResponseDto;
import com.firststep.back.member.form.AddMemberForm;
import com.firststep.back.member.service.MemberService;
import com.nimbusds.jose.shaded.gson.Gson;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {
    @InjectMocks
    private MemberController memberController;
    @Mock
    private MemberService memberService;
    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
        gson = new Gson();
    }

    @Test
    public void MockMvc_isNotNull() {
        assertNotNull(memberController);
        assertNotNull(mockMvc);
    }

    @Test
    public void addMember_SUCCESS() throws Exception {
        // given
        String memberEmail = "test@test.com";
        String password = "testPassword";
        String memberNickname = "testNickname";
        String loginType = "testLoginType";
        final String url = "/api/member";

        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
                .memberId(1L)
                .memberEmail(memberEmail)
                .memberNickname(memberNickname)
                .loginType(loginType)
                .build();
        doReturn(memberResponseDto).when(memberService).addMember(AddMemberForm.builder()
                        .memberEmail(memberEmail)
                        .password(password)
                        .memberNickname(memberNickname)
                        .loginType(loginType)
                .build());
        // when
        final ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(createAddMemberForm(memberEmail, password, memberNickname, loginType))));
        // then
        resultActions.andExpect(status().isCreated());
        MemberResponseDto response = gson.fromJson(
                resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8),
                MemberResponseDto.class);
        assertThat(memberResponseDto.memberId()).isEqualTo(1L);
        assertThat(memberResponseDto.memberEmail()).isEqualTo(memberEmail);
    }

    @Test
    public void addMember_FAIL() throws Exception {
        // given
        String memberEmail = "test";
        String password = "testPassword";
        String memberNickname = "testNickname";
        String loginType = "testLoginType";
        final String url = "/api/member";

        // when
        final ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(createAddMemberForm(memberEmail, password, memberNickname, loginType))));
        // then
        resultActions.andExpect(status().isBadRequest());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(contentAsString).contains("must be a well-formed email address");
    }

    private AddMemberForm createAddMemberForm(String memberEmail, String password, String memberNickname, String loginType) {
        return AddMemberForm.builder()
                .memberEmail(memberEmail)
                .password(password)
                .memberNickname(memberNickname)
                .loginType(loginType)
                .build();
    }

}