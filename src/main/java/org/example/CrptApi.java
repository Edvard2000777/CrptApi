package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.*;

public class CrptApi {
    private static final String API_URL = "https://ismp.crpt.ru/api/v3/lk/documents/create";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final Semaphore requestSemaphore;
    private final HttpClient httpClient;
    private final ExecutorService executor;

    public CrptApi(TimeUnit timeUnit, int requestLimit, HttpClient client) {
        this.requestSemaphore = new Semaphore(requestLimit);
        this.httpClient = client;
        this.executor = Executors.newSingleThreadExecutor(); // Создаем исполнителя с одним потоком
        scheduleSemaphoreReset(timeUnit);
    }

    private void scheduleSemaphoreReset(TimeUnit timeUnit) {
        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(timeUnit.toMillis(1));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                requestSemaphore.drainPermits();
            }
        });
    }

    public void createDocument(String documentJson, String signature) {
        try {
            requestSemaphore.acquire();
            executor.execute(() -> sendPostRequest(documentJson, signature));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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
        } finally {
            requestSemaphore.release();
        }
    }

    // Закрыть исполнителя при завершении работы
    public void shutdown() {
        executor.shutdown();
    }
}