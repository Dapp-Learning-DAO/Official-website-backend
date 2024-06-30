package com.dl.officialsite.defi.handler;

import cn.hutool.json.JSONObject;
import com.dl.officialsite.defi.entity.Whale;
import com.dl.officialsite.defi.entity.WhaleProtocol;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @ClassName AbstractWhaleProtocolService
 * @Author jackchen
 * @Date 2024/4/22 22:34
 * @Description query whale protocol service
 **/
@Slf4j
public abstract class AbstractWhaleProtocolHandler {

    //request protocol graph
    public String requestProtocolGraph(Integer first, Integer skip,
        String protocolName, String protocolGraphUrl) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String requestBody =
            "{\"query\":\"{\\n  accounts(\\n    where: {positions_: {side: BORROWER, timestampClosed: null, asset_in: [\\\"0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48\\\", \\\"0xdac17f958d2ee523a2206206994597c13d831ec7\\\", \\\"0x6b175474e89094c44da98b954eedeac495271d0f\\\", \\\"0x3Fe6a295459FAe07DF8A0ceCC36F37160FE86AA9\\\"], timestampOpened_gt: 1680942374, timestampOpened_lt: 1712478374}, borrows_: {amountUSD_gt: 20000}}\\n    skip: "
                + skip + "\\n    first: " + first
                + "\\n  ) {\\n    id\\n    positionCount\\n    openPositionCount\\n    positions(where: {timestampClosed: null}) {\\n      id\\n      timestampOpened\\n      timestampClosed\\n      side\\n      isCollateral\\n      type\\n      principal\\n      balance\\n      depositCount\\n      withdrawCount\\n      borrowCount\\n      repayCount\\n      borrows {\\n        hash\\n        amount\\n        amountUSD\\n        timestamp\\n      }\\n      deposits {\\n        hash\\n        amount\\n        amountUSD\\n        timestamp\\n      }\\n      repays {\\n        hash\\n        amount\\n        amountUSD\\n        timestamp\\n      }\\n      asset {\\n        id\\n        symbol\\n        name\\n        decimals\\n      }\\n    }\\n  }\\n}\",\"extensions\":{}}";
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder()
            .url(protocolGraphUrl)
            .post(body)
            .addHeader("Content-Type", "application/json")
            .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            log.error("请求{}的graph失败", protocolName);
            throw new RuntimeException(e);
        }
        ;
        String jsonStr = null;
        try {
            jsonStr = response.body().string();
        } catch (IOException e) {
            log.error("解析{}的graph返回失败", protocolName);
            throw new RuntimeException(e);
        }
        return jsonStr;

    }

    public abstract void initWhaleProtocol();

    public abstract void insertWhaleProtocol(List<WhaleProtocol> insertWhaleProtocolList);

    public abstract WhaleProtocol convertToWhaleProtocol(JSONObject account);

    public Whale convertToWhale(JSONObject account) {
        Whale whale = new Whale();
        String address = account.getStr("id");
        whale.setAddress(address);
        return whale;
    }
}
