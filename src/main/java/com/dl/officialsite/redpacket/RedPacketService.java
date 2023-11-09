package com.dl.officialsite.redpacket;


import com.dl.officialsite.common.constants.Constants;
import com.dl.officialsite.common.enums.CodeEnums;
import com.dl.officialsite.common.exception.BizException;
import com.dl.officialsite.mail.EmailService;
import com.dl.officialsite.member.Member;
import com.dl.officialsite.member.MemberRepository;
import com.dl.officialsite.team.Team;
import com.dl.officialsite.team.TeamMember;
import com.dl.officialsite.team.TeamMemberRepository;
import com.dl.officialsite.team.TeamRepository;
import com.dl.officialsite.team.vo.*;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Scheduled(cron = "0 0/3 * * * ? ")
    public void updateRedpacketStatus() throws IOException {
        log.info("schedule task begin --------------------- " );
        HttpPost request = new HttpPost("https://api.studio.thegraph.com/proxy/55957/dapp-learning-redpacket/version/latest");
        request.setHeader("Content-Type", "application/json");
        // Define your GraphQL query

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
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String jsonResponse = EntityUtils.toString(entity);

            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonObject data = jsonObject.getAsJsonObject("data");
            JsonArray redpacketsArray = data.getAsJsonArray("redpackets");

            //log.info("redpacket array : " + redpacketsArray);
            for (int i = 0; i < redpacketsArray.size(); i++) {
                // Access each element in the array
                JsonObject redpacketObject = redpacketsArray.get(i).getAsJsonObject();
              //  log.info("redpackt object : " + redpacketObject);
                String id = redpacketObject.get("id").getAsString();
             //   log.info("id: " + id);
                RedPacket redPacket = redPacketRepository.findByIdAndStatus(id,0);
                log.info("redPacket: " + redPacket);
                if(redPacket == null )
                    continue;
               String hasRefundedOrAllClaimed  =   redpacketObject.get("hasRefundedOrAllClaimed").getAsString();
                Boolean claimed = redpacketObject.get("hasRefundedOrAllClaimed").getAsBoolean();

                if (claimed) {
                    log.info( "redpacket id: "+id +  "claimed: "  );
                    redPacket.setStatus(1);
                    redPacketRepository.save(redPacket);
                    continue;
                }
                JsonArray claimers = redpacketObject.getAsJsonArray("claimers");

                // redPacket.getAddressList() is the total claimer.
                if(claimers.size() == redPacket.getAddressList().size()) {
                    log.info( "redpacket id: "+id +  " aLL claimed  "  );
                    redPacket.setStatus(1);
                    redPacketRepository.save(redPacket);
                    continue;
                }

                if (claimers.size() > redPacket.getClaimedAddress().size()) {
                    ArrayList<String> claimersList = new ArrayList<>();

                    for (int j = 0; j < claimers.size(); j++) {
                        String s = claimers.get(j).getAsJsonObject().get("claimer").getAsString();
                        //log.info("claimer address: " + s);
                        claimersList.add(s);
                    }
                    redPacket.setClaimedAddress(claimersList);
                    log.info( "update claimed address : " + id  );
                    redPacketRepository.save(redPacket);
                }


            }
        }
    }
}





