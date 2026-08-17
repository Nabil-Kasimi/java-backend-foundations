package httpresponse;



import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponse {
    private final String version;
    private final int statusCode;
    private final String reasonPhrase;
    private final Map<String, String> headers;
    private final byte[] body;

    private HttpResponse(String version, int statusCode, String reasonPhrase, Map<String, String> headers,
            byte[] body) {
        this.version = version;
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.headers = Map.copyOf(headers);
        this.body = body.clone();
    }

    public byte[] toBytes() {
        StringBuilder sb = new StringBuilder();
        sb.append(version).append(' ').append(statusCode).append(' ').append(reasonPhrase).append("\r\n");

        for (Map.Entry<String, String> e : headers.entrySet()) {
            sb.append(e.getKey()).append(": ").append(e.getValue()).append("\r\n");
        }

        sb.append("\r\n");
        byte[] headerBytes = sb.toString().getBytes(StandardCharsets.UTF_8);
        byte[] full = new byte[headerBytes.length + body.length];
        System.arraycopy(headerBytes, 0, full, 0, headerBytes.length);
        System.arraycopy(body, 0, full, headerBytes.length, body.length);
        return full;
    }

    public static HttpResponse ok(String body) {
        return create("HTTP/1.1", 200, "OK", body);
    }

    public static HttpResponse notFound() {
        return create("HTTP/1.1", 404, "Not Found", "");
    }

    public static HttpResponse badRequest() {
        return create("HTTP/1.1", 400, "Bad Request", "");
    }

    public static HttpResponse serverError() {
        return create("HTTP/1.1", 500, "Server Error", "");
    }

    private static HttpResponse create(String version, int statusCode, String reasonPhrase, String bodyText) {
        Map<String, String> headers = new LinkedHashMap<>();
        byte[] body = bodyText.getBytes(StandardCharsets.UTF_8);
        headers.put("Content-Length", String.valueOf(body.length));
        headers.put("Content-Type", "text/plain; charset=UTF-8");
        return new HttpResponse(version, statusCode, reasonPhrase, headers, body);
    }
}
