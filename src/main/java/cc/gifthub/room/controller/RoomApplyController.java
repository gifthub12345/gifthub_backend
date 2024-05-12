package cc.gifthub.room.controller;

import cc.gifthub.room.dto.ApplicantDto;
import cc.gifthub.room.service.RoomApplyServiceImpl;
import cc.gifthub.room.service.RoomServiceImpl;
import cc.gifthub.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomApplyController {

    private final RoomServiceImpl roomServiceImpl;
    private final RoomApplyServiceImpl roomApplyServiceImpl;

    @PostMapping("/join")
    public ResponseEntity<ApplicantDto> applicantRoom(
            @AuthenticationPrincipal UserEntity user,
            @PathVariable Long roomId
    ){
        roomApplyServiceImpl.applyRoom(roomId, user.getId());
        ApplicantDto applicantDto = new ApplicantDto();
        applicantDto.setResponseMessage("You have successfully joined the room.");
        applicantDto.setRoomId(roomId);
        applicantDto.setUserId(user.getId());

        return ResponseEntity.ok().body(applicantDto);
    }
}
