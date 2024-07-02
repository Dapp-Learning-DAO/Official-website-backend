package com.dl.officialsite.defi.handler;

import cn.hutool.json.JSONObject;
import com.dl.officialsite.defi.entity.WhaleProtocol;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName CompoundWhaleProtocolHandler
 * @Author jackchen
 * @Date 2024/4/22 22:59
 * @Description compound whale protocol handler
 **/
@Service
@Slf4j
public class CompoundWhaleProtocolHandler extends AbstractWhaleProtocolHandler{

    @Override
    public void initWhaleProtocol() {

    }

    @Override
    public void insertWhaleProtocol(List<WhaleProtocol> insertWhaleProtocolList) {

    }

    @Override
    public WhaleProtocol convertToWhaleProtocol(JSONObject account) {
        return null;
    }
}
