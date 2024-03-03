import org.example.CrptApi;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.net.http.HttpClient;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import static org.mockito.Mockito.*;

class CrptApiTest {

    @Test
    void testCreateDocument() throws Exception {
        // Создание моков
        HttpClient httpClientMock = Mockito.mock(HttpClient.class);
        HttpResponse<String> responseMock = Mockito.mock(HttpResponse.class);

        // Установка поведения мока для метода send
        when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(responseMock);
        when(responseMock.statusCode()).thenReturn(200); // Установка кода ответа

        // Создание экземпляра класса CrptApi
        CrptApi crptApi = new CrptApi(TimeUnit.SECONDS, 10, httpClientMock);

        // Вызов метода createDocument
        crptApi.createDocument("test_document_json", "test_signature");

        // Здесь можно добавить проверки на ожидаемое поведение после вызова метода createDocument
    }
}