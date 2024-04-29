package cc.gifthub.image.repository;

import cc.gifthub.image.domain.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    ImageEntity findByUrl(String url);
}
