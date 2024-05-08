package cc.gifthub.room.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_MATCH_ID(HttpStatus.BAD_REQUEST, "코드 번호가 일치하지 않습니다");

    private final HttpStatus status;
    private final String errorMessage;
}