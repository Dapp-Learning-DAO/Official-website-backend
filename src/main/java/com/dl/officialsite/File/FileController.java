package com.dl.officialsite.File;

import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.ipfs.IPFSService;
import java.io.IOException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName FileController
 * @Author jackchen
 * @Date 2023/10/16 17:07
 * @Description 文件上传
 **/
@RestController
@RequestMapping("/file")
@Data
@Slf4j
public class FileController {

    private final IPFSService ipfsService;

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public BaseResponse upload(@RequestParam("file") MultipartFile file) {
        try {
            String hash = ipfsService.upload(file.getBytes());
            return BaseResponse.successWithData(hash);
        } catch (IOException e) {
            log.error("文件上传失败{}", file.getName());
            throw new RuntimeException(e);
        }
    }

    /**
     * 文件下载
     */
    @GetMapping("/download")
    public BaseResponse download(String fileHahs) {
        byte[] download = ipfsService.download(fileHahs);
        if (ObjectUtils.isEmpty(download)) {
            return BaseResponse.failWithReason("1001", "文件下载失败");
        }
        return BaseResponse.successWithData(download);
    }
}
