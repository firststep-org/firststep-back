package com.firststep.back.member.controller;

import com.firststep.back.global.dto.ResponseDto;
import com.firststep.back.member.dto.MemberResponseDto;
import com.firststep.back.member.form.AddMemberForm;
import com.firststep.back.member.service.MemberService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/api/member")
    public ResponseEntity<ResponseDto> addMember(@RequestBody @Valid AddMemberForm addMemberForm) {
        MemberResponseDto memberResponseDto = memberService.addMember(addMemberForm);
        return ResponseEntity.created(URI.create(String.format("member/%d", memberResponseDto.memberId()))).body(
                ResponseDto.builder()
                        .message("회원가입 성공")
                        .data(memberResponseDto)
                        .build());
    }
}
