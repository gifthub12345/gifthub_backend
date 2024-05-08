package cc.gifthub.room.exception;

import cc.gifthub.room.dto.RoomErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RoomExcpetionHandler {
    @ExceptionHandler(RoomException.class)
    public RoomErrorResponse roomeExceptionHandler(RoomException e){
        log.error("{} is occured", e.getErrorCode());
        return new RoomErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }
}
