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

public class HttpCinetV11AsyncWithHostandPort {

    public static void main(String[] args) throws IOException {
    	
        // Define the host and port for the HTTP server
        String host = "localhost"; 
        int port = 8283; 

        // Create an HttpServer instance
        HttpServer server = HttpServer.create(new InetSocketAddress(host, port), 0);

        // Set up the context (endpoint) that the server will respond to
        server.createContext("/", new MyHandler());

        // Start the server
        server.setExecutor(null); 
        server.start();
        System.out.println("Server started on http://" + host + ":" + port);

        // Construct the URL for the HttpClient to hit
        String url = "http://" + host + ":" + port + "/"; // URL to hit

        
        sendHttpClientAsync(url);
    }

    public static void sendHttpClientAsync(String url) {
        final long TEST_DURATION_MS = 10 * 60 * 1000; // 10 minutes

        long startTime = System.currentTimeMillis();
        long endTime = startTime + TEST_DURATION_MS;

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        while (System.currentTimeMillis() < endTime) {
            try {
                HttpRequest initialRequest = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .headers("X-Our-Header-1", "value1", "X-Our-Header-2", "value2")
                        .GET()
                        .build();

                System.out.println("Sending request to URL: " + initialRequest.uri()+" HttpClientV11AsyncWithHostandPort internal class name ===========>"+client.getClass().getName());

                CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(initialRequest, HttpResponse.BodyHandlers.ofString());

                // Process the response asynchronously
                responseFuture.thenAccept(response -> {
                    if (response != null) {
                        System.out.println("Response Status code: " + response.statusCode());
                        System.out.println("Response Body: " + response.body());
                    } else {
                        System.out.println("Received null response.......");
                    }
                }).exceptionally(e -> {
                    System.err.println("An error occurred while sending the request asynchronously...");
                    e.printStackTrace();
                    return null;
                });

                // Wait for the response to complete before sending another request
                responseFuture.join();

                // Add delay between requests
                Thread.sleep(10000);

            } catch (Exception e) {
                System.err.println("An error occurred while building the request...");
                e.printStackTrace();
            }
        }

        
    }

    // Define the MyHandler class outside the sendHttpClientAsync method
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "<html><body><h1>Hello from Java HttpCinetV11AsyncWithHostandPort Server!</h1></body></html>";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
