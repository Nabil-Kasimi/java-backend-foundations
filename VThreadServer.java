import httprequest.HttpRequest;
import httpresponse.HttpResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class VThreadServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Virtual Thread Server listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // blocking, but okay
                Thread.startVirtualThread(() -> handleClient(clientSocket));
            }
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (clientSocket) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            OutputStream out = clientSocket.getOutputStream();

            // Read request line + headers until blank line
            StringBuilder raw = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                raw.append(line).append("\r\n");
                if (line.isEmpty()) {
                    break; // end of headers
                }
            }

            String rawRequest = raw.toString();
            System.out.println("Request:\n" + rawRequest);

            // Read request line + headers until blank line
            StringBuilder raw = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                raw.append(line).append("\r\n");
                if (line.isEmpty()) {
                    break; // end of headers
                }
            }  HttpRequest request = HttpRequest.parseReq(rawRequest);
            if (request == null || !request.method.equals("GET")) {
                writeResponse(out, HttpResponse.badRequest());
                return;
            }

            HttpResponse response;
            if (request.path.equals("/")) {
                response = HttpResponse.ok("Hello from virtual threads!");
            } else {
                response = HttpResponse.notFound();
            }

            writeResponse(out, response);

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }

    private static void writeResponse(OutputStream out, HttpResponse response) throws IOException {
        out.write(response.toBytes());
        out.flush();
    }
}
