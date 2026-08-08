import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws IOException{
        System.out.println("runing....");

        int port = 8080;

        //here we created the container where to put the socket so we can watch the events
        Selector selector =  Selector.open();
        //the passive socket that listen and accept new client
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(port));
        // activating the non-blocking mode
        serverSocket.configureBlocking(false);

        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server is listening on non-blocking-mode");
        while(true)
        {
            selector.select();
            System.out.println("new client arrive");
            Set<SelectionKey> trafficSockets = selector.selectedKeys();
            Iterator<SelectionKey> iter = trafficSockets.iterator();

            while(iter.hasNext())
            {
                SelectionKey key = iter.next();

                if(key.isAcceptable())
                {
                    
                }
            }
        }

    }
}
