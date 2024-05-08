package cc.gifthub.room.dto;

import cc.gifthub.room.exception.ErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomErrorResponse {
    private ErrorCode errorCode;
    private String errorMessage;
}
