package cc.gifthub.room.service;

import cc.gifthub.image.repository.ImageRepository;
import cc.gifthub.room.domain.RoomEntity;
import cc.gifthub.room.dto.RoomJoinDto;
import cc.gifthub.room.repository.RoomRepository;
import cc.gifthub.user.domain.UserEntity;
import cc.gifthub.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Override
    public void createRoom(Long currentUserId) {
        byte[] array = new byte[10]; // length is bounded by 10
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        if(roomRepository.findByCode(generatedString) == null){
            RoomEntity newRoom = RoomEntity.builder()
                    .code(generatedString)
                    .build();
            roomRepository.save(newRoom);

            userRepository.findById(currentUserId).ifPresent(user -> {
                user.updateRoom(newRoom);
                log.info("asdasdasdas" + user.getRoom().getCode() + user.getRoom().getId().toString());
                userRepository.save(user);
            });
        }
    }


    @Override
    public void enterRoom(Long currentUserId, RoomJoinDto roomJoinDto) {
        RoomEntity roomByCode = validateRoom(roomJoinDto);
        if (roomByCode != null) {
            Optional<UserEntity> byId = userRepository.findById(currentUserId);
            if(byId.isPresent()){
                UserEntity userEntity = byId.get();
                userEntity.updateRoom(roomByCode);
            }
        }


    }
    public RoomEntity validateRoom(RoomJoinDto roomJoinDto) {
        RoomEntity roomByCode = roomRepository.findByCode(roomJoinDto.getCode());
        if (roomByCode == null) {
            return null;
        }else{
            return roomByCode;
        }

    }

    @Override
    public void exitRoom(Long currentUserId, Long room_id) {

        Optional<RoomEntity> roomById = roomRepository.findById(room_id);
        Optional<UserEntity> userById = userRepository.findById(currentUserId);

        if (roomById.isPresent() && userById.isPresent()) {
            RoomEntity findRoom = roomById.get();
            UserEntity findUser = userById.get();

            // currentUser 나기기: currentUser의 room_id => null
            findUser.updateRoom(null);
            userRepository.save(findUser);

            // 나가는 user가 업로드한 자원 삭제
            imageRepository.deleteAllByUserId(findUser.getId());

            // 남아있던 인원 수에 따라 방 폭 결정
            if (findRoom.getUsers().isEmpty()) {
                roomRepository.delete(findRoom);
            }

        }
    }
}
