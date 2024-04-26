package com.firststep.back.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberException extends RuntimeException {
    private ExceptionResult exceptionResult;

    public MemberException(ExceptionResult exceptionResult) {
        this.exceptionResult = exceptionResult;
    }
}
