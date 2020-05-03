package com.buysell.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.internal.Mimetypes;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.buysell.domain.DTO.BoardFileDTO;
import lombok.NoArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@NoArgsConstructor
public class FileService {
    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public List<BoardFileDTO> upload(MultipartFile[] uploadFile, Long uid) throws IOException {
        List<BoardFileDTO> list = new ArrayList<>();

        String uploadPath = getFolder(uid); // 파일 경로

        for (MultipartFile multipartFile : uploadFile){
            BoardFileDTO fileDTO = new BoardFileDTO();

            String uploadFileName = multipartFile.getOriginalFilename(); // 이름 원본

            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
            fileDTO.setFileName(uploadFileName); // 원본 이름 저장

            UUID uuid = UUID.randomUUID();

            uploadFileName = uuid.toString() + "_" + uploadFileName; // uuid + 이름 원본

            File saveFile = new File(uploadPath, uploadFileName);

            if (checkImageType(saveFile)) {
                fileDTO.setUploadPath(uploadPath);
                fileDTO.setUuid(uuid.toString());
                fileDTO.setImage(true);

                // 메타 데이터 저장
                ObjectMetadata meta = new ObjectMetadata();
//                meta.setContentType(Mimetypes.getInstance().getMimetype(uploadFileName)); // 파일명으로 contentType 설정
//                byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
//                meta.setContentLength(bytes.length); // 더 많은 데이터를 기다리는 것을 방지하기위해 사이즈 지정
                meta.setContentType(multipartFile.getContentType());
                meta.setContentLength(multipartFile.getSize());

                s3Client.putObject(new PutObjectRequest(bucket, uploadPath + "/" + uploadFileName, multipartFile.getInputStream(), meta)
                        .withCannedAcl(CannedAccessControlList.PublicRead)); // 원본 저장

                // 썸네일 생성
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                Thumbnails.of(multipartFile.getInputStream()).size(480, 600).toOutputStream(os);
                InputStream is = new ByteArrayInputStream(os.toByteArray());
                meta.setContentLength(os.size());

                s3Client.putObject(new PutObjectRequest(bucket, uploadPath + "/s_" + uploadFileName, is, meta)
                        .withCannedAcl(CannedAccessControlList.PublicRead)); // 썸네일 저장
            }
            list.add(fileDTO);
        }

        return list;
    }

    public Map<String, Object> uploadProfile(MultipartFile uploadFile, Long uid){
        Map<String, Object> map = new HashMap<>();

        String uploadPath = uid + "/profile"; // 개인 추가 업로드 경로

        String uploadFileName = uploadFile.getOriginalFilename();

        // IE file path
        uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);

        try{
            File saveFile = new File(uploadPath, uploadFileName);

            if (checkImageType(saveFile)){
                map.put("uploadPath", uploadPath);
                map.put("fileName", uploadFileName);
                map.put("image", true);

                ObjectMetadata meta = new ObjectMetadata();
                meta.setContentType(uploadFile.getContentType());
                meta.setContentLength(uploadFile.getSize());

                s3Client.putObject(new PutObjectRequest(bucket, uploadPath + "/" + uploadFileName, uploadFile.getInputStream(), meta)
                        .withCannedAcl(CannedAccessControlList.PublicRead)); // 원본 저장

                // 썸네일 생성
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                Thumbnails.of(uploadFile.getInputStream()).size(64, 64).toOutputStream(os);
                InputStream is = new ByteArrayInputStream(os.toByteArray());
                meta.setContentLength(os.size());

                s3Client.putObject(new PutObjectRequest(bucket, uploadPath + "/s_" + uploadFileName, is, meta)
                        .withCannedAcl(CannedAccessControlList.PublicRead)); // 썸네일 저장
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return map;
    }

    private String getFolder(Long userid) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = userid + "-" + sdf.format(date);
        return str.replace("-", "/");
    }

    private boolean checkImageType(File file) {
        try {
            String contentType = Files.probeContentType(file.toPath());

            return contentType.startsWith("image");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
