package cc.gifthub.room.service;

import cc.gifthub.room.domain.RoomEntity;
import cc.gifthub.room.dto.ApplicantDto;
import cc.gifthub.room.repository.ApplicantRepository;
import cc.gifthub.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomApplicantService {
    private final ApplicantRepository applicantRepository;
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public List<ApplicantDto> getApplicants(Long roomId) {
        //QueryDsl로 구현
        return applicantRepository.findAllbyRoomId(roomId);
    }
}
