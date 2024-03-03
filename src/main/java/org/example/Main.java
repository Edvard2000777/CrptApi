package org.example;
import java.net.http.HttpClient;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Создание HTTP-клиента
        HttpClient httpClient = HttpClient.newHttpClient();

        // Создание экземпляра CrptApi с временем ожидания 1 секунда и лимитом запросов 10
        CrptApi crptApi = new CrptApi(TimeUnit.SECONDS, 10, httpClient);

        // Создание документа JSON для отправки
        String documentJson = "{\"key\": \"value\"}";
        // Создание подписи
        String signature = "signature";

        // Создание документа через CrptApi
        crptApi.createDocument(documentJson, signature);

        // Задержка для демонстрации
        try {
            Thread.sleep(20000); // Подождем 2 секунды
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Закрытие экземпляра CrptApi
        crptApi.shutdown();
    }
}