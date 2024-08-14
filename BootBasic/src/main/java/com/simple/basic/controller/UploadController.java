package com.simple.basic.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/fileupload")
public class UploadController {
    @Value("${project.uploadPath}")
    private String uploadPath;

    // 폴더 생성 함수
    public String makeFolder() {
        String filepath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));

        File file = new File(uploadPath + filepath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return filepath + "/";
    }

    @GetMapping("/upload")
    public String uploadView() {
        return "fileupload/upload";
    }

    @PostMapping("/uploadOk")
    public String uploadOk(@RequestParam("file") MultipartFile file) {
        // 1. 브라우저에 따라 사용자의 풀경로가 제목에 포함되는 경우가 있다.
        // 2. 동일 파일 대한 처리, 동일 이름이 올라오면 파일이 덮어씌워진다.
        // 3. 1개 폴더에 저장가능한 파일 수는 4~6 만 개 (월별 폴더 생성해 처리)

        String originName = file.getOriginalFilename();
        String fileName = originName.substring(originName.lastIndexOf("\\") + 1);  // 파일명

        String filepath = makeFolder(); // 폴더명

        String uuid = UUID.randomUUID().toString();

        String savePath = uploadPath + filepath + uuid + "_" + fileName; // 파일명 포함 경로

        System.out.println(savePath);

        try {
            file.transferTo(new File(savePath));

            // fileName, filePath, uuid 디비에 저장

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "fileupload/upload_ok";
    }

    // 다중 파일 업로드
    @PostMapping("/uploadOk2")
    public String uploadOk2(MultipartHttpServletRequest files) {
        List<MultipartFile> fileList = files.getFiles("file"); // 폼태그 name 값

        for (MultipartFile file : fileList) {
            String fileName = file.getOriginalFilename();
            String filepath = makeFolder();
            String uuid = UUID.randomUUID().toString();
            String savePath = uploadPath + filepath + uuid + "_" + fileName; // 파일명 포함 경로
            try {
                file.transferTo(new File(savePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return "fileupload/upload_ok";
    }

    // 복수 태그로 여러파일 업로드
    @PostMapping("/uploadOk3")
    public String uploadOk3(@RequestParam("file") List<MultipartFile> list) {
        list = list.stream().filter(a -> !a.isEmpty()).collect(Collectors.toList());
        for (MultipartFile file : list) {
            String fileName = file.getOriginalFilename();
            String filepath = makeFolder();
            String uuid = UUID.randomUUID().toString();
            String savePath = uploadPath + filepath + uuid + "_" + fileName; // 파일명 포함 경로
            try {
                file.transferTo(new File(savePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "fileupload/upload_ok";
    }

    // 비동기 업로드
    @PostMapping("/uploadOk4")
    @ResponseBody
    public String uploadOk4(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filepath = makeFolder();
        String uuid = UUID.randomUUID().toString();
        String savePath = uploadPath + filepath + uuid + "_" + fileName; // 파일명 포함 경로
        System.out.println(savePath);
        try {
            file.transferTo(new File(savePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "success";
    }
}
