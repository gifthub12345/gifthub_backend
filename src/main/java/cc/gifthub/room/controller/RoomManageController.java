package cc.gifthub.room.controller;

import cc.gifthub.room.dto.CreateRoomForm;
import cc.gifthub.room.dto.RoomDto;
import cc.gifthub.room.service.RoomService;
import cc.gifthub.room.service.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")

public class RoomManageController {
    private final RoomServiceImpl roomServiceImpl;

    //나중에 ID 받아오기
    @PostMapping
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
