package cc.gifthub.user.dto;

import cc.gifthub.user.domain.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
public class UserOAuthDto {
    private String identifier;
    private String name;
    @Builder
    public UserOAuthDto(String identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public static UserOAuthDto toUserOAuthDto(UserEntity userEntity) {
        return UserOAuthDto.builder()
                .identifier(userEntity.getIdentifier())
                .name(userEntity.getName())
                .build();
    }
}
