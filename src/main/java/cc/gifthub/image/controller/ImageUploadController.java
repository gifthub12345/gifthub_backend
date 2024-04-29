package cc.gifthub.image.controller;

import cc.gifthub.image.dto.ImageUploadDto;
import cc.gifthub.image.service.ImageServiceImpl;
import cc.gifthub.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageUploadController {

    private final ImageServiceImpl imageServiceImpl;

    @PostMapping("/rooms/{room_id}/categories/{category_id}")
    public ResponseEntity<?> s3Upload(@RequestPart(value = "image", required = false) MultipartFile image,
                                      @PathVariable("room_id") Long room_id,
                                      @PathVariable("category_id") Long category_id) throws IOException {
        ImageUploadDto imageUploadDto = ImageUploadDto.builder()
                .room_id(room_id)
                .category_id(category_id)
                .user_id(1l)
                .build();
        String imagePath = imageServiceImpl.upload(image, imageUploadDto);
        return ResponseEntity.ok().body(imagePath);
    }


    @DeleteMapping("/room")
    public ResponseEntity<?> s3Delete(@RequestParam String addr) throws IOException {
        imageServiceImpl.deleteImageFromS3(addr);
        return ResponseEntity.ok().body(null);
    }


}
