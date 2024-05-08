package cc.gifthub.room.domain;

import cc.gifthub.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "room")
@Builder
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity user;

    private String name;
    public RoomEntity(String name, String code) {
        this.name = name;
    }
}
