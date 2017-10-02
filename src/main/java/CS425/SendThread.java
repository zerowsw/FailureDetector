package CS425;

import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Logger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/***
 * This class is used to send messages to introducer, successor, predecessor
 */

public class SendThread extends Thread {
    public static Logger logger = Logger.getLogger(SendThread.class.getName());
    private static String heartbeat;
    public static void SendThread(String machineId,int sendPort)throws IOException {
        byte[] sendBuf = new byte[1024];
        InetAddress sendAddress=InetAddress.getLocalHost();
        DatagramSocket ds = new DatagramSocket(8088);
        DatagramPacket dp_send= new DatagramPacket(heartbeat.getBytes(),heartbeat.length(),sendAddress,sendPort);
        ds.send(dp_send);
    }
    public static void introSendThread(String machineId,int sendPort, int location)throws IOException {

    }
}
