package com.buysell.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.buysell.domain.DTO.BoardFileDTO;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileService {
    private final AmazonS3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${spring.uploadFolderPath}")
    private String uploadFolder;

    public List<BoardFileDTO> upload(MultipartFile[] uploadFile, Long uid) throws IOException {
        List<BoardFileDTO> list = new ArrayList<>();

        String uploadPath = getFolder(uid); // 파일 경로
        File uploadFolderPath = new File(uploadFolder, uploadPath);

        if (!uploadFolderPath.exists()){
            uploadFolderPath.mkdirs();
        }

        for (MultipartFile multipartFile : uploadFile){
            BoardFileDTO fileDTO = new BoardFileDTO();

            String uploadFileName = multipartFile.getOriginalFilename(); // 이름 원본

            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
            fileDTO.setFileName(uploadFileName); // 원본 이름 저장

            UUID uuid = UUID.randomUUID();

            uploadFileName = uuid.toString() + "_" + uploadFileName; // uuid + 이름 원본

            File saveFile = new File(uploadFolderPath, uploadFileName);

            if (checkImageType(saveFile)) {
                fileDTO.setUploadPath(uploadPath);
                fileDTO.setUuid(uuid.toString());
                fileDTO.setImage(true);

                if (bucket.equals("local")) { // 개발 환경
                    multipartFile.transferTo(saveFile);
                    fileDTO.setImage(true);

                    FileOutputStream thumbnail = new FileOutputStream(new File(uploadFolderPath, "s_" + uploadFileName));
                    Thumbnails.of(saveFile)
                            .size(480, 600)
                            .outputFormat("jpg")
                            .toOutputStream(thumbnail);

                    thumbnail.close();
                } else { // 배포 환경
                    // 메타 데이터 저장
                    ObjectMetadata meta = new ObjectMetadata();
                    meta.setContentType(multipartFile.getContentType());
                    meta.setContentLength(multipartFile.getSize()); // 더 많은 데이터를 기다리는 것을 방지하기위해 사이즈 지정

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

            }
            list.add(fileDTO);
        }

        return list;
    }

    public Map<String, Object> uploadProfile(MultipartFile uploadFile, Long uid){
        Map<String, Object> map = new HashMap<>();

        String uploadPath = uid + "/profile"; // 개인 추가 업로드 경로
        File uploadFolderPath = new File(uploadFolder, uploadPath);

        if (!uploadFolderPath.exists()){
            uploadFolderPath.mkdirs();
        }

        String uploadFileName = uploadFile.getOriginalFilename();

        // IE file path
        uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);

        try{
            File saveFile = new File(uploadFolderPath, uploadFileName);

            if (checkImageType(saveFile)){

                map.put("uploadPath", uploadPath);
                map.put("fileName", uploadFileName);
                map.put("image", true);

                if (bucket.equals("local")) { // 로컬 환경
                    uploadFile.transferTo(saveFile);
                    FileOutputStream thumbnail = new FileOutputStream(new File(uploadFolderPath, "s_" + uploadFileName));
                    Thumbnails.of(saveFile)
                            .size(64, 64)
                            .outputFormat("jpg")
                            .toOutputStream(thumbnail);

                    thumbnail.close();
                } else { // 배포 환경
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
