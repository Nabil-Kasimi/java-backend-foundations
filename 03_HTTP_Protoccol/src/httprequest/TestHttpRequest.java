package httprequest;
// import httprequest.HttpRequest;

public class TestHttpRequest {
    public static void main(String[] args) {
        String raw = "GET /index.html HTTP/1.1\r\nHost: localhost\r\nConnection: keep-alive\r\n\r\n";
        HttpRequest req = HttpRequest.parseReq(raw);

        System.out.println(req.headers);
    }
}


