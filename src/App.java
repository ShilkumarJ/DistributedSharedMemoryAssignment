import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String args[]) throws IOException {
        GossipServer gossipServer = new GossipServer();
        gossipServer.start( 3 );
    }
}
