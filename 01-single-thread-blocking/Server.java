import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String av[]) throws IOException
    {

        int port = 8080;
        System.out.println("P->started...");

        // here we created server's socket
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("server is listening on port " + port);

        while (true) {
            // here we created client's socket
            Socket clientSocket = serverSocket.accept();
            System.out.println("client arrive");

            // here we made way to read the data sent by the client
            InputStream inStream = clientSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytes = inStream.read(buffer);
            String req = new String(buffer, 0, bytes);
            System.out.println("Client send: " + req);

            // here we made a way to respond to client by creating the outputstream and we
            // formed the message using the http rules.
            // then we use write to put the bytes in the client's socket
            OutputStream outStream = clientSocket.getOutputStream();
            String httpRes = "HTTP/1.1 200 OK\r\n\r\nHello from M3allem Server!";
            outStream.write(httpRes.getBytes());
            clientSocket.close();
            System.out.println("client's socket is closed");
        }
    }
}
