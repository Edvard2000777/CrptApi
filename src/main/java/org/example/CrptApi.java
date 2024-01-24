package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CrptApi {
    private static final String API_URL = "https://ismp.crpt.ru/api/v3/lk/documents/create";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final Semaphore requestSemaphore;
    private final HttpClient httpClient;


    public CrptApi(TimeUnit timeUnit, int requestLimit, HttpClient client) {
        this.requestSemaphore = new Semaphore(requestLimit);
        this.httpClient = HttpClient.newHttpClient();
        scheduleSemaphoreReset(timeUnit);
    }

    private void scheduleSemaphoreReset(TimeUnit timeUnit) {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(timeUnit.toMillis(1));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                requestSemaphore.drainPermits();
            }
        }).start();
    }

    public void createDocument(String documentJson, String signature) {
        try {
            requestSemaphore.acquire();
            sendPostRequest(documentJson, signature);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            requestSemaphore.release();
        }
    }

    private void sendPostRequest(String documentJson, String signature) {
        try {
            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.put("document", documentJson);
            requestBody.put("signature", signature);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

// Handle the API response as needed
            System.out.println("API Response: " + response.statusCode());
            System.out.println(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
