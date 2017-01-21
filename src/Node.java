//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.List;
//
//public class Node {
//    private final int portNumber;
//    private final List<Node> nodes;
//    private boolean flag;
//
//    public Node(int portNumber, List<Node> nodes) throws Exception {
//        this.portNumber = portNumber;
//        this.nodes = nodes;
////        String data = "Toobie ornaught toobie";
////        try {
////            ServerSocket srvr = new ServerSocket(portNumber);
//////            System.out.print("Server has connected!\n");
//////            PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
//////            System.out.print("Sending string: '" + data + "'\n");
//////            out.print(data);
//////            out.close();
////            Socket skt = srvr.accept();
////
////            while ( !flag ) {
////                PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
////
////            }
////            skt.close();
////            srvr.close();
////        }
////        catch(Exception e) {
////            System.out.print("Whoops! It didn't work!\n");
////        }
//        ServerSocket sersock = new ServerSocket(portNumber);
//        System.out.println("Server  ready for chatting");
//        Socket sock = sersock.accept( );
//        sock.
//
//        Socket sock = new Socket("127.0.0.1", 3000);
//        // reading from keyboard (keyRead object)
//        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
//        // sending to client (pwrite object)
//        OutputStream ostream = sock.getOutputStream();
//        PrintWriter pwrite = new PrintWriter(ostream, true);
//
//        // receiving from server ( receiveRead  object)
//        InputStream istream = sock.getInputStream();
//        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
//
//        System.out.println("Start the chitchat, type and press Enter key");
//
//        String receiveMessage, sendMessage;
//        while(true)
//        {
//            sendMessage = keyRead.readLine();  // keyboard reading
//            pwrite.println(sendMessage);       // sending to server
//            pwrite.flush();                    // flush the data
//            if((receiveMessage = receiveRead.readLine()) != null) //receive from server
//            {
//                System.out.println(receiveMessage); // displaying at DOS prompt
//            }
//        }
//    }
//}
//
//
//}
