package com.firststep.back.member.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record FindMemberForm(
        @Email
        String memberEmail
) {
}
