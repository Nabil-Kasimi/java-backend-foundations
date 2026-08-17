package httprequest;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    public final String method;
    public final String path;
    public final String version;
    public final Map<String, String> headers;

    public HttpRequest(String method, String path, String version, Map<String, String> headers) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.headers = headers;
    }

    public static HttpRequest parseReq(String req) {

        String lines[] = req.split("\r\n");
        if (lines.length == 0)
            return null;

        String reqLine[] = lines[0].split(" ");
        if (reqLine.length != 3)
            return null;

        String method = reqLine[0];
        String path = reqLine[1];
        String version = reqLine[2];

        Map<String, String> headers = new HashMap<>();

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            if (line.isEmpty())
                break;
            int colon = line.indexOf(":");
            if (colon > 0) {
                String key = line.substring(0, colon).trim().toLowerCase();
                String value = line.substring(colon + 1).trim();
                headers.put(key, value);
            }
        }

        return new HttpRequest(method, path, version, headers);
    }

}
