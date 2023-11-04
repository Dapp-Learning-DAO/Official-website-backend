package com.dl.officialsite;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class GraphQLTest {


    public static final Logger logger = LoggerFactory.getLogger(GraphQLTest.class);

    @Test
    public void testQL() {
//        WebClient webClient = WebClient.create("https://api.thegraph.com/subgraphs/name/aave/aave-v2-matic");
//       // HttpGraphQlClient graphQlClient = HttpGraphQlClient.create(webClient);
//     WebClientGraphQLClient client = MonoGraphQLClient.createWithWebClient(webClient);
//
//        String query = " {\n" +
//                "    reserves {\n" +
//                "      name\n" +
//                "    }\n" +
//                "  }";
//        //The GraphQLResponse contains data and errors.
//        Mono<GraphQLResponse> graphQLResponseMono = client.reactiveExecuteQuery(query);
//
//
//        //GraphQLResponse has convenience methods to extract fields using JsonPath.
//        Mono<List<String>> somefield = graphQLResponseMono.map(r -> r.extractValue("reserves"));
//
//        logger.info("&&&&&&&7" +somefield.toString());
//        //Don't forget to subscribe! The request won't be executed otherwise.
//        somefield.subscribe().dispose();
    }


    @Test
    public void  test1() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("https://api.thegraph.com/subgraphs/name/aave/aave-v2-matic");
            request.setHeader("Content-Type", "application/json");
            // Define your GraphQL query

     String graphQL = "\"{ reserves { name baseLTVasCollateral reserveFactor utilizationRate reserveLiquidationThreshold liquidityRate variableBorrowRate totalDeposits availableLiquidity totalATokenSupply totalCurrentVariableDebt } }\"" ;

     //不能有\n 并且要带“”；
     String graphQL1 = " \"{" +
             "   reserves {" +
             "      name" +
             "     " +
             "     baseLTVasCollateral" +
             "      reserveFactor" +
             "      utilizationRate" +
             "    reserveLiquidationThreshold" +
             "      liquidityRate " +
             "      variableBorrowRate" +
             "      totalDeposits" +
             "    " +
             "      availableLiquidity" +
             "      totalATokenSupply" +
             "      totalCurrentVariableDebt" +
             "    }" +
             "  } \" ";


         String query = "{ \"query\": " +
                 graphQL1 +
                    " }";


            logger.info("%%%%%%  "+  query);
            request.setEntity(new StringEntity(query));

            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String jsonResponse = EntityUtils.toString(entity);
                // Parse and process the jsonResponse here
                logger.info("&&&&&&"+ jsonResponse);


                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

                // Extract the 'reserves' field
                JsonObject data = jsonObject.getAsJsonObject("data");
                JsonArray reservesArray = data.getAsJsonArray("reserves");

                System.out.println("Reserve array : " + reservesArray);
                for (int i = 0; i < reservesArray.size(); i++) {
                    // Access each element in the array
                    JsonObject reserveObject = reservesArray.get(i).getAsJsonObject();
                    String name = reserveObject.get("name").getAsString();
                    System.out.println("Reserve Name: " + name);
                }
                // Use the 'reserveFactor' value as needed

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
