package cc.gifthub.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Builder
    public UserEntity(Long id, String identifier, String name, String email) {
        this.id = id;
        this.identifier = identifier;
        this.name = name;
        this.email = email;
    }

    public void updateNameAndEmail(String newName, String newEmail) {
        this.name = newName;
        this.email = newEmail;
    }
}
