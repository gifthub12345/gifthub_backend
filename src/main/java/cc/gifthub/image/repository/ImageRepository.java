package cc.gifthub.image.repository;

import cc.gifthub.image.domain.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    List<ImageEntity> findByRoomIdAndCategoryId(Long roomId, Long categoryId);

    ImageEntity findByUrl(String url);

    boolean existsByBarcode(String barcode);

    void deleteAllByUserId(Long userId);
    void deleteAllByRoomId(Long roomId);
}
