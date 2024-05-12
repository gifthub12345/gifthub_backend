package cc.gifthub.room.controller;

import cc.gifthub.room.dto.ApplicantDto;
import cc.gifthub.room.dto.CreateRoomForm;
import cc.gifthub.room.dto.RoomDto;
import cc.gifthub.room.service.RoomApplyServiceImpl;
import cc.gifthub.room.service.RoomService;
import cc.gifthub.room.service.RoomServiceImpl;
import cc.gifthub.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")

public class RoomManageController {
    private final RoomServiceImpl roomServiceImpl;
    private final RoomApplyServiceImpl roomApplyServiceImpl;

    //나중에 ID 받아오기
    @PostMapping("/new")
    public ResponseEntity<RoomDto> createRoom(
            @RequestBody CreateRoomForm form
    ) {
        //수정 잊지 말기
        Long userId = 1L;
        return ResponseEntity.ok(
                roomServiceImpl.createRoom(form)
        );
    }
}
