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
    public RoomDto createRoom(Long userId, CreateRoomForm form) {
        //나중에 오류 발생 예외 처리 필요하면 개발
        UserEntity user = userRepository.findById(userId).orElse(null);
        RoomEntity room = new RoomEntity();
        return RoomDto.from(room);
    }
}
