package cc.gifthub.category.service;

import cc.gifthub.category.domain.CategoryEntity;
import cc.gifthub.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> getAllCategories();

    List<CategoryDto> convert2CategoryDtoList(List<CategoryEntity> categoryEntityList);
}
