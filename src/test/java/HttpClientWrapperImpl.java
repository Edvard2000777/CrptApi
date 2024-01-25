

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientWrapperImpl implements HttpClientWrapper {
    private final HttpClient httpClient;

    public HttpClientWrapperImpl(HttpClient httpClient) {
        this.httpClient = httpClient;

    }
    @Override
    public HttpResponse<String> send(HttpRequest request, HttpResponse.BodyHandler<String> responseBodyHandler) {
        try {
            return httpClient.send(request, responseBodyHandler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}