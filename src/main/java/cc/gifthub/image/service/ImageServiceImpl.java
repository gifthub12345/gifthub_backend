package cc.gifthub.image.service;

import cc.gifthub.category.domain.CategoryEntity;
import cc.gifthub.category.repository.CategoryRepository;
import cc.gifthub.image.domain.ImageEntity;
import cc.gifthub.image.dto.ImageS3GetDto;
import cc.gifthub.image.dto.ImageUploadDto;
import cc.gifthub.image.exception.*;
import cc.gifthub.image.repository.ImageRepository;
import cc.gifthub.room.domain.RoomEntity;
import cc.gifthub.room.repository.RoomRepository;
import cc.gifthub.user.domain.UserEntity;
import cc.gifthub.user.repository.UserRepository;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ImageServiceImpl implements ImageService{
    private final AmazonS3Client amazonS3Client;
    private final ImageRepository ImageRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${cloud.aws.bucket-name}")
    private String bucket;

    @Override
    public String upload(MultipartFile image, ImageUploadDto imageUploadDto) throws IOException {
        if(image.isEmpty() || Objects.isNull(image.getOriginalFilename())){
            throw new ImageEmptyException();
        }

        String s3Url = this.uploadImage(image);

        Optional<UserEntity> userById = userRepository.findById(imageUploadDto.getUser_id());
        Optional<CategoryEntity> categoryById = categoryRepository.findById(imageUploadDto.getCategory_id());
        Optional<RoomEntity> roomById = roomRepository.findById(imageUploadDto.getRoom_id());

        UserEntity userEntity = userById.get();
        CategoryEntity categoryEntity = categoryById.get();
        RoomEntity roomEntity = roomById.get();

        List<ImageEntity> listByRoomIdAndCategoryId = imageRepository.findByRoomIdAndCategoryId(roomEntity.getId(), categoryEntity.getId());
        for(ImageEntity imageEntity : listByRoomIdAndCategoryId){
            if(bCryptPasswordEncoder.matches(imageUploadDto.getBarcode(), imageEntity.getBarcode())){
                throw new ImageAlreadyExistException();
            }
        }

        ImageEntity imageEntity = ImageEntity.builder()
                .url(s3Url)
                .expire(imageUploadDto.getExpire())
                .barcode(bCryptPasswordEncoder.encode(imageUploadDto.getBarcode()))
                .category(categoryEntity)
                .user(userEntity)
                .room(roomEntity)
                .build();

        ImageRepository.save(imageEntity);

        return s3Url;
    }

    private String uploadImage(MultipartFile image)  {
        this.validateImageExtension(image.getOriginalFilename());
        try{
            return this.uploadImageToS3(image);
        }catch (IOException e){
            throw new S3ImageUploadException();
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

        }catch (S3ImageUploadException e){
            throw new S3ImageUploadException();
        }finally {
            byteArrayInputStream.close();
            inputStream.close();
        }
        return amazonS3Client.getUrl(bucket, s3FileName).toString();
    }

    private void validateImageExtension(String fileOriginName) {
        int lastDotIndex = fileOriginName.lastIndexOf(".");
        if(lastDotIndex == -1){
            throw new ExtensionNotFoundException();
        }
        String extension = fileOriginName.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtensionList = Arrays.asList("jpg", "png", "jpeg", "gif");

        if(!allowedExtensionList.contains(extension)){
            throw new ExtensionUnsuitableException();
        }
    }
    @Override
    public void deleteImageFromS3(Long gifticon_id) throws IOException {
        String key = getKeyFromGifitconId(gifticon_id);
        if (!key.isEmpty()) {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
            ImageRepository.deleteById(Math.toIntExact(gifticon_id));
        } else {
            throw new S3UrlException();
        }
    }

    private String getKeyFromGifitconId(Long gifticon_id) throws UnsupportedEncodingException  {
        Optional<ImageEntity> findImage = imageRepository.findById(Math.toIntExact(gifticon_id));
        if (findImage.isPresent()) {
            ImageEntity imageEntity = findImage.get();
            String decodingKey = URLDecoder.decode(imageEntity.getUrl(), "UTF-8");
            // URL에서 마지막 '/' 다음의 문자열부터 추출
            int lastSlashIndex = decodingKey.lastIndexOf('/');
            if (lastSlashIndex != -1 && lastSlashIndex < decodingKey.length() - 1) {
                return decodingKey.substring(lastSlashIndex + 1);
            } else {
                throw new S3UrlException();
            }
        } else {
            throw new S3ImageNotFoundException();
        }

    }



    @Override
    public List<ImageS3GetDto> getImagesFromS3(Long room_id, Long category_id) {
        List<ImageEntity> byRoomIdAndCategoryId = imageRepository.findByRoomIdAndCategoryId(room_id, category_id);
        if(byRoomIdAndCategoryId != null ){
            List<ImageS3GetDto> allImagesFromS3 = getAllImagesFromS3(byRoomIdAndCategoryId);
            return allImagesFromS3;

        }
        return null;
    }


    public List<ImageS3GetDto> getAllImagesFromS3(List<ImageEntity> s3List) {
        List<ImageS3GetDto> imageS3GetDtoList = new ArrayList<>();
        for(ImageEntity imageEntity: s3List){
            ImageS3GetDto imageS3GetDto = ImageS3GetDto.builder().url(imageEntity.getUrl()).build();
            imageS3GetDtoList.add(imageS3GetDto);
        }
        return imageS3GetDtoList;
    }

    @Override
    public ImageS3GetDto getOneImageFromS3(Long gifticon_id) {
        Optional<ImageEntity> getImage = imageRepository.findById(Math.toIntExact(gifticon_id));
        if(getImage.isPresent()){
            ImageEntity imageEntity = getImage.get();
            return ImageS3GetDto.builder().url(imageEntity.getUrl()).build();
        }
        return null;
    }
}
