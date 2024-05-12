package cc.gifthub.category.service;

import cc.gifthub.category.domain.CategoryEntity;
import cc.gifthub.category.dto.CategoryDto;
import cc.gifthub.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryDto> convert2CategoryDtoList(List<CategoryEntity> categoryEntityList) {
        List<CategoryDto> categoryDtoList = new ArrayList<>();

        for(CategoryEntity categoryEntity : categoryEntityList) {
            CategoryDto categoryDto = CategoryDto.builder().title(categoryEntity.getTitle()).build();
            categoryDtoList.add(categoryDto);
        }
        return categoryDtoList;
    }
}
