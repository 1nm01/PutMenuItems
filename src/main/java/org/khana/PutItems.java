package org.khana;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.khana.entity.Menu;
import org.khana.response.ApiResponse;

import java.util.Map;

public class PutItems implements RequestHandler<APIGatewayProxyRequestEvent, ApiResponse> {
    private static final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).build();
    private static final DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(client);
    private static final DynamoDBMapperConfig dynamoDBMapperConfig = DynamoDBMapperConfig.builder().withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.PUT).build();
    @Override
    public ApiResponse handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
        Gson gson = new Gson();
        Menu menu = null;
        LambdaLogger logger = context.getLogger();
        try {
            menu = gson.fromJson(apiGatewayProxyRequestEvent.getBody(), Menu.class);
        } catch (JsonSyntaxException e) {
            logger.log(e.getMessage());
        }
        dynamoDBMapper.save(menu, dynamoDBMapperConfig);
        Map<String,String> headers = Map.of("content-type", "application/json");
        return new ApiResponse(201, headers, menu.toString());
    }
}
