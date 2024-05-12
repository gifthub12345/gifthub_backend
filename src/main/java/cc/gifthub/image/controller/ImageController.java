package cc.gifthub.image.controller;

import cc.gifthub.image.dto.ImageOcrDto;
import cc.gifthub.image.dto.ImageS3GetDto;
import cc.gifthub.image.dto.ImageUploadDto;
import cc.gifthub.image.service.ImageServiceImpl;
import cc.gifthub.room.repository.RoomRepository;
import cc.gifthub.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ImageController {

    private final ImageServiceImpl imageServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private final RoomRepository roomRepository;

    // 특정 카테고리에 해당하는 이미지 가져오기
    @GetMapping("/rooms/{room_id}/categories/{category_id}")
    public ResponseEntity<?> s3Get(@PathVariable("room_id") Long room_id, @PathVariable("category_id") Long category_id) throws IOException {
        List<ImageS3GetDto> imagesFromS3 = imageServiceImpl.getImagesFromS3(room_id, category_id);
        return ResponseEntity.ok().body(imagesFromS3);
    }




    // 이미지 업로드
    @PostMapping("/rooms/{room_id}/categories/{category_id}")
    public ResponseEntity<?> s3Upload(@RequestPart(value = "image", required = false) MultipartFile image,
                                      @RequestPart ImageOcrDto imageOcrDto,
                                      @PathVariable("room_id") Long room_id,
                                      @PathVariable("category_id") Long category_id) throws IOException {

       // 현재 사용자 id 찾기
        Long currentUserId = userServiceImpl.getCurrentUserId(SecurityContextHolder.getContext());

        // s3 업로드
        ImageUploadDto imageUploadDto = ImageUploadDto.builder()
                .room_id(room_id)
                .category_id(category_id)
                .user_id(currentUserId)
                .barcode(imageOcrDto.getBarcode())
                .expire(imageOcrDto.getExpire())
                .build();
        String imagePath = imageServiceImpl.upload(image, imageUploadDto);
        return ResponseEntity.ok().body(imagePath);
    }

    // 이미지 하나 가져오기
    @GetMapping("/rooms/{room_id}/categories/{category_id}/gifticons/{gifticon_id}")
    public ResponseEntity<?> s3OneGet(@PathVariable("gifticon_id")Long gifticon_id) throws IOException {
        ImageS3GetDto oneImageFromS3 = imageServiceImpl.getOneImageFromS3(gifticon_id);
        return ResponseEntity.ok().body(oneImageFromS3);
    }

    // 이미지 삭제
    @DeleteMapping("/rooms/{room_id}/categories/{category_id}/gifticons/{gifticon_id}")
    public ResponseEntity<?> s3Delete(@PathVariable("gifticon_id")Long gifticon_id) throws IOException {
        imageServiceImpl.deleteImageFromS3(gifticon_id);
        return ResponseEntity.ok().body(null);
    }






}
