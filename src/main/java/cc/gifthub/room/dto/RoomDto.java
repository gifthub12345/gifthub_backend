package cc.gifthub.room.dto;

import cc.gifthub.category.dto.CategoryDto;
import cc.gifthub.room.domain.RoomEntity;
import cc.gifthub.user.dto.UserDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;
    private String name;
    private List<CategoryDto> categories;
    private UserDto user;
    public static RoomDto from(RoomEntity room) {
        return RoomDto.builder()
                .id(room.getId())
                .user(UserDto.from(room.getUser()))
                .name(room.getName())
                .categories(room.getCategories().stream()
                        .map(categoryEntity -> CategoryDto.from(categoryEntity))
                        .collect(Collectors.toList()))
                .build();
    }
}
