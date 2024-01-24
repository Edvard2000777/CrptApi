import org.example.CrptApi;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.net.http.HttpClient;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import static org.mockito.Mockito.*;

public class CrptApiTest {

    @Mock
    private HttpClient httpClient;

    private CrptApi crptApi;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        crptApi = new CrptApi(TimeUnit.SECONDS, 1, httpClient);
      //  crptApi.setHttpClient(httpClient);  // Добавьте этот метод в класс CrptApi для установки mock HttpClient
    }

    @Test
    public void testCreateDocument() throws Exception {
        String documentJson = "{}";
        String signature = "signature";

        HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("Success");

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(response);

        crptApi.createDocument(documentJson, signature);

        verify(httpClient, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }
}
