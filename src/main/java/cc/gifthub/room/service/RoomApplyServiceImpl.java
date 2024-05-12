package cc.gifthub.room.service;

import cc.gifthub.room.domain.ApplicantEntity;
import cc.gifthub.room.exception.ErrorCode;
import cc.gifthub.room.exception.RoomException;
import cc.gifthub.room.repository.ApplicantRepository;
import cc.gifthub.room.repository.RoomRepository;
import cc.gifthub.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class RoomApplyServiceImpl {
    private final RoomRepository roomRepository;
    private final ApplicantRepository applicantRepository;
    private final UserRepository userRepository;

    //NOT_MATCH_ID(HttpStatus.BAD_REQUEST, "코드 번호가 일치하지 않습니다"),
    @Transactional
    public void applyRoom(Long userId, Long roomId) {
        //validateApplicant(roomId, userId);
        saveApplicant(roomId, userId);
    }

    private void authenticateRoomMember(Long userId, Long roomId) {
        if(applicantRepository.existsByRoomIdAndApplicantId(roomId, userId)) {
            throw new RoomException(ErrorCode.NOT_MATCH_ID);
        }
    }

    //private void validateApplicant

    private void saveApplicant(Long roomId, Long userId) {
        applicantRepository.save(ApplicantEntity.builder()
                .room(roomRepository.getReferenceById(roomId))
                .user(userRepository.getReferenceById(userId))
                .build());
    }
}
