package com.cavisson.HttpClient_v11;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class HttpServerExample {

    public static void main(String[] args) throws IOException {
       
        InetSocketAddress socketAddress = new InetSocketAddress("localhost", 8080);
        HttpServer server = HttpServer.create(socketAddress, 0);
        System.out.println("Server is starting on port 8080...");

        
        server.createContext("/test", new MyHttpHandler());

        
        server.start();
        System.out.println("Server started. Listening on http://localhost:8080/test");
    }

    
    static class MyHttpHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
           
            HttpClient client = HttpClient.newHttpClient();
            
           
            System.out.println("HttpClient internal class: " + client.getClass().getName());

           
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
                    .GET() 
                    .build();
            
            
            System.out.println("HttpRequest internal class: " + request.getClass().getName());

            String responseBody = "";
            int statusCode = 200;

            try {
               
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                
                
                System.out.println("HttpResponse internal class: " + response.getClass().getName());
                
                
                statusCode = response.statusCode();
                responseBody = response.body();
                System.out.println("Status Code: " + statusCode);
                System.out.println("Response Body: " + responseBody);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            
            String responseText = "HttpClient fetched data: " + responseBody;
            exchange.sendResponseHeaders(statusCode, responseText.length());
            OutputStream os = exchange.getResponseBody();
            os.write(responseText.getBytes());
            os.close();
        }
    }
}
