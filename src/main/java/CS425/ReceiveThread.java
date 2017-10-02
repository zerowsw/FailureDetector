package CS425;


import java.util.logging.Logger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;

/*** This class is used to receive the message from other nodes.
 *
 */

public class ReceiveThread extends Thread{

    public static Logger logger = Logger.getLogger(ReceiveThread.class.getName());
    
    public static void ReceiveThread(String machineId,int receivePort)throws IOException{
        byte[] receiveBuf = new byte[1024];
        boolean receive = true;

        DatagramSocket ds_receive = new DatagramSocket(receivePort);
        DatagramPacket dp_receive = new DatagramPacket(receiveBuf, 1024);
        logger.info("The member"+machineId+"opens port"+receivePort+"for receiving message.");

        while(receive)
        {
            receiveBuf = dp_receive.getData();
        }

    }
    public static void introReceiveThread(String machineId,int receivePort, int location)throws IOException{


    }
}
