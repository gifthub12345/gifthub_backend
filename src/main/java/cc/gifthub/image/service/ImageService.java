package cc.gifthub.image.service;

import cc.gifthub.image.dto.ImageS3GetDto;
import cc.gifthub.image.dto.ImageUploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
     String upload(MultipartFile image, ImageUploadDto imageUploadDto) throws IOException;
     void deleteImageFromS3(Long gifticon_id) throws IOException;
     List<ImageS3GetDto> getImagesFromS3(Long room_id, Long category_id);
     ImageS3GetDto getOneImageFromS3(Long gifticon_id);
}
