package com.dl.officialsite.redpacket;


import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.config.ChainConfig;
import com.dl.officialsite.redpacket.config.RedPacketConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xxl.job.core.context.XxlJobHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * @ClassName TeamService
 * @Author jackchen
 * @Date 2023/10/21 17:23
 * @Description TODO
 **/
@Service
@Slf4j(topic = "RedPacket")
@Configuration
public class RedPacketService {

    @Autowired
    private RedPacketRepository redPacketRepository;

    @Autowired
    private ChainConfig chainConfig;

    @Resource
    private RedPacketConfig redPacketConfig;

    public CloseableHttpClient httpClient = HttpClients.createDefault();

    public void updateRedpacketStatus() {
        XxlJobHelper.log("RedPacketService updateRedPacketStatus start");
        for (String chainId : chainConfig.getIds()) {
            try {
                updateRedpacketStatusByChainId(chainId);
            } catch (Exception e) {
                XxlJobHelper.log("updateRedPacketStatusByChainId:  " + chainId + " error:" + e.getMessage());
            }
        }
    }

    private void updateRedpacketStatusByChainId(String chainId) throws IOException {
        HttpEntity entity = getHttpEntityFromChain(chainId);
        if (entity != null) {
            String jsonResponse = EntityUtils.toString(entity);

            if (jsonResponse.contains("errors")) {
                XxlJobHelper.log("response from the graph: chainId{}, data {} ", chainId, jsonResponse);
                return;
            }
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonObject data = jsonObject.getAsJsonObject("data");
            JsonArray redpacketsArray = data.getAsJsonArray("Redpacket");
            JsonArray lastupdatesArray = data.getAsJsonArray("Lastupdate");
            log.debug("lastupdatesArray" + lastupdatesArray.toString());

            List<RedPacket> redPacketList = redPacketRepository.findUnfinishedRedpacketByChainId(chainId);

            for (int i = 0; i < redpacketsArray.size(); i++) {
                // Access each element in the array
                JsonObject redpacketObject = redpacketsArray.get(i).getAsJsonObject();

                String id = redpacketObject.get("id").getAsString();
                for (int j = 0; j < redPacketList.size(); j++) {
                    RedPacket redPacket = redPacketList.get(j);

                    if (!redPacket.getId().equals(id)) {
                        continue;
                    }

                    JsonArray claimers = redpacketObject.getAsJsonArray("claimers");
                    ArrayList<String> claimersList = new ArrayList<>();
                    ArrayList<String> claimedValueList = new ArrayList<>();
                    for (int k = 0; k < claimers.size(); k++) {
                        String claimer = claimers.get(k).getAsJsonObject().get("claimer").getAsString();
                        String value = claimers.get(k).getAsJsonObject().get("claimedValue").getAsString();
                        claimersList.add(claimer);
                        claimedValueList.add(value);

                    }
                    redPacket.setClaimedAddress(claimersList);
                    redPacket.setClaimedValues(claimedValueList);

                    ////0 uncompleted  1 completed    2 overtime 3 refund
                    Boolean allClaimed = redpacketObject.get("allClaimed").getAsBoolean();
                    Boolean refunded = redpacketObject.get("refunded").getAsBoolean();
//                    log.info("****** refunded"+ refunded);
//                    log.info("****** allClaimed"+ allClaimed);

                    if (redPacket.getStatus() == null) {
                        log.info(redPacket.getName() + "****** upchain successfully");
                        redPacket.setStatus(0);
                    }
                    if (redPacket.getExpireTime() < System.currentTimeMillis() / 1000) {
                        redPacket.setStatus(2);
                    }
                    if (allClaimed) {
                        redPacket.setStatus(1);
                    }
                    if (refunded) {
                        redPacket.setStatus(3);
                    }


                    redPacketRepository.save(redPacket);
                }
            }
        }

    }

    private HttpEntity getHttpEntityFromChain(String chainId) throws IOException {
        HttpPost request = null;
        switch (chainId) {
            case Constants.CHAIN_ID_OP:  // op
                request = new HttpPost("https://indexer.dev.hyperindex.xyz/31816b0/v1/graphql");
                break;
            case Constants.CHAIN_ID_SEPOLIA: //sepolia
                request = new HttpPost(
                    "https://indexer.dev.hyperindex.xyz/616a894/v1/graphql");
                break;
/*            case Constants.CHAIN_ID_SCROLL: //scrool
                request = new HttpPost(
                    "https://indexer.bigdevenergy.link/d3f3a96/v1/graphql");
                break;
            case Constants.CHAIN_ID_ARBITRUM: //arbitrum
                request = new HttpPost(
                    "https://indexer.bigdevenergy.link/40a09e1/v1/graphql");
                break;
            case Constants.CHAIN_ID_ZKSYNC: //zksync
                request = new HttpPost(
                    "https://indexer.bigdevenergy.link/93a81ef/v1/graphql");
                break;*/
/*            case Constants.CHAIN_ID_POLYGON_ZKEVM: //polygon zkevm
                request = new HttpPost(
                    "https://gateway-arbitrum.network.thegraph.com/api/" + redPacketConfig.getGraphConfig().getKey()  +"/subgraphs/id/5DsufFHE6P7tK7QT1mGCPrQEKURD2E1SSHAyCSzf5CAg");
                break;
            case Constants.CHAIN_ID_LINEA: //linea
                request = new HttpPost(
                    "https://gateway-arbitrum.network.thegraph.com/api/" + redPacketConfig.getGraphConfig().getKey()  +"/subgraphs/id/958YHrTJturbhR6uwRyPu1wmBNiXivNkLMPY7tUiL4wD");
                break;*/

        }
        request.setHeader("Content-Type", "application/json");
        // Define your GraphQL query
        long currentTimeMillis = System.currentTimeMillis();
        long time = currentTimeMillis / 1000 - 3600 * 24 * 90;
        // time = Math.max(time, 1703751860);
        String creationTimeGtValue = String.valueOf(time);


        String graphQL = "{\n"
            + "    \"query\": \"{\\n  Redpacket(\\n    where: {creationTime: {_gt: \\\"" + creationTimeGtValue + "\\\"}}\\n    order_by: {creationTime: desc}\\n  ) {\\n    id\\n    refunded\\n    lock\\n    name\\n    creationTime\\n    allClaimed\\n    claimers {\\n      claimer\\n      claimedValue\\n    }\\n  }\\n  Lastupdate(order_by: {lastupdateTimestamp: asc}) {\\n    lastupdateTimestamp\\n  }\\n}\"\n"
            + "}";


        request.setEntity(new StringEntity(graphQL));
        HttpResponse response = httpClient.execute(request);
        // System.out.println("response" + response);
        HttpEntity entity = response.getEntity();
        return entity;
    }
}





