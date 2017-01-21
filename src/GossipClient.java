import java.io.*;
import java.net.*;
public class GossipClient
{
    public static void main(String[] args) throws Exception
    {

        Socket sock = new Socket("127.0.0.1", 3000);
        // reading from keyboard (keyRead object)
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
        // sending to client (pwrite object)
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);

        // receiving from server ( receiveRead  object)
        InputStream istream = sock.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

//        System.out.println("Start the chitchat, type and press Enter key");
//        pwrite.println("coming...");       // sending to server
//        pwrite.flush();


            new Thread( new Runnable() {
                @Override public void run() {
                    while ( true ) {
                        String sendMessage = null;  // keyboard reading
                        try {
                            sendMessage = keyRead.readLine();
                        } catch ( IOException e ) {
                            e.printStackTrace();
                        }
                        pwrite.println( sendMessage );
                        pwrite.flush();// flush the data
                    }

                }
            } ) .start();     // sending to server
            new Thread( new Runnable() {
                @Override public void run() {
                    while ( true ) {
                        String receiveMessage;
                        try {
                            if ( ( receiveMessage = receiveRead.readLine() ) != null ) //receive from server
                            {
                                System.out.println( receiveMessage ); // displaying at DOS prompt
                            }
                        } catch ( IOException e ) {
                            e.printStackTrace();
                        }
                    }
                }
            } ).start();


    }
}                        