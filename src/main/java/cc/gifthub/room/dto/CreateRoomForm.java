package cc.gifthub.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CreateRoomForm {

    private Long id;
    private String name;
    private List<Long> categoryIds;

}
