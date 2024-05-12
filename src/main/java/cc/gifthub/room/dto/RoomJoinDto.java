package cc.gifthub.room.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomJoinDto {
    String code;
    @Builder
    public RoomJoinDto(String code) {
        this.code = code;
    }
}
