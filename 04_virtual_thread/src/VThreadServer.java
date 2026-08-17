import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import httprequest.HttpRequest;
import httpresponse.HttpResponse;

public class VThreadServer {
    public static void main(String[] args) {
        int port = 8081;

        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server is listening on Port: " + port);

            while (true) {

                Socket client = server.accept();

                Thread.startVirtualThread(() -> {
                    handelRequest(client);
                });
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handelRequest(Socket client) {
        try {
            BufferedReader buffer = new BufferedReader(
                    new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));

            OutputStream ouStream = client.getOutputStream();

            StringBuilder raw = new StringBuilder();
            String line;

            while ((line = buffer.readLine()) != null) {
                raw.append(line).append("\r\n");
                if (line.isEmpty())
                    break;
            }

            HttpRequest req = HttpRequest.parseReq(raw.toString());

            if (req == null) {
                ouStream.write(HttpResponse.badRequest().toBytes());
                ouStream.flush();
                return;
            }

            HttpResponse res;

            if (req.method.equals("GET") && req.path.equals("/")) {
                res = HttpResponse.ok("Hello from virtual threads");
            } else if (!req.method.equals("GET")) {
                res = HttpResponse.badRequest();
            } else {
                res = HttpResponse.notFound();
            }

            ouStream.write(res.toBytes());
            ouStream.flush();
            client.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
