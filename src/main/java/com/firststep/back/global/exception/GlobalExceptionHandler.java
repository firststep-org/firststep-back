package com.firststep.back.global.exception;

import static com.firststep.back.global.exception.ExceptionResult.UNKNOWN_ERROR;
import static com.firststep.back.global.exception.ExceptionResult.VALIDATION_ERROR;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        final List<String> errorList = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        log.warn("Invalid DTO Parameter errors : {}", errorList);
        return this.makeErrorResponseEntity(HttpStatus.BAD_REQUEST, VALIDATION_ERROR, errorList.toString());
    }

    private ResponseEntity<Object> makeErrorResponseEntity(final HttpStatus status, ExceptionResult code, final String errorDescription) {
        return ResponseEntity.status(status)
                .body(new ExceptionResponse(code.toString(), errorDescription));
    }

    @ExceptionHandler({MemberException.class})
    public ResponseEntity<Object> handleRestApiException(final MemberException exception) {
        log.warn("MembershipException occur: ", exception);
        ExceptionResult exceptionResult = exception.getExceptionResult();
        return this.makeErrorResponseEntity(exceptionResult.getHttpStatus(), exceptionResult, exceptionResult.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(final Exception exception) {
        log.warn("Exception occur: ", exception);
        return this.makeErrorResponseEntity(UNKNOWN_ERROR.getHttpStatus(), UNKNOWN_ERROR, exception.getMessage());
    }

    @Getter
    @RequiredArgsConstructor
    static class ExceptionResponse {
        private final String code;
        private final String message;
    }
}
