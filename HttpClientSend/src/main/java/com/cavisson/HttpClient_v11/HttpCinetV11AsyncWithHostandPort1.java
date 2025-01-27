package com.cavisson.HttpClient_v11;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;



public class HttpCinetV11AsyncWithHostandPort1 {

    public static void main(String[] args) throws Throwable{
     
    	sendmain();
    	
    }
    
    public static void sendmain() throws Exception{
    	String host = "localhost"; 
        int port = 8183; 

       
        HttpServer server = HttpServer.create(new InetSocketAddress(host, port), 0);

        
        server.createContext("/", new MyHandler());

        
        server.setExecutor(null); // Default executor
        server.start();
        System.out.println("Server started on http://" + host + ":" + port);
        
        String url = "http://" + host + ":" + port + "/"; // URL to hit
        Thread.sleep(60000);
        System.out.println(" going to call Httpclient =====");
        sendHttpClientAsync(url);
    }

    public static void sendHttpClientAsync(String url) {

        final long TEST_DURATION_MS = 3*60*1000;

        long startTime = System.currentTimeMillis();
        long endTime = startTime + TEST_DURATION_MS;

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        System.out.println("client ="+client);

        while (true) {
        	extracted(url, client);
        }
        

        
        
        
        
    }

	public static void extracted(String url, HttpClient client) {
		
            try {
                HttpRequest initialRequest = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .headers("X-Our-Header-1", "value1", "X-Our-Header-2", "value2")
                        .GET()
                        .build();

                System.out.println("Sending request to URL: " + initialRequest);

                CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(initialRequest, HttpResponse.BodyHandlers.ofString());

                // Process the response asynchronously
                responseFuture.thenAccept(response -> {
                    if (response != null) {
                        System.out.println("Response Status code: " + response.statusCode()+" complete FQM is name ===> "+client.getClass().getName()+" client = "+client);
                        System.out.println("Response Body: " + response.body());
                    } else {
                        System.out.println("Received null response.......");
                    }
                }).exceptionally(e -> {
                    System.err.println("An error occurred while sending the request asynchronously...");
                    e.printStackTrace();
                    return null;
                });

                
                responseFuture.join();

                
                Thread.sleep(30000);

            } catch (Exception e) {
                System.err.println("An error occurred while building the request...");
                e.printStackTrace();
            }
        
	}
}
class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "<html><body><h1>Hello from Java HTTP Server!</h1></body></html>";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
