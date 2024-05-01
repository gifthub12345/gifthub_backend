package cc.gifthub.image.domain;

import cc.gifthub.category.domain.CategoryEntity;
import cc.gifthub.room.domain.RoomEntity;
import cc.gifthub.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor
@Table(name= "gifticon")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gifticon_id")
    private Long id;

    private String url;
    private LocalDateTime expire;
    private String barcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @Builder
    public ImageEntity(Long id, String url, LocalDateTime expire, String barcode, CategoryEntity category, UserEntity user, RoomEntity room) {
        this.id = id;
        this.url = url;
        this.expire = expire;
        this.barcode = barcode;
        this.category = category;
        this.user = user;
        this.room = room;
    }
}