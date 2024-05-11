package cc.gifthub.room.service;

import cc.gifthub.category.domain.CategoryEntity;
import cc.gifthub.category.repository.CategoryRepository;
import cc.gifthub.category.repository.RoomCategoryRepository;
import cc.gifthub.category.type.RoomCategory;
import cc.gifthub.room.domain.RoomCategoryEntity;
import cc.gifthub.room.domain.RoomEntity;
import cc.gifthub.room.dto.CreateRoomForm;
import cc.gifthub.room.dto.RoomDto;
import cc.gifthub.room.exception.ErrorCode;
import cc.gifthub.room.exception.RoomException;
import cc.gifthub.room.repository.RoomRepository;
import cc.gifthub.user.domain.UserEntity;
import cc.gifthub.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class RoomServiceImpl {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RoomCategoryRepository roomCategoryRepository;

    @Transactional
    public RoomDto createRoom(CreateRoomForm form) {
        RoomEntity room = roomRepository.save(RoomEntity.builder()
                .id(form.getId())
                .name(form.getName())
                .build());

        saveRoomCategory(room, getCategories(form.getCategoryIds()));
        return RoomDto.from(room);
    }
    private List<CategoryEntity> getCategories(List<Long> categoryIds){
        List<CategoryEntity> categories = categoryRepository.findAllById(categoryIds);

        if(categories.size() != categoryIds.size()){
            throw new RoomException(ErrorCode.NOT_FOUND_CATEGORY);
        }

        return categories;
    }

    private void saveRoomCategory(RoomEntity room, List<CategoryEntity> categories) {

        List<RoomCategoryEntity> roomCategories = new ArrayList<>();
        for(CategoryEntity category : categories) {
            roomCategories.add(new RoomCategoryEntity(room, category));
        }
        roomCategoryRepository.saveAll(roomCategories);
    }

}
