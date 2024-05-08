package cc.gifthub.room.exception;

import lombok.Getter;

@Getter
public class RoomException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String errorMessage;

    public RoomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getErrorMessage();
    }
}
