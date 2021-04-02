package com.jt.service;

import com.jt.vo.ImageVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@PropertySource(value = "classpath:/properties/Image.properties",encoding = "utf-8")
public class FileServiceImpl implements FileService{

    //设定图片上传路径
    @Value("${image.fileDir}")
    private String ImagePathDir ; //"D:/Java/jt-image/";

    //设定域名地址
    @Value("${image.urlPath}")
    private String urlPath ;

    public static Set<String> imageSet = new HashSet<>();
    static{
        imageSet.add(".jpg");
        imageSet.add(".png");
        imageSet.add(".gif");
    }

    /**
     * 1.图片校验
     * 2.恶意软件校验
     * 3.应该采用分目录存储的方式     保存数据
     * 4.上传的文件名称应该尽量避免重名  自定义文件名称...  UUID.后缀...
     * @param uploadFile
     * @return
     */
    @Override
    public ImageVO upload(MultipartFile uploadFile) {


        //1.图片校验
        String ImageName = uploadFile.getOriginalFilename();
        ImageName = ImageName.toLowerCase();
        int Index = ImageName.lastIndexOf(".");
        String fileType = ImageName.substring(Index);
        if(!imageSet.contains(fileType))
            return ImageVO.fail();

        //2.恶意软件校验
        //获取图片宽高并对比
        try {
            BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if(width == 0 && height == 0){
                return ImageVO.fail();
            }

            //3.分目录试储存---以日期格式分目录
            String DateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            String pathDir = ImagePathDir + DateDir;
            File file = new File(pathDir);
            if(!file.exists()){
                file.mkdirs();
            }

            //4.图片重命名
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String newName = pathDir+"/"+uuid+fileType;

            //文件上传
            File realFile = new File(newName);
            uploadFile.transferTo(realFile);

            String url = urlPath+DateDir+"/"+uuid+fileType;
            System.out.println(url);

            return ImageVO.success(url,width,height);
        } catch (IOException e) {
            e.printStackTrace();
            return ImageVO.fail();
        }
    }
}
