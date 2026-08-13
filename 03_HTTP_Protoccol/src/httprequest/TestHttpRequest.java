package httprequest;
// import httprequest.HttpRequest;

public class TestHttpRequest {
    public static void main(String[] args) {
        String raw = "GET /index.html HTTP/1.1\r\nHost: localhost\r\nConnection: keep-alive\r\n\r\n";
        HttpRequest req = HttpRequest.parseReq(raw);
        System.out.println("the http methode: " + req.method);
        System.out.println("the endpoint: " + req.path);
        System.out.println("the headers: " + req.headers);
    }
}
