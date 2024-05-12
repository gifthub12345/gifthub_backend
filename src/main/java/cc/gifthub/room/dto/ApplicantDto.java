package cc.gifthub.room.dto;


import cc.gifthub.category.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data


public class ApplicantDto {
    //private Long applicant_id;
    private String responseMessage;
    private Long roomId;
    private Long userId;
    private List<CategoryDto> categories;
}
