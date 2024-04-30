package com.firststep.back.member.controller;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.firststep.back.global.dto.ResponseDto;
import com.firststep.back.global.exception.GlobalExceptionHandler;
import com.firststep.back.member.dto.MemberResponseDto;
import com.firststep.back.member.form.AddMemberForm;
import com.firststep.back.member.service.MemberService;
import com.google.gson.Gson;
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
                .memberId(0L)
                .memberEmail(memberEmail)
                .memberNickname(memberNickname)
                .loginType(loginType)
                .build();
        given(memberService.addMember(any())).willReturn(memberResponseDto);
        // when
        final ResultActions resultActions = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(createAddMemberForm(memberEmail, password, memberNickname, loginType),
                                AddMemberForm.class)))
                .andDo(print());
        // then
        resultActions.andExpect(status().isCreated());
        MemberResponseDto response = gson.fromJson(
                gson.fromJson(resultActions.andReturn().getResponse().getContentAsString(UTF_8),
                        ResponseDto.class).data().toString(), MemberResponseDto.class);
        System.out.println(response);
        assertThat(response.memberId()).isEqualTo(0L);
        assertThat(response.memberEmail()).isEqualTo(memberEmail);
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
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString(UTF_8);
        assertThat(contentAsString).contains("must be a well-formed email address");
    }

    @Test
    public void findMemberByEmail_SUCCESS() throws Exception {
        // given
        String memberEmail = "test@test.com";
        doReturn(MemberResponseDto.builder()
                .memberId(0L)
                .memberEmail(memberEmail)
                .memberNickname("testNickname")
                .loginType("testLoginType")
                .build()).when(memberService).findMemberByEmail(memberEmail);
        // then
        final ResultActions resultActions = mockMvc.perform(get("/api/member/email")
                .contentType(MediaType.APPLICATION_JSON)
                .param("memberEmail", memberEmail));
        // then
        resultActions.andExpect(status().isOk());
        MemberResponseDto response = gson.fromJson(
                gson.fromJson(resultActions.andReturn().getResponse().getContentAsString(UTF_8),
                        ResponseDto.class).data().toString(), MemberResponseDto.class);
        assertThat(response.memberId()).isEqualTo(0L);
        assertThat(response.memberEmail()).isEqualTo(memberEmail);
    }

    @Test
    public void findMemberByEmail_FAIL() throws Exception {
        // given
        String memberEmail = "test";
        // when
        final ResultActions resultActions = mockMvc.perform(get("/api/member/email")
                .contentType(MediaType.APPLICATION_JSON)
                .param("memberEmail", memberEmail));
        // then
        resultActions.andExpect(status().isBadRequest());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString(UTF_8);
        assertThat(contentAsString).contains("VALIDATION_ERROR");
    }

    private AddMemberForm createAddMemberForm(String memberEmail, String password, String memberNickname,
                                              String loginType) {
        return AddMemberForm.builder()
                .memberEmail(memberEmail)
                .password(password)
                .memberNickname(memberNickname)
                .loginType(loginType)
                .build();
    }

}