package cc.gifthub.room.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_MATCH_ID(HttpStatus.BAD_REQUEST, "코드 번호가 일치하지 않습니다"),
    //USER_ID_EXISTS(HttpStatus.BAD_REQUEST, "유저의 정보가 이미 DB에 존재합니다"),
    //GIFTICON_EXISTS(HttpStatus.BAD_REQUEST, "기프티콘이 이미 DB에 존재합니다");

    private final HttpStatus status;
    private final String errorMessage;
}
