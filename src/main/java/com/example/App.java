package com.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class App {
    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", App::serveHomePage);
            server.setExecutor(null);
            server.start();
            System.out.println("Browser app running at http://localhost:" + port);
        } catch (IOException exception) {
            throw new RuntimeException("Could not start the browser app", exception);
        }
    }

    private static void serveHomePage(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            exchange.close();
            return;
        }

        try (InputStream page = App.class.getResourceAsStream("/index.html")) {
            if (page == null) {
                exchange.sendResponseHeaders(404, -1);
                exchange.close();
                return;
            }

            byte[] content = readAll(page);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, content.length);
            try (OutputStream response = exchange.getResponseBody()) {
                response.write(content);
            }
        }
    }

    private static byte[] readAll(InputStream input) throws IOException {
        java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        return new String(output.toByteArray(), StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8);
    }
}