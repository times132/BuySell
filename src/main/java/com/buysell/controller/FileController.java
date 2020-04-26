package com.buysell.controller;

import com.buysell.domain.DTO.BoardFileDTO;
import com.buysell.security.CustomUserDetails;
import com.buysell.service.UserService;
import lombok.AllArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@AllArgsConstructor
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private UserService userService;

    private String getFolder(Long userid) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = userid + "-" + sdf.format(date);
        return str.replace("-", File.separator);
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

    @GetMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> fileGET(String fileName) {
        logger.info("-----File fileGET-----");

        File file = new File("D:\\upload\\" + fileName);

        ResponseEntity<byte[]> result = null;

        try {
            HttpHeaders header = new HttpHeaders();

            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        }
        catch (AccessDeniedException e){
            logger.warn("잘못된 접근입니다.");
        }
        catch (NoSuchFileException e){
            logger.warn("사진이 없습니다");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/uploadFile", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<BoardFileDTO>> uploadFilePOST(MultipartFile[] uploadFile, @AuthenticationPrincipal CustomUserDetails user){
        logger.info("-----File uploadFilePOST-----");
        Long userid = user.getUser().getId();
        List<BoardFileDTO> list = new ArrayList<>();
        String uploadFolder = "D:\\upload";

        String uploadFolderPath = getFolder(userid); // 3/2020/03/13
        File uploadPath = new File(uploadFolder, uploadFolderPath);


        if (uploadPath.exists() == false) {
            uploadPath.mkdirs();
        }

        for (MultipartFile multipartFile : uploadFile) {

            logger.info("======================================");
            logger.info("Upload File Name: " + multipartFile.getOriginalFilename());
            logger.info("Upload File Size: " + multipartFile.getSize());

            BoardFileDTO fileDTO = new BoardFileDTO();

            String uploadFileName = multipartFile.getOriginalFilename();

            // IE file path
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
            logger.info("only file name: " + uploadFileName);
            fileDTO.setFileName(uploadFileName);

            UUID uuid = UUID.randomUUID();

            uploadFileName = uuid.toString() + "_" + uploadFileName;

            try {
                File saveFile = new File(uploadPath, uploadFileName);
                multipartFile.transferTo(saveFile);

                fileDTO.setUploadPath(uploadFolderPath);
                fileDTO.setUuid(uuid.toString());

                if (checkImageType(saveFile)) {

                    fileDTO.setImage(true);

                    FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
                    Thumbnails.of(saveFile)
                            .size(480, 600)
                            .outputFormat("jpg")
                            .toOutputStream(thumbnail);

                    thumbnail.close();
                }

                list.add(fileDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/uploadProfile", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> profilePOST(MultipartFile uploadProfile, @AuthenticationPrincipal CustomUserDetails user){
        logger.info("-----User uploadProfile-----");

        Long userid = user.getUser().getId();
        Map<String, Object> map = new HashMap<>();
        String uploadFolder = "D:\\upload\\"; // 공통 업로드 경로
        String uploadFolderPath = userid + "\\profile"; // 개인 추가 업로드 경로

        File uploadPath = new File(uploadFolder, uploadFolderPath);

        if (!uploadPath.exists()){
            uploadPath.mkdirs();
        }

        logger.info("======================================");
        logger.info("Upload File Name: " + uploadProfile.getOriginalFilename());

        String uploadFileName = uploadProfile.getOriginalFilename();

        // IE file path
        uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);

        try{
            File saveFile = new File(uploadPath, uploadFileName);

            if (checkImageType(saveFile)){
                uploadProfile.transferTo(saveFile);

                map.put("uploadPath", uploadFolderPath);
                map.put("fileName", uploadFileName);
                map.put("image", true);

                FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
                Thumbnails.of(saveFile)
                        .size(64, 64)
                        .outputFormat("jpg")
                        .toOutputStream(thumbnail);

                thumbnail.close();
//                userService.uploadProfile(uploadFileName, userid);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/deleteFile")
    @ResponseBody
    public ResponseEntity<String> deleteFilePOST(String fileName, String type){
        logger.info("-----File deleteFilePOST-----");

        File file;

        try {
            file = new File("D:\\upload\\"  + URLDecoder.decode(fileName, "UTF-8"));
            file.delete();

            if (type.equals("image")){
                String originFileName = file.getAbsolutePath().replace("s_", "");
                file = new File(originFileName);
                file.delete();
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>("deleted", HttpStatus.OK);
    }
}
