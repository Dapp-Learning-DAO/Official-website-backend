package com.dl.officialsite;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;


public class RedPacketGraphQLTest {


    public static final Logger logger = LoggerFactory.getLogger(RedPacketGraphQLTest.class);




    @Test
    public void  test   () {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // HttpPost request = new HttpPost("https://api.studio.thegraph.com/query/55957/dapp-learning-redpacket/version/latest");
            HttpGet request = new HttpGet("https://api.studio.thegraph.com/query/55957/dapp-learning-redpacket/v0.0.1");
            request.setHeader("Content-Type", "application/json");


            //不能有\n 并且要带“”；
            String graphQL1 = " \"{" +
                    "  claims {\n" +
                    "    \n" +
                    "      claimer\n" +
                    "    claimedValue\n" +
                    "    tokenAddress\n" +
                    "    happyRedPacketId  \n" +
                    "  }\n" +
                    "} \"";


            String query = "{ \"query\": " +
                    graphQL1 +
                    " }";

            ArrayList nameValuePairs = new ArrayList<>();

            NameValuePair param1NameValuePair = new BasicNameValuePair("query", query);
            nameValuePairs.add(param1NameValuePair);
            URI uri = new URIBuilder(request.getURI()).addParameters(nameValuePairs).build();
            request.setURI(uri);

            // logger.info("%%%%%%  "+  query);


            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String jsonResponse = EntityUtils.toString(entity);
                // Parse and process the jsonResponse here
                logger.info("&&&&&&"+ jsonResponse);


                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

                // Extract the 'reserves' field
                JsonObject data = jsonObject.getAsJsonObject("data");
                JsonArray reservesArray = data.getAsJsonArray("claims");

                System.out.println("claims : " + reservesArray);
//                for (int i = 0; i < reservesArray.size(); i++) {
//                    // Access each element in the array
//                    JsonObject reserveObject = reservesArray.get(i).getAsJsonObject();
//                    String name = reserveObject.get("name").getAsString();
//                    System.out.println("Reserve Name: " + name);
//                }
                // Use the 'reserveFactor' value as needed

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    @Test
//    public void  testApollo   () {
//        ApolloClient apolloClient = ApolloClient.builder()
//                .serverUrl("https://api.studio.thegraph.com/query/55957/dapp-learning-redpacket/v0.0.1")
//                .build();
//
//            //不能有\n 并且要带“”；
//            String graphQL1 = " \"{" +
//                    "  claims {\n" +
//                    "    \n" +
//                    "      claimer\n" +
//                    "    claimedValue\n" +
//                    "    tokenAddress\n" +
//                    "    happyRedPacketId  \n" +
//                    "  }\n" +
//                    "} \"";
//
//
//            String query = "{ \"query\": " +
//                    graphQL1 +
//                    " }";
//
//
//
//        apolloClient.query(QueryDetails).enqueue(
//                new ApolloCall.Callback<YourQuery.YourQueryData>() {
//                    @Override
//                    public void onResponse(@NotNull Response<YourQuery.YourQueryData> response) {
//                        // Handle the response data here
//                        if (response.hasErrors()) {
//                            System.err.println("GraphQL errors: " + response.getErrors());
//                        } else {
//                            YourQuery.YourQueryData data = response.getData();
//                            List<YourQuery.YourQueryData.Claim> claims = data.claims;
//                            for (YourQuery.YourQueryData.Claim claim : claims) {
//                                // Process each claim
//                                System.out.println("Claimer: " + claim.claimer);
//                                System.out.println("Claimed Value: " + claim.claimedValue);
//                                System.out.println("Token Address: " + claim.tokenAddress);
//                                System.out.println("Happy RedPacket ID: " + claim.happyRedPacketId);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NotNull ApolloException e) {
//                        // Handle the error
//                        e.printStackTrace();
//                    }
//                }
//        );
//
//            // logger.info("%%%%%%  "+  query);
//
//
//
//
//    }


}
