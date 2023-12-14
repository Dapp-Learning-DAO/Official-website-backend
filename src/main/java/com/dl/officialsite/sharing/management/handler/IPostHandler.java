package com.dl.officialsite.sharing.management.handler;

import com.dl.officialsite.sharing.constant.SharingPostType;
import com.dl.officialsite.sharing.model.bo.PostGenerationInput;

import java.io.IOException;
import java.io.OutputStream;

public interface IPostHandler<T extends PostGenerationInput> {

    void generateOutput(T input, OutputStream os) throws IOException;

    SharingPostType postType();
}
