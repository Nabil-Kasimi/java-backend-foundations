package httpresponse;

import java.util.Map;

public class HttpResponse {

    public static final String HTTP_1_1 = "HTTP/1.1";

    private final String version;
    private final int statusCode;
    private final String reasonPhrase;
    private final Map<String, String> headers;
    private final byte[] body;

    private HttpResponse(Builder builder) {
        this.version = builder.version;
        this.statusCode = builder.statusCode;
        this.reasonPhrase = builder.reasonPhrase;
        this.headers = Map.copyOf(builder.headers);
        this.body = builder.body.clone();
    }

    public String getVersion() {
        return version;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body.clone();
    }

    public static final class Builder {
        
    }
}
