package cc.gifthub.image.service;

import cc.gifthub.category.domain.CategoryEntity;
import cc.gifthub.category.repository.CategoryRepository;
import cc.gifthub.image.domain.ImageEntity;
import cc.gifthub.image.dto.ImageUploadDto;
import cc.gifthub.image.repository.ImageRepository;
import cc.gifthub.room.domain.RoomEntity;
import cc.gifthub.room.repository.RoomRepository;
import cc.gifthub.user.domain.UserEntity;
import cc.gifthub.user.repository.UserRepository;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
    private final AmazonS3Client amazonS3Client;
    private final ImageRepository ImageRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final CategoryRepository categoryRepository;

    @Value("${cloud.aws.bucket-name}")
    private String bucket;

    @Override
    public String upload(MultipartFile image, ImageUploadDto imageUploadDto) throws IOException {
        if(image.isEmpty() || Objects.isNull(image.getOriginalFilename())){
            throw new IOException("비어있는 파일");
        }
        String s3Url = this.uploadImage(image);

        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~");

        Optional<UserEntity> userById = userRepository.findById(imageUploadDto.getUser_id());
        UserEntity userEntity = userById.get();

        Optional<CategoryEntity> categoryById = categoryRepository.findById(imageUploadDto.getCategory_id());
        CategoryEntity categoryEntity = categoryById.get();

        Optional<RoomEntity> roomById = roomRepository.findById(imageUploadDto.getRoom_id());
        RoomEntity roomEntity = roomById.get();
        log.info("{}, {}, {}", userEntity.getId(), categoryEntity.getId(), roomEntity.getId());
        ImageEntity imageEntity = ImageEntity.builder()
                .url(s3Url)
                .expire(LocalDateTime.now()) // change
                .barcode("barcode_change") // change
                .category(categoryEntity)
                .user(userEntity)
                .room(roomEntity)
                .build();

        ImageRepository.save(imageEntity);


        return s3Url;
    }

    private String uploadImage(MultipartFile image) throws IOException {
        this.validateImageExtension(image.getOriginalFilename());
        try{
            return this.uploadImageToS3(image);
        }catch (IOException e){
            throw new IOException("업로드 실패");
        }
    }

    private String uploadImageToS3(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        String s3FileName = UUID.randomUUID().toString() + originalFilename;

        InputStream inputStream = image.getInputStream();
        byte[] bytes = IOUtils.toByteArray(inputStream);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("image/" + extension);
        objectMetadata.setContentLength(bytes.length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try{
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, s3FileName, byteArrayInputStream, objectMetadata);
            amazonS3Client.putObject(putObjectRequest);

        }catch (AmazonS3Exception e){
            throw new IOException("s3에 등록 실패");
        }finally {
            byteArrayInputStream.close();
            inputStream.close();
        }
        return amazonS3Client.getUrl(bucket, s3FileName).toString();
    }

    private void validateImageExtension(String fileOriginName) throws IOException {
        int lastDotIndex = fileOriginName.lastIndexOf(".");
        if(lastDotIndex == -1){
            throw new IOException("파일명 오류 - 확장자 없음");
        }
        String extension = fileOriginName.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtensionList = Arrays.asList("jpg", "png", "jpeg", "gif");

        if(!allowedExtensionList.contains(extension)){
            throw new IOException("확장자 오류 - 해당 확장자는 이미지가 아님");
        }
    }

    @Override
    public void deleteImageFromS3(String imageAddress) throws IOException {
        String key = getKeyFromImageAddress(imageAddress);
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));

        ImageRepository.delete(ImageRepository.findByUrl(imageAddress));
    }

    private String getKeyFromImageAddress(String imageAddress) throws IOException {
        try{
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1);
        }catch(IOException e){
            throw new IOException("s3주소 디코딩 실패");
        }
    }
}
