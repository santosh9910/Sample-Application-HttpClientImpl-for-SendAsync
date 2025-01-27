# Sample-Application-HttpClientImpl-for-SendAsync

This project demonstrates the use of `HttpClient` from Java's `java.net.http` package to send asynchronous HTTP requests using the `sendAsync` method. It also includes a simple HTTP server implemented using `HttpServer` from `com.sun.net.httpserver`.

---

## Features

1. **HTTP Server**:
   - Listens on `http://localhost:8183/`.
   - Responds with a simple HTML message:  
     `Hello from Java HTTP Server!`

2. **HTTP Client**:
   - Sends asynchronous HTTP GET requests to the server using `HttpClient.sendAsync`.
   - Handles the response and displays:
     - HTTP status code.
     - Response body.

---

## Requirements

- **Java Version**: 11 or higher
- **Maven**: Ensure Maven is installed for dependency management and project build.

---
**How It Works**
Start the HTTP Server: The server starts on http://localhost:8183/.
Any GET request to this URL will receive the response:

<html><body><h1>Hello from Java HTTP Server!</h1></body></html>

**HTTP Client Sends Asynchronous Requests**:
The client sends multiple HTTP GET requests to the server.
It uses HttpClient's sendAsync method to make non-blocking calls.
Logs the following for each request:
**Status code.
Response body.**

