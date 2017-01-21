import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Node {
    private Map<Socket, PrintWriter> socketPrintWriterMap = new HashMap<>();
    private List<Socket> sockets = new ArrayList<>();
    private ServerSocket serSock;

    public Node( int portNumber ) {
        createSocket( portNumber );
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
        PrintWriter printWriter = new PrintWriter( outputStream, true );
        socketPrintWriterMap.put( sock, printWriter );
    }

    private void createSocket( int portNumber ) {
        try {
            serSock = new ServerSocket( portNumber );
        } catch ( IOException e ) {
            e.printStackTrace();
        }

    }

    public void start( int numberOfNodes, List<Integer> portNumbers ) throws IOException, InterruptedException {
        connectTo( portNumbers );
        for ( int i = 0; i < numberOfNodes; i++ ) {
            Socket socket;
//            try {
                socket = serSock.accept();
//            } catch ( Exception e ) {
//
//                System.out.println( "Unable To connect to " + portNumbers.get( i ) + ", Retrying in 5 seconds" );
//                Thread.sleep( 5000 );
//                socket = new Socket( "127.0.0.1", portNumbers.get( i ) );
//
//            }
            System.out.println( "Connected To::" + socket.getPort() );
            sockets.add( socket );
            listenTo( socket );
            createOutputStreamFor( socket );

        }
        startWriteThread();

    }

    private void connectTo( List<Integer> portNumbers ) {
        new Thread( () -> {
            Iterator iterator = portNumbers.iterator();
            while ( iterator.hasNext() ) {
                Integer portNumber = (Integer) iterator.next();

                try {
                    Socket socket = new Socket( "127.0.0.1", portNumber );
                    listenTo( socket );
                } catch ( Exception e ) {

                    try {
                        System.out.println( "Unable To connect to " + portNumber + ", Retrying in 10 seconds" );
                        Thread.sleep( 10000 );
                        Socket socket = new Socket( "127.0.0.1", portNumber );
                        listenTo( socket );
                    } catch ( Exception e1 ) {
                        e.printStackTrace();
                    }
                }
            }
        } ).start();
    }

    private void startWriteThread() {
        BufferedReader keyRead = new BufferedReader( new InputStreamReader( System.in ) );

        new Thread( () -> {
            while ( true ) {
                try {
                    String sendMessage = keyRead.readLine();
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
        } ).start();

    }
}