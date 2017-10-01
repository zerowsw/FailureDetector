package CS425;


import java.util.logging.Logger;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*** This class is used to listening to the message from other nodes.
 *
 */

public class ListeningThread extends Thread{

    public static Logger logger = Logger.getLogger(ListeningThread.class.getName());
    public ListeningThread(int port){
        byte[] buf = new byte[1024];
        DatagramSocket ds = new DatagramSocket(9000);
        InetAddress loc = InetAddress.getLocalHost();
        

    }
}
