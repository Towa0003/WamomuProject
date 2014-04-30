package fh.kl.wamomu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;

/**
 * Created by Max on 15.04.2014.
 */
public class CustomRestException extends RestClientException {

    private HttpStatus statusCode;

    private String body;

    public CustomRestException(String msg) {
        super(msg);
        // TODO Auto-generated constructor stub
    }

    public CustomRestException(HttpStatus statusCode, String body, String msg) {
        super(msg);
        this.statusCode = statusCode;
        this.body = body;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
