package cc.gifthub.room.dto;

import cc.gifthub.room.domain.RoomEntity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;
    private String name;
    //private UserDto user;
    public static RoomDto from(RoomEntity room) {
        return RoomDto.builder()
                .id(room.getId())
                //.user(UserDto.from(room.getUserEntity()))
                .name(room.getName())
                .build();
    }
}
