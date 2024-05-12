package cc.gifthub.user.controller;

import cc.gifthub.user.dto.UserInfoBriefDto;
import cc.gifthub.user.dto.UserInfoDto;
import cc.gifthub.user.service.UserServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;

    //  방 전체 참여자 보기
    @GetMapping("/rooms/{room_id}/users")
    public ResponseEntity<?> getUsers(@PathVariable("room_id") Long room_id) throws IOException {
        List<UserInfoBriefDto> userinfoList = userServiceImpl.getUserinfoList(room_id);
        return ResponseEntity.ok().body(userinfoList);
    }

    //  개인 정보 보기
    @GetMapping("/rooms/{room_id}/users/{user_id}")
    public ResponseEntity<?> getUserInfo(@PathVariable("user_id") Long user_id) throws IOException {
        UserInfoDto userinfo = userServiceImpl.getUserinfo(user_id);
        return ResponseEntity.ok().body(userinfo);
    }
}
