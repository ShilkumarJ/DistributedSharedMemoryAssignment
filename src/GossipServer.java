import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GossipServer {
    static Map<Socket, PrintWriter> socketPrintWriterMap = new HashMap<>(  );
    public static void main( String[] args ) throws Exception {
        BufferedReader keyRead = new BufferedReader( new InputStreamReader( System.in ) );
        ServerSocket sersock = new ServerSocket( 3000 );
        System.out.println( "Server  ready for chatting" );
        Socket sock1 = sersock.accept();
        Socket sock2 = sersock.accept();
        Socket sock3 = sersock.accept();
        Socket sock4 = sersock.accept();

        createThread( sock1, keyRead );
        createThread( sock2, keyRead );
        createThread( sock3, keyRead );
        createThread( sock4, keyRead );

        String sendMessage;
        while ( true ) {
            try {
                sendMessage = keyRead.readLine();
                int  client = Integer.parseInt(  sendMessage.split( " " )[0]);
                String msg =  sendMessage.split( " " )[1];
                PrintWriter pwrite = null;

                switch ( client ){
                    case 1:
                        pwrite = socketPrintWriterMap.get(sock1);
                        break;
                    case 2:
                        pwrite = socketPrintWriterMap.get(sock2);
                        break;
                    case 3:
                        pwrite = socketPrintWriterMap.get(sock3);
                        break;
                    case 4:
                        pwrite = socketPrintWriterMap.get(sock4);
                        break;
                }

                pwrite.println( sendMessage );
                pwrite.flush();
            } catch ( IOException e ) {
                e.printStackTrace();
            }

        }

    }

    private static void createThread( final Socket sock, BufferedReader keyRead ) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                System.out.println( "port   " + sock.getPort() );

                OutputStream ostream = null;
                BufferedReader receiveRead = null;
                PrintWriter pwrite = null;
                String receiveMessage;

                try {
                    ostream = sock.getOutputStream();
                    pwrite = new PrintWriter( ostream, true );
                    socketPrintWriterMap.put( sock, pwrite );
                    System.out.println("map"+sock.getPort());
                    InputStream istream = sock.getInputStream();
                    receiveRead = new BufferedReader( new InputStreamReader( istream ) );
                    if ( ( receiveMessage = receiveRead.readLine() ) != null ) {
                        System.out.println( sock.getPort()+"  "+ receiveMessage );
                    }
                } catch ( IOException e ) {
                    e.printStackTrace();
                }

            }
        } ).start();
    }
}