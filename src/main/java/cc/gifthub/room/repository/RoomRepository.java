package cc.gifthub.room.repository;

import cc.gifthub.image.domain.ImageEntity;
import cc.gifthub.room.domain.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    RoomEntity findByCode(String code);



}
