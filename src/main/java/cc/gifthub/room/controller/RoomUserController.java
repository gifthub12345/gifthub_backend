package cc.gifthub.room.controller;

import cc.gifthub.room.dto.ApplicantDto;
import cc.gifthub.room.dto.RoomApplicantsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomUserController {

    @GetMapping("/user")
    public ResponseEntity<RoomApplicantsResponse> getApplicants(
            @PathVariable Long roomId, @PathVariable List<ApplicantDto> applicants
    ){
        return ResponseEntity.ok().body(new RoomApplicantsResponse(roomId, applicants));
    }
}
