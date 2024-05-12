package cc.gifthub.room.repository;

import cc.gifthub.room.domain.RoomEntity;

import java.util.Optional;

public interface QueryDslApplicantRepository {
    Optional<RoomEntity> findByIdWithUserAndCategory(Long roomid);

}
