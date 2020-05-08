package com.buysell.controller;

import com.buysell.domain.DTO.BoardFileDTO;
import com.buysell.security.CustomUserDetails;
import com.buysell.service.FileService;
import com.buysell.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
    private FileService fileService;

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
    public ResponseEntity<List<BoardFileDTO>> uploadFilePOST(MultipartFile[] uploadFile, @AuthenticationPrincipal CustomUserDetails user) throws IOException {
        logger.info("-----File uploadFilePOST-----");

        return new ResponseEntity<>(fileService.upload(uploadFile, user.getUser().getId()), HttpStatus.OK);
    }

    @PostMapping(value = "/uploadProfile", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> profilePOST(MultipartFile uploadProfile, @AuthenticationPrincipal CustomUserDetails user){
        logger.info("-----User uploadProfile-----");

        Long userid = user.getUser().getId();

        return new ResponseEntity<>(fileService.uploadProfile(uploadProfile, userid), HttpStatus.OK);
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

        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }
}
