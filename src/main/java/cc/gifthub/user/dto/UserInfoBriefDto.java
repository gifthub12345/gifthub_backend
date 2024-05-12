package cc.gifthub.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoBriefDto {
    private String name;
    @Builder
    public UserInfoBriefDto(String name) {
        this.name = name;
    }
}
