package cc.gifthub.room.domain;

import cc.gifthub.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)

@Builder
@Table(
    uniqueConstraints = {
            @UniqueConstraint(name = "unique_user_room",
            columnNames = {"user_id", "room_id"})
        }
)

@Entity
public class ApplicantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    private RoomEntity room;
}
