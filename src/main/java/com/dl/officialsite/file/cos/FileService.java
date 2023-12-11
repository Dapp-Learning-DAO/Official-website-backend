package com.dl.officialsite.file.cos;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    String key;

    @Autowired
    private COSClient cosClient;
    @Autowired
    private COSProperties cosProperties;

    // 支持的文件类型
    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg");

    public String uploadImage(MultipartFile file) {

        try {
            // 1、图片信息校验
            // 1)校验文件类型
            String type = file.getContentType(); //获取文件格式
            if (!suffixes.contains(type)) {
                // logger.info("上传失败，文件类型不匹配：{}", type);
                return null;
            }
            // 2)校验图片内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                // logger.info("上传失败，文件内容不符合要求");
                return null;
            }

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(type);
            UUID uuid = UUID.randomUUID();
            // 指定要上传到 COS 上对象键 此key是文件唯一标识
            key = uuid.toString().replace("-","")+".jpg";
            PutObjectRequest putObjectRequest = new PutObjectRequest(cosProperties.getBucketName(), key, file.getInputStream(),objectMetadata);

            //使用cosClient调用第三方接口
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            log.info(putObjectRequest+"");
            //返回路径

        }catch (Exception e){
            e.printStackTrace();
        }
        //拼接返回路径
        String imagePath = "https://" + cosProperties.getBucketName() + ".cos." + cosProperties.getRegionName() + ".myqcloud.com/" + key;
        return imagePath;
    }

}
