package vs.servererrorsclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("template")
public class ServerErrorClientTemplate {

    @Value("${api.noError}")
    String apiNoError;

    @Value("${api.clientError}")
    String apiClientError;

    @Value("${api.serverError}")
    String apiServerError;

    @Value("${api.randomError}")
    String apiRandomError;

    final RestTemplate restTemplate = new RestTemplate();
    final ObjectMapper mapper = new ObjectMapper();

    public ServerErrorClientTemplate() {
    }

    @GetMapping("none")
    public ResponseEntity<String> none() {
        return restTemplate.exchange(apiNoError, HttpMethod.GET, null, String.class);
    }

    @GetMapping("client")
    public ResponseEntity<ObjectNode> client() {
        try {
            return restTemplate.exchange(apiClientError, HttpMethod.GET, null, ObjectNode.class);
        } catch (HttpClientErrorException e) {
            ObjectNode errorResponse = mapper.createObjectNode()
                    .put("error from server", e.getStatusCode().value())
                    .put("response from server", e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
        }
    }

    @GetMapping("server")
    public ResponseEntity<ObjectNode> server() {
        try {
            return restTemplate.exchange(apiServerError, HttpMethod.GET, null, ObjectNode.class);
        } catch (HttpServerErrorException e) {
            ObjectNode errorResponse = mapper.createObjectNode()
                    .put("error from server", e.getStatusCode().value())
                    .put("response from server", e.getResponseBodyAsString());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }

    @GetMapping("random")
    public ResponseEntity random() {
        try {
            return restTemplate.exchange(apiRandomError, HttpMethod.GET, null, String.class);
        } catch (HttpClientErrorException e) {
            ObjectNode errorResponse = mapper.createObjectNode()
                    .put("error from server", e.getStatusCode().value())
                    .put("response from server", e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
        } catch (HttpServerErrorException e) {
            ObjectNode errorResponse = mapper.createObjectNode()
                    .put("error from server", e.getStatusCode().value())
                    .put("response from server", e.getResponseBodyAsString());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }

}
