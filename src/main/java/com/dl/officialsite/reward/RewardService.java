package com.dl.officialsite.reward;


import com.dl.officialsite.sharing.Share;
import com.dl.officialsite.sharing.SharingRepository;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TeamService
 * @Author jackchen
 * @Date 2023/10/21 17:23
 * @Description TODO
 **/
@Service
@Slf4j
public class RewardService {

    @Autowired
    private RewardRepository rewardRepository;


    @Autowired
    private SharingRepository sharingRepository;

    public CloseableHttpClient httpClient = HttpClients.createDefault();

    //@Scheduled(cron = "0 0/2 * * * ? ")
    public void updateRedpacketStatus() throws IOException {
        log.info("schedule task begin --------------------- ");
        for (String chainId : new String[]{"10", "11155111"}) {
            HttpEntity entity = getHttpEntityFromChain(chainId);
            if (entity != null) {
                String jsonResponse = EntityUtils.toString(entity);
                log.info("response from the graph: chainId " + chainId + jsonResponse.substring(0, 20));
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                JsonObject data = jsonObject.getAsJsonObject("data");
                JsonArray redpacketsArray = data.getAsJsonArray("redpackets");
                //todo get the uncompleted reward from db
                List<Reward> reward = rewardRepository.findByStatus(0);
                for(Reward reward1 : reward) {

                    List<Share> unClaimShareList = sharingRepository.findByIdInAndRewardStatus(reward1.getSharingIds(), 1);
                    for (int i = 0; i < redpacketsArray.size(); i++) {
                        // Access each element in the array
                        JsonObject redpacketObject = redpacketsArray.get(i).getAsJsonObject();
                        String id = redpacketObject.get("id").getAsString();
                        //get address
                        //todo
                        for (int j = 0; j < unClaimShareList.size(); j++) {
                            Share share = unClaimShareList.get(j);
                            if (share.getMemberAddress() == id) {
                                share.setRewardStatus(1);
                            }

                            sharingRepository.save(share);
                        }
                    }
                }
            }
        }
    }

    private HttpEntity getHttpEntityFromChain(String chainId) throws IOException {
        HttpPost request = null;
       switch (chainId) {
           case "10":  // op
               request = new HttpPost("http://api.studio.thegraph.com/proxy/55957/dapp-learning-redpacket/version/latest");
               break;
           case "11155111": //sepolia
               request = new HttpPost("https://api.studio.thegraph.com/query/55957/redpacket-/version/latest");
       }

        request.setHeader("Content-Type", "application/json");
        // Define your GraphQL query
        long currentTimeMillis = System.currentTimeMillis();
        String creationTimeGtValue = String.valueOf(currentTimeMillis / 1000 - 3600*24*90);

        String graphQL = "\" {" +
                "  redpackets (where: { creationTime_gt: "+  creationTimeGtValue + " }) {" +
                "    id     " +
                "    hasRefundedOrAllClaimed   " +
                "     claimers {" +
                "      claimer" +
                "    }" +
                "  }" +
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


//    @Scheduled(cron = "0 0/5 * * * ? ")
//    public void syncNotSavingDBRedpacket() throws IOException {
//        log.info("schedule task begin --------------------- ");
//        HttpPost request = new HttpPost("http://api.studio.thegraph.com/proxy/55957/dapp-learning-redpacket/version/latest");
//        request.setHeader("Content-Type", "application/json");
//        // Define your GraphQL query
//        long currentTimeMillis = System.currentTimeMillis();
//        String creationTimeGtValue = String.valueOf(currentTimeMillis / 1000 - 3600*24*7);
//
//        String graphQL = "\" {" +
//                "  redpackets (where: { creationTime_gt: "+  creationTimeGtValue + " }) {" +
//                "    id     " +
//                "    hasRefundedOrAllClaimed   " +
//                "     claimers {" +
//                "      claimer" +
//                "    }" +
//                "  }" +
//                "}\"";
//
//
//        String query = "{ \"query\": " +
//                graphQL +
//                " }";
//
//        request.setEntity(new StringEntity(query));
//        HttpResponse response = httpClient.execute(request);
//        HttpEntity entity = response.getEntity();
//
//        if (entity != null) {
//            String jsonResponse = EntityUtils.toString(entity);
//            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
//            JsonObject data = jsonObject.getAsJsonObject("data");
//            JsonArray redpacketsArray = data.getAsJsonArray("redpackets");
//
//            // log.info("redpacket array : " + redpacketsArray.get(0));
//            List<RedPacket> redPacketList = redPacketRepository.findAll();
//
//            for (int i = 0; i < redpacketsArray.size(); i++) {
//
//                JsonObject redpacketObject = redpacketsArray.get(i).getAsJsonObject();
//                String id = redpacketObject.get("id").getAsString();
//                for (int j = 0; j < redPacketList.size(); j++) {
//
//                    RedPacket redPacket = redPacketList.get(j);
//                    if (redPacketList.get(j).getId().toLowerCase().equals(id.toLowerCase()))
//                        continue;
//                    todo
//                    //redPacketRepository.save(redPacket);
//
//                }
//            }
//        }
//    }
}





