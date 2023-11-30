package org.khana;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent = new APIGatewayProxyRequestEvent();
        apiGatewayProxyRequestEvent.setBody("{\"restId\": \"1\"}");
        PutItems fcmToken = new PutItems();
        fcmToken.handleRequest(apiGatewayProxyRequestEvent, null);
    }
}
