package com.dl.officialsite.sharing.manager;

import com.dl.officialsite.sharing.constant.SharingPostType;
import com.dl.officialsite.sharing.handler.IPostHandler;
import com.dl.officialsite.sharing.model.bo.PostGenerationInput;
import com.dl.officialsite.sharing.model.req.PostGenerateReq;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 海报处理器
 */
@Component
public class PostImageManager {

    private Map<SharingPostType, IPostHandler> handlers;

    @Autowired
    public PostImageManager(List<IPostHandler> handlers){
        this.handlers = new HashMap<>();

        for(IPostHandler handler: handlers){
            this.handlers.put(handler.postType(), handler);
        }
    }

    public void generateTemplate(PostGenerateReq req){
        SharingPostType sharingPostType = SharingPostType.codeOf(req.getPostType());
        IPostHandler postHandler = this.handlers.get(sharingPostType);
        Preconditions.checkState(postHandler !=null, "post type not found");

        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            postHandler.generateOutput(req.getInput(), baos);
        } catch (Throwable ex)
        {
            throw new RuntimeException(ex);
        }

    }

}
