package cc.gifthub;

import cc.gifthub.category.domain.CategoryEntity;
import cc.gifthub.category.repository.CategoryRepository;
import cc.gifthub.category.repository.RoomCategoryRepository;
import cc.gifthub.category.type.RoomCategory;
import cc.gifthub.room.domain.RoomEntity;
import cc.gifthub.room.dto.CreateRoomForm;
import cc.gifthub.room.dto.RoomDto;
import cc.gifthub.room.repository.RoomRepository;
import cc.gifthub.room.service.RoomService;
import cc.gifthub.room.service.RoomServiceImpl;
import cc.gifthub.user.domain.RoomUserEntity;
import cc.gifthub.user.domain.UserEntity;
import cc.gifthub.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private RoomCategoryRepository roomCategoryRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private RoomServiceImpl roomService;

    private UserEntity user;
    private RoomEntity room;
    private final List<CategoryEntity> categories = new ArrayList<>();

    @BeforeEach
    void BeforeEach(){
        user = user.builder()
                .id(9L)
                .username("gifthub")
                .password("1234")
                .email("gifthub@google.com")
                .build();
        room = RoomEntity.builder()
                .id(999L)
                .user(user)
                .name("제목")
                .build();

        for(int i = 0; i < 2; i++){
            categories.add(CategoryEntity.builder()
                    .id((long) + 1)
                    .title("category" + i)
                    .category(RoomCategory.Chicken)
                    //image("image_url" + i)
                    .build());
        }
    }

    @Test
    @DisplayName("방 등록 성공")
    void createRoomSuccess(){
        //given
        RoomUserEntity roomUserEntity = RoomUserEntity.builder()
                        .id(1L)
                        .user(user)
                        .room(room)
                        .build();

        given(userRepository.findById(anyLong()))
                .willReturn(Optional.of(user));

        given(roomRepository.findById(any()))
                .willReturn(Optional.of(room));

        given(roomRepository.save(any(RoomEntity.class)))
                .willReturn(room);
        given(userRepository.save(any()))
                .willReturn(roomUserEntity);

        //when
        CreateRoomForm form = new CreateRoomForm(
                101L,
                "의미 없는 제목",
                List.of(1000L, 1001L)
        );
        RoomDto roomDto = roomService.createRoom(form);
        ArgumentCaptor<RoomUserEntity> captor = ArgumentCaptor.forClass(RoomUserEntity.class);

        //then
        assertEquals(room.getId(), roomDto.getId());
        assertEquals(user.getUsername(), roomDto.getUser().getId());
        assertEquals(room.getName(), roomDto.getName());
        assertEquals(room.getCategories().size(), roomDto.getCategories().size());
        verify(roomCategoryRepository, times(1)).save(any());
    }
}
