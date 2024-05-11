package cc.gifthub.category.repository;


import cc.gifthub.room.domain.RoomCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomCategoryRepository extends JpaRepository<RoomCategoryEntity, Long> {
}
