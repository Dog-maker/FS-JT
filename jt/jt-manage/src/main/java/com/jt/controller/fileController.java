package com.jt.controller;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class fileController {


    @RequestMapping("/file")
    public String file(MultipartFile fileImage) throws IOException {
        String name = fileImage.getOriginalFilename();
        File fileDir = new File("D:/Java/jt-massage");
        if(!fileDir.exists()){
            fileDir.mkdir();
        }
        String FilePath = fileDir.getPath()+"/"+name;
        File newFile = new File(FilePath);
        fileImage.transferTo(newFile);
        return "文件上传成功";
    }

    /**
     * 业务:实现商品的文件上传操作
     * url地址: http://localhost:8091/pic/upload?dir=image
     * 参数:    uploadFile   注意字母的大小写
     * 返回值结果: ImageVO对象.
     */
    @Autowired
    private FileService fileService;

    @RequestMapping("/pic/upload")
    public ImageVO upload(MultipartFile uploadFile){

        //将所有的业务操作,放到Service层中完成!!!
        return fileService.upload(uploadFile);
    }


}
