package com.dl.officialsite.sharing.management.handler;

import com.dl.officialsite.sharing.constant.SharingPostType;
import com.dl.officialsite.sharing.model.bo.PostClassicalGeneratorInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Component
@Slf4j
public class PostClassicHandler implements IPostHandler<PostClassicalGeneratorInput>{
    @Override
    public void generateOutput(PostClassicalGeneratorInput input, OutputStream os) throws IOException {
        /**
         * 加载模板
         */
        ClassPathResource classPathResource = new ClassPathResource("post/classic.png");
        try(InputStream ins = classPathResource.getInputStream()){
            BufferedImage image = ImageIO.read(ins);
            log.info("image load finished");
        }


        /**
         * 画右上角组织图标
         */

        /**
         * Presenter
         */

        /**
         * 
         */
    }

    @Override
    public SharingPostType postType() {
        return SharingPostType.CLASSIC;
    }
}
