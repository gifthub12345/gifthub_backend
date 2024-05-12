package cc.gifthub.room.repository;

import cc.gifthub.room.domain.ApplicantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<ApplicantEntity, Long>, QueryDslRepo {

    boolean existsByRoomIdAndApplicantId(Long roomId, Long applicantId);

    Optional<ApplicantEntity> findByRoomId(Long userId);
}
