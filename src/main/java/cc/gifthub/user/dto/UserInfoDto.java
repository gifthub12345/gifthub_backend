package cc.gifthub.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoDto {
    private String name;
    private String email;
    @Builder
    public UserInfoDto(String name, String email) {
        this.email = email;
        this.name = name;
    }
}
