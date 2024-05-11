package cc.gifthub.room.domain;

import cc.gifthub.category.domain.CategoryEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "room_category_check",
        columnNames = {"room_id", "category_id"})
})

public class RoomCategoryEntity{
    public RoomCategoryEntity(RoomEntity roomEntity, CategoryEntity categoryEntity) {
        setRoom(room);
        this.category = categoryEntity;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private RoomEntity room;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CategoryEntity category;

    public void setRoom(RoomEntity room) {
        if(this.room != null){
            this.room.getCategories().remove(this);
        }
        this.room = room;
        //room.getCategories().add(this);
    }
}
