package com.firststep.back.member.form;

import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
public record AddMemberForm(
        @Email
        String memberEmail,
        String password,
        String memberNickname,
        String loginType
) {
}
