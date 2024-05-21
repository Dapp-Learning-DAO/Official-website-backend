package com.dl.officialsite.nft.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RankExtractorUtil {

    private static final Logger log = LoggerFactory.getLogger(RankExtractorUtil.class);

    public static int extractParameterValueFromUrl(String url, String targetParam) throws URISyntaxException {
        URI uri = new URI(url);
        List<NameValuePair> params = URLEncodedUtils.parse(uri, StandardCharsets.UTF_8);

        for (NameValuePair param : params) {
            if (param.getName().equals(targetParam)) {
                return Integer.parseInt(param.getValue());
            }
        }

        log.error("Value not found for param:[{}]", targetParam);
        return -1;
    }


}