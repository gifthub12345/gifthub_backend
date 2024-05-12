package cc.gifthub.room.controller;

import cc.gifthub.category.dto.CategoryDto;
import cc.gifthub.category.service.CategoryServiceImpl;
import cc.gifthub.image.service.ImageServiceImpl;
import cc.gifthub.room.dto.RoomJoinDto;
import cc.gifthub.room.service.RoomServiceImpl;
import cc.gifthub.user.dto.UserOAuthDto;
import cc.gifthub.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RoomController {
    private final RoomServiceImpl roomServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private final CategoryServiceImpl categoryServiceImpl;
    private final ImageServiceImpl imageServiceImpl;

    // 방 들어가기 - 공유받은 암호키 입력 후 입장
    @PostMapping("/room/enter")
    public ResponseEntity<?> enterRoom(@RequestBody RoomJoinDto roomJoinDto) {

        // 현재 사용자 id 찾기
        Long currentUserId = userServiceImpl.getCurrentUserId(SecurityContextHolder.getContext());
        roomServiceImpl.enterRoom(currentUserId, roomJoinDto);
        return ResponseEntity.ok().body("enterRoom success");

    }

    // 방 생성 - 현재 유저는 자동으로 입장됨
    @PostMapping("/room/new")
    public ResponseEntity<?> createRoom(){
        // 현재 사용자 id 찾기
        Long currentUserId = userServiceImpl.getCurrentUserId(SecurityContextHolder.getContext());
        roomServiceImpl.createRoom(currentUserId);
        return ResponseEntity.ok().body("createRoom success");
    }


    // 방 기본 화면 - 카테고리 리스트 받기
    @GetMapping("/rooms/{room_id}")
    public ResponseEntity<?> mainRoom(@PathVariable("room_id") Long room_id){
        List<CategoryDto> categoryDtoList = categoryServiceImpl.convert2CategoryDtoList(categoryServiceImpl.getAllCategories());
        return ResponseEntity.ok().body(categoryDtoList);
    }

    // 방 나가기
    @PostMapping("/rooms/{room_id}/exit")
    public ResponseEntity<?> exitRoom(@PathVariable("room_id")Long room_id){
        // 현재 사용자 id 찾기
        Long currentUserId = userServiceImpl.getCurrentUserId(SecurityContextHolder.getContext());
        roomServiceImpl.exitRoom(currentUserId, room_id);
        return ResponseEntity.ok().body("exitRoom success");
    }


}
