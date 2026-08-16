
import java.nio.charset.StandardCharsets;

import httpresponse.HttpResponse;

public class TestHttpResponse {
    public static void main(String[] args) {
        String body = "hello welcome to our server";
        HttpResponse response = HttpResponse.ok(body);

        String raw = new String(response.toBytes(), StandardCharsets.UTF_8);
        System.out.println(raw);

        System.out.println("**********************************");
        HttpResponse response2 = HttpResponse.notFound();

        String raw2 = new String(response2.toBytes(), StandardCharsets.UTF_8);
        System.out.println(raw2);
    }
}
