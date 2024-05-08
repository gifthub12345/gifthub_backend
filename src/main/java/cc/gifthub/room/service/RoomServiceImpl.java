package cc.gifthub.room.service;

import cc.gifthub.room.domain.RoomEntity;
import cc.gifthub.room.dto.CreateRoomForm;
import cc.gifthub.room.dto.RoomDto;
import cc.gifthub.room.repository.RoomRepository;
import cc.gifthub.user.domain.UserEntity;
import cc.gifthub.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class RoomServiceImpl {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    @Transactional
    public RoomDto createRoom(CreateRoomForm form) {
        RoomEntity room = roomRepository.save(RoomEntity.builder()
                .id(form.getId())
                .name(form.getName())
                .build());
        return RoomDto.from(room);
    }
}
