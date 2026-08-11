import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws IOException {
        System.out.println("runing....");

        int port = 8080;

        // here we created the container where to put the socket so we can watch the
        // events
        Selector selector = Selector.open();
        // the passive socket that listen and accept new client
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(port));
        // activating the non-blocking mode
        serverSocket.configureBlocking(false);

        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server is listening on non-blocking-mode");
        while (true) {
            selector.select();
            Set<SelectionKey> trafficSockets = selector.selectedKeys();
            Iterator<SelectionKey> iter = trafficSockets.iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();

                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                    System.out.println("new Client is registered");
                } else if (key.isReadable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int bytes = client.read(buffer);

                    if (bytes == -1) {
                        System.out.println("client disconnected");
                        key.cancel();
                        client.close();
                    } else if (bytes > 0) {
                        buffer.flip();
                        String msg = StandardCharsets.UTF_8.decode(buffer).toString();
                        System.out.println("message is: " + msg);

                        // Prepare echo data and wait for write readiness
                        ByteBuffer echoBuffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
                        key.interestOps(SelectionKey.OP_WRITE);
                        key.attach(echoBuffer);
                    }

                } else if (key.isWritable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();


                    if (buffer != null) {
                        client.write(buffer);
                        if (!buffer.hasRemaining()) {

                            // All data sent, go back to reading
                            key.interestOps(SelectionKey.OP_READ);
                            key.attach(null);
                        }
                    }
                }

            }
        }

    }
}
