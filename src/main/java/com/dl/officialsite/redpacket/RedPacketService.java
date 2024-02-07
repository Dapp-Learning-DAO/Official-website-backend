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
import java.util.*;

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

   @Scheduled(cron =  "${jobs.redpacket.corn:0/10 * * * * ?}")
   public void updateRedpacketStatus()  {
        log.info("schedule task begin --------------------- ");
        for (String chainId : chainConfig.getIds()) {
            try {
                updateRedpacketStatusByChainId(chainId);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("updateRedpacketStatusByChainId:  " + chainId + " error:"+ e.getMessage());
            }
        }
    }

    private void updateRedpacketStatusByChainId(String chainId) throws IOException {
        HttpEntity entity = getHttpEntityFromChain(chainId);
        if (entity != null) {
            String jsonResponse = EntityUtils.toString(entity);

            if (jsonResponse.contains("errors")) {
                log.info("response from the graph: chainId{}, data {} ", chainId, jsonResponse);
                return;
            }
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonObject data = jsonObject.getAsJsonObject("data");
            JsonArray redpacketsArray = data.getAsJsonArray("redpackets");
            JsonArray lastupdatesArray = data.getAsJsonArray("lastupdates");
            log.debug("lastupdatesArray"+ lastupdatesArray.toString());

    // todo
//            if(lastupdatesArray.size() != 0){
//                String lastTimestampFromGraph = lastupdatesArray.get(0).getAsJsonObject().get("lastupdateTimestamp").getAsString();
//
//                if(Objects.equals(lastTimestampFromGraph, lastUpdateTimestampMap.get(chainId))){
//                    log.debug("chainId "+ chainId + "no event update");
//                    return ;
//                } else {
//                    lastUpdateTimestampMap.put( chainId, lastTimestampFromGraph);
//                    log.debug("chainId "+ chainId + "set new  event update: "+ lastTimestampFromGraph );
//                }
//            }

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

                    if(redPacket.getStatus() == null) {
                        log.info(redPacket.getName() + "****** upchain successfully" );
                        redPacket.setStatus(0);
                    }
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

    private HttpEntity getHttpEntityFromChain(String chainId) throws IOException {
        HttpPost request = null;
       switch (chainId) {
           case Constants.CHAIN_ID_OP:  // op
               request = new HttpPost("https://api.studio.thegraph.com/query/64274/optimism/version/latest");
               break;
           case Constants.CHAIN_ID_SEPOLIA: //sepolia
               request = new HttpPost("https://api.studio.thegraph.com/query/64274/sepolia/version/latest");
               break;
           case Constants.CHAIN_ID_SCROLL: //scrool
               request = new HttpPost("https://api.studio.thegraph.com/query/64274/scroll/version/latest");
               break;
           case Constants.CHAIN_ID_ARBITRUM: //arbitrum
               request = new HttpPost("https://api.studio.thegraph.com/query/64274/arbitrum/version/latest");
               break;

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





