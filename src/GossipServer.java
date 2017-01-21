import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GossipServer {
    static Map<Socket, PrintWriter> socketPrintWriterMap = new HashMap<>();
    static List<Socket> sockets = new ArrayList<>();
    private ServerSocket serSock;

    public GossipServer() {
        createSocket();
    }

    public static void main( String[] args ) throws Exception {

    }

    private void listenTo( final Socket sock ) {
        new Thread( () -> {

            BufferedReader receiveRead = null;
            String receiveMessage;

            try {
                InputStream istream = sock.getInputStream();
                receiveRead = new BufferedReader( new InputStreamReader( istream ) );
                if ( ( receiveMessage = receiveRead.readLine() ) != null ) {
                    System.out.println( sock.getPort() + "  " + receiveMessage );
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }

        } ).start();
    }

    private void createOutputStreamFor( Socket sock ) throws IOException {
        OutputStream outputStream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter( outputStream, true );
        socketPrintWriterMap.put( sock, pwrite );
    }

    private void createSocket() {
        try {
            serSock = new ServerSocket( 3000 );
        } catch ( IOException e ) {
            e.printStackTrace();
        }

    }

    public void start( int numberOfNodes ) throws IOException {
        for ( int i = 0; i < numberOfNodes; i++ ) {
            Socket socket = serSock.accept();
            sockets.add( socket );
            listenTo( socket );
            createOutputStreamFor( socket );

        }
        startWriteThread();
    }

    private void startWriteThread() {
        BufferedReader keyRead = new BufferedReader( new InputStreamReader( System.in ) );

        String sendMessage;
        while ( true ) {
            try {
                sendMessage = keyRead.readLine();
                int clientNumber = Integer.parseInt( sendMessage.split( " " )[0] );
                String msg = sendMessage.split( " " )[1];
                PrintWriter printWriter = null;
                printWriter = socketPrintWriterMap.get( sockets.get( clientNumber - 1 ) );
                printWriter.println( msg );
                printWriter.flush();
            } catch ( IOException e ) {
                e.printStackTrace();
            }

        }
    }
}