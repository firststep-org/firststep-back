package com.firststep.back.global.dto;

import lombok.Builder;

@Builder
public record ResponseDto(
        String message,
        Object data
) {
}
