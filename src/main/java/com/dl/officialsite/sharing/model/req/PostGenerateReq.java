package com.dl.officialsite.sharing.model.req;

import com.dl.officialsite.sharing.model.bo.PostGenerationInput;
import lombok.Data;

@Data
public class PostGenerateReq {
    /**
     * 模板款式，目前只有DL经典款式
     */
    private int postType;

    private PostGenerationInput input;
}
