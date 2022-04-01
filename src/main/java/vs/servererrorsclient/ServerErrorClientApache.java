package vs.servererrorsclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("apache")
public class ServerErrorClientApache {

    @Value("${api.noError}")
    String apiNoError;

    @Value("${api.clientError}")
    String apiClientError;

    @Value("${api.serverError}")
    String apiServerError;

    @Value("${api.randomError}")
    String apiRandomError;

    final CloseableHttpClient httpClient = HttpClients.createDefault();
    final ObjectMapper mapper = new ObjectMapper();

    @GetMapping("none")
    public ResponseEntity<String> none() throws IOException {

        HttpGet httpGet = new HttpGet(apiNoError);

        CloseableHttpResponse response = httpClient.execute(httpGet);

        int statusCode = response.getStatusLine().getStatusCode();
        String body = new String(response.getEntity().getContent().readAllBytes());

        return ResponseEntity.status(statusCode).body(body);

    }

    @GetMapping("client")
    public ResponseEntity client() throws IOException {

        HttpGet httpGet = new HttpGet(apiClientError);

        CloseableHttpResponse response = httpClient.execute(httpGet);

        int statusCode = response.getStatusLine().getStatusCode();
        String body = new String(response.getEntity().getContent().readAllBytes());

        if (statusCode == HttpStatus.SC_BAD_REQUEST) {
            ObjectNode errorResponse = mapper.createObjectNode()
                    .put("error from server", statusCode)
                    .put("response from server", body);
            return ResponseEntity.status(statusCode).body(errorResponse);
        }

        return ResponseEntity.status(statusCode).body(body);

    }

    @GetMapping("server")
    public ResponseEntity server() throws IOException {

        HttpGet httpGet = new HttpGet(apiServerError);

        CloseableHttpResponse response = httpClient.execute(httpGet);

        int statusCode = response.getStatusLine().getStatusCode();
        String body = new String(response.getEntity().getContent().readAllBytes());

        if (statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
            ObjectNode errorResponse = mapper.createObjectNode()
                    .put("error from server", statusCode)
                    .put("response from server", body);
            return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(errorResponse);
        }

        return ResponseEntity.status(statusCode).body(body);

    }

    @GetMapping("random")
    public ResponseEntity random() throws IOException {

        HttpGet httpGet = new HttpGet(apiRandomError);

        CloseableHttpResponse response = httpClient.execute(httpGet);

        int statusCode = response.getStatusLine().getStatusCode();
        String body = new String(response.getEntity().getContent().readAllBytes());

        if(statusCode == HttpStatus.SC_BAD_REQUEST) {
            ObjectNode errorResponse = mapper.createObjectNode()
                    .put("error from server", statusCode)
                    .put("response from server", body);
            return ResponseEntity.status(statusCode).body(errorResponse);
        }
        if (statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
            ObjectNode errorResponse = mapper.createObjectNode()
                    .put("error from server", statusCode)
                    .put("response from server", body);
            return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(errorResponse);
        }

        return ResponseEntity.status(statusCode).body(body);

    }

}
