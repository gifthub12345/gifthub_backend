package cc.gifthub.image.service;

import cc.gifthub.image.dto.ImageUploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
     String upload(MultipartFile image, ImageUploadDto imageUploadDto) throws IOException;
     void deleteImageFromS3(String imageAddress) throws IOException;

}
