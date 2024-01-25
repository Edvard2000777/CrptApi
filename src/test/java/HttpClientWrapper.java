import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public interface HttpClientWrapper {
    HttpResponse<String> send(HttpRequest request, HttpResponse.BodyHandler<String> responseBodyHandler) throws IOException, InterruptedException;
}