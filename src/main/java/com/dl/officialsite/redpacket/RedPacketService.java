package com.dl.officialsite.redpacket;


import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.config.ChainConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public CloseableHttpClient httpClient = HttpClients.createDefault();

    private String lastUpdateTimestamp= "";

   @Scheduled(cron =  "${jobs.redpacket.corn:0/10 * * * * ?}")
   public void updateRedpacketStatus() throws IOException {
        log.info("schedule task begin --------------------- ");
        for (String chainId : chainConfig.getIds()) {
            log.info("chain_id " + chainId);
            HttpEntity entity = getHttpEntityFromChain(chainId);
            if (entity != null) {
                String jsonResponse = EntityUtils.toString(entity);
                log.info("response from the graph: chainId{}, data {} ", chainId ,jsonResponse);
                if (jsonResponse.contains("errors")) {
                    return;
                }
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                JsonObject data = jsonObject.getAsJsonObject("data");
                JsonArray redpacketsArray = data.getAsJsonArray("redpackets");
                JsonArray lastupdatesArray = data.getAsJsonArray("lastupdates");
         //     String lastTimestampFromGraph = lastupdatesArray.get(0).getAsJsonObject().get("lastupdateTimestamp").getAsString();

                // open in prod todo 
//                if(Objects.equals(lastTimestampFromGraph, lastUpdateTimestamp)){
//                    return;
//                } else {
//                    lastUpdateTimestamp = lastTimestampFromGraph;
//                }


                List<RedPacket> redPacketList = redPacketRepository.findUnfinishedRedpacketByChainId(chainId);
                   log.info("redPacketList size " + redPacketList.size());
                for (int i = 0; i < redpacketsArray.size(); i++) {
                    // Access each element in the array
                    JsonObject redpacketObject = redpacketsArray.get(i).getAsJsonObject();

                    String id = redpacketObject.get("id").getAsString();
                    for (int j = 0; j < redPacketList.size(); j++) {
                        RedPacket redPacket = redPacketList.get(j);

                        if (!Objects.equals(redPacket.getId(), id)) {
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
                        log.info("****** refunded"+ refunded);
                        log.info("****** allClaimed"+ allClaimed);
                        if( redPacket.getExpireTime()< System.currentTimeMillis()/1000){
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
    }

    private HttpEntity getHttpEntityFromChain(String chainId) throws IOException {
        HttpPost request = null;
       switch (chainId) {
           case Constants.CHAIN_ID_OP:  // op
               request = new HttpPost("http://api.studio.thegraph.com/proxy/55957/dapp-learning-redpacket/version/latest");
               break;
           case Constants.CHAIN_ID_SEPOLIA: //sepolia
               request = new HttpPost("https://api.studio.thegraph.com/query/55957/redpacket-/version/latest");
       }

        request.setHeader("Content-Type", "application/json");
        // Define your GraphQL query
        long currentTimeMillis = System.currentTimeMillis();
        long time = currentTimeMillis / 1000 - 3600*24*90;
       // time = Math.max(time, 1703751860);
        String creationTimeGtValue = String.valueOf(time);


        String graphQL = "\" {" +
                "  redpackets (where: { creationTime_gt: "+  creationTimeGtValue + " }) {" +
                "    id     " +
                "    refunded   " +
                "   lock " +
                "    name       " +
                "    creationTime   " +
                "    allClaimed  " +
                "    claimers {" +
                "    claimer" +
                "    claimedValue " +
                "    }" +
                " }" +
                "  lastupdates (orderBy : lastupdateTimestamp , orderDirection: desc) { lastupdateTimestamp } " +

                "}\"";


        String query = "{ \"query\": " +
                graphQL +
                " }";

        request.setEntity(new StringEntity(query));
        HttpResponse response = httpClient.execute(request);
        // System.out.println("response" + response);
        HttpEntity entity = response.getEntity();
        return entity;
    }
}





