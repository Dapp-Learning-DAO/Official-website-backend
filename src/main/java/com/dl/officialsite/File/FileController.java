package com.dl.officialsite.File;

import cn.hutool.core.io.IoUtil;
import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.ipfs.IPFSService;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
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
    public BaseResponse upload(@RequestParam("file") MultipartFile file,
        @RequestParam String address) {
        try {
            String hash = ipfsService.upload(file.getBytes());
            return BaseResponse.successWithData(hash);
        } catch (IOException e) {
            log.error("文件上传失败{}", file.getName());
            throw new BizException(CodeEnums.FAIL_UPLOAD_FAIL.getCode(),
                CodeEnums.FAIL_UPLOAD_FAIL.getMsg());
        }
    }

    /**
     * 文件下载
     */
    @GetMapping("/download")
    public void download(@RequestParam String fileHash,
        @RequestParam String address, HttpServletResponse response)
        throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = ipfsService.downloadStream(fileHash);
        } catch (IOException e) {
            log.error("文件下载失败{}", fileHash);
            throw new BizException(CodeEnums.FAIL_DOWNLOAD_FAIL.getCode(),
                CodeEnums.FAIL_DOWNLOAD_FAIL.getMsg());
        }
        response.setContentType("application/octet-stream");
        IoUtil.copy(inputStream, response.getOutputStream());
    }
}
