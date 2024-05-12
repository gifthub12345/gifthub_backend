package cc.gifthub.user.domain;

import cc.gifthub.room.domain.RoomEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String identifier;
    private String name;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id")
    private RoomEntity room;


    @Builder
    public UserEntity(Long id, String identifier, String name, String email, RoomEntity room) {
        this.id = id;
        this.identifier = identifier;
        this.name = name;
        this.email = email;
        this.room = room;
    }

    public void updateNameAndEmail(String newName, String newEmail) {
        this.name = newName;
        this.email = newEmail;
    }

    public void updateRoom(RoomEntity newRoom) {
        this.room = newRoom;
    }
}
