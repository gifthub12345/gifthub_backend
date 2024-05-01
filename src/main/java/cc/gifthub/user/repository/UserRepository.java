package cc.gifthub.user.repository;

import cc.gifthub.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByIdentifier(String identifier);
}
