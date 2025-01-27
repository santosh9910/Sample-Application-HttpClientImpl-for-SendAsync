package com.cavisson.HttpClient_v11;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpClientV11Send {
    
    public static void main(String[] args) throws Exception {
        send();
    }

    public static void send() throws Exception {
        String host = "localhost"; 
        int port = 8181;

       
        HttpServer server = HttpServer.create(new InetSocketAddress(host, port), 0);

        server.createContext("/", new MyHandlert()); 
        server.setExecutor(null); 
        server.start();
        System.out.println("Server started on http://" + host + ":" + port);

        
        Thread.sleep(2000);

        String URL = "http://" + host + ":" + port + "/";
        System.out.println("Going to call sendHttpClient...");
        sendHttpClient(URL);
    }

    public static void sendHttpClient(String url) {
        final long TEST_DURATION_MS = 1 * 60 * 1000; // 1 minute duration

        long startTime = System.currentTimeMillis();
        long endTime = startTime + TEST_DURATION_MS;

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        while (System.currentTimeMillis() < endTime) {
            execute(url, client);
        }
        System.out.println("Test completed.");
    }

    public static void execute(String url, HttpClient client) {
        try {
            HttpRequest initialRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .headers("X-Our-Header-1", "value1", "X-Our-Header-2", "value2")
                    .GET()
                    .build();

            System.out.println("Sending request to URL: " + initialRequest.uri() + " HttpClient class: " + client.getClass().getName());

            HttpResponse<String> response = client.send(initialRequest, HttpResponse.BodyHandlers.ofString());

            if (response != null) {
                System.out.println("Response Status code: " + response.statusCode()+"CLIENT === "+client.getClass().getName());
                System.out.println("Response Body: " + response.body()+"CLIENT === "+client.getClass().getName());
            } else {
                System.out.println("Received null response...");
            }

            Thread.sleep(2000); // Shorter sleep time between requests
        } catch (Exception e) {
            System.err.println("An error occurred while sending the request...");
            e.printStackTrace();
        }
    }
}

class MyHandlert implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "<html><body><h1>Hello, HTTP Client!</h1></body></html>";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
