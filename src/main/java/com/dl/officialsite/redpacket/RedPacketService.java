package com.dl.officialsite.redpacket;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

/**
 * @ClassName TeamService
 * @Author jackchen
 * @Date 2023/10/21 17:23
 * @Description TODO
 **/
@Service
@Slf4j
public class RedPacketService {

    @Autowired
    private RedPacketRepository redPacketRepository;

    public CloseableHttpClient httpClient = HttpClients.createDefault();

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void updateRedpacketStatus() throws IOException {
        log.info("schedule task begin --------------------- ");
        HttpPost request = new HttpPost("http://api.studio.thegraph.com/proxy/55957/dapp-learning-redpacket/version/latest");
        request.setHeader("Content-Type", "application/json");
        // Define your GraphQL query
        long currentTimeMillis = System.currentTimeMillis();
        String creationTimeGtValue = String.valueOf(currentTimeMillis / 1000 -3600*24*90);

        String graphQL = "\" {" +
                "  redpackets {" +
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

        if (entity != null) {
            String jsonResponse = EntityUtils.toString(entity);
            log.info("response from the graph: "+ jsonResponse);
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonObject data = jsonObject.getAsJsonObject("data");
            JsonArray redpacketsArray = data.getAsJsonArray("redpackets");

          // log.info("redpacket array : " + redpacketsArray.get(0));
            List<RedPacket> redPacketList = redPacketRepository.findByStatus(0);

            for (int i = 0; i < redpacketsArray.size(); i++) {
                // Access each element in the array
                JsonObject redpacketObject = redpacketsArray.get(i).getAsJsonObject();
                 // log.info("redpackt object : " + redpacketObject);
                String id = redpacketObject.get("id").getAsString();
                for (int j = 0; j < redPacketList.size(); j++) {

                    RedPacket redPacket = redPacketList.get(j);
                   // log.info("redPacket: " + redPacket);
                    if (!redPacketList.get(j).getId().equals(id))
                        continue;


                    JsonArray claimers = redpacketObject.getAsJsonArray("claimers");

                    // redPacket.getAddressList() is the total claimer.
                    if (claimers.size() == redPacket.getAddressList().size()) {
                        log.info("redpacket id: " + id + " aLL claimed  ");
                        redPacket.setStatus(1);
                        redPacketRepository.save(redPacket);
                        continue;
                    }


                    if (claimers.size() > redPacket.getClaimedAddress().size()) {
                        ArrayList<String> claimersList = new ArrayList<>();

                        //todo
                        for (int k = 0; k < claimers.size(); k++) {
                            String s = claimers.get(k).getAsJsonObject().get("claimer").getAsString();
                            //log.info("claimer address: " + s);
                            claimersList.add(s);
                        }
                        redPacket.setClaimedAddress(claimersList);
                        log.info("update claimed address : " + id);
                        redPacketRepository.save(redPacket);
                    }

                  //refund
               Boolean claimed = redpacketObject.get("hasRefundedOrAllClaimed").getAsBoolean();
                    if (claimed) {
                        log.info("redpacket id: " + id + "claimed: ");
                        redPacket.setStatus(1);
                        redPacketRepository.save(redPacket);
                    }
                }
            }
        }
    }
}





