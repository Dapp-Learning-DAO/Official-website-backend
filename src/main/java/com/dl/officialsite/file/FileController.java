package com.dl.officialsite.file;//package com.dl.officialsite.file;

import cn.hutool.core.io.IoUtil;
import com.dl.officialsite.common.base.BaseResponse;
import com.dl.officialsite.file.cos.FileService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

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

    private final FileService fileService;

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public BaseResponse upload(@RequestParam(required = false) MultipartFile file,
        @RequestParam String address) {
        String hash = fileService.upload(file);
        return BaseResponse.successWithData(hash);
    }

    /**
     * todo
     */
    @GetMapping("/download")
    public void download(@RequestParam String fileHash,
        @RequestParam String address, HttpServletResponse response)
        throws IOException {
        InputStream inputStream = null;

        inputStream = fileService.download(fileHash);

        response.setContentType("application/octet-stream");
        IoUtil.copy(inputStream, response.getOutputStream());
    }
}
