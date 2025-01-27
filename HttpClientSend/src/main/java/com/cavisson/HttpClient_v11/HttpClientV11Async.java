package com.cavisson.HttpClient_v11;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class HttpClientV11Async {

    public static void main(String[] args) {
        String URL = "https://www.google.com";
        sendHttpClientAsync(URL);
    }

    public static void sendHttpClientAsync(String url) {

        final long TEST_DURATION_MS = 3 * 60 * 1000;

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

                System.out.println(" Sending request to URL: " + initialRequest.uri()+" HttpClient class name is = "+client.getClass().getName());

                CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(initialRequest, HttpResponse.BodyHandlers.ofString());

                // Process the response asynchronously
                responseFuture.thenAccept(response -> {
                    if (response != null) {
                        System.out.println(" Response Status code: " + response.statusCode());
                        System.out.println(" Response Body: " + response.body());
                    } else {
                        System.out.println(" Received null response.......");
                    }
                }).exceptionally(e -> {
                    System.err.println(" An error occurred while sending the request asynchronously...");
                    e.printStackTrace();
                    return null;
                });

                // Wait for the response to complete before sending another request
                responseFuture.join();

                Thread.sleep(10000);
            } catch (Exception e) {
                System.err.println(" An error occurred while building the request...");
                e.printStackTrace();
            }
        }
    }
}
