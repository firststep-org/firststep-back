package com.firststep.back.member.controller;

import com.firststep.back.global.dto.ResponseDto;
import com.firststep.back.member.dto.MemberResponseDto;
import com.firststep.back.member.form.AddMemberForm;
import com.firststep.back.member.service.MemberService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.executable.ValidateOnExecution;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @GetMapping("api/member/email")
    public ResponseEntity<ResponseDto> findMemberByEmail(@RequestParam @Email String memberEmail) {
        MemberResponseDto memberResponseDto = memberService.findMemberByEmail(memberEmail);
        return ResponseEntity.ok().body(
                ResponseDto.builder()
                        .message("회원 조회 성공")
                        .data(memberResponseDto)
                        .build());
    }
}
