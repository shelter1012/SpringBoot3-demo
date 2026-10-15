package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    @Value("${file.upload-path}")
    private String uploadPath;

    @Value("${file.access-prefix}")
    private String accessPrefix;

    @PostMapping("/upload")
    public Map<String,Object> upload(@RequestParam("file") MultipartFile file) {
        Map<String,Object> result = new HashMap<>();

        if(file.isEmpty()){
            result.put("code","A0400");
            result.put("msg","文件为空");
            return result;
        }

        try{
//            1.确保目录存在
            File dir = new File(uploadPath);
            if(!dir.exists()) {
                dir.mkdirs();
            }

//            2.生成唯一文件名(UUID+原拓展名)
            String originalName = file.getOriginalFilename();
            String ext = "";
            if (originalName != null && originalName.contains(".")) {
                ext = originalName.substring(originalName.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString().replace("-", "")+ext;

//            3.保存文件
            File dest = new File(uploadPath + File.separator + fileName);
            file.transferTo(dest);

//            4.返回访问URL
            String url = accessPrefix + "/" + fileName;

            result.put("code","00000");
            result.put("msg","上传成功");
            result.put("url",url);
            return result;
        } catch (Exception e){
            e.printStackTrace();
            result.put("code","A0500");
            result.put("msg","上传失败" + e.getMessage());
            return result;
        }
    }
}
