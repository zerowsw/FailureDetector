package CS425;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/***
 * This is the entry of the project, where nodes join into the group and
 *   generate their membership lists.
 */


public class NodesGroup {

    public static Logger logger = Logger.getLogger(NodesGroup.class.getName());

    // set the send port and receive port of every potential member
    public final static int sendPort = 8080;
    public final static int receivePort= 8088;

    //set the introducer's Ip and location
    public static String introducerIp = "192.168.1.1";
    public static int introducerLocation = 1;

    //define the variable in the membership
    public static String machineIp = "";
    public static String machineId = "";
    public static int machineLocation;
    public static long currentTime = System.currentTimeMillis();
    public static String machineTimestamp = String.valueOf(currentTime);



    public static void main(String[] args) throws IOException {

        Thread Receive;
        // create a log file
        logEntry();

        // join the current machine into the distributed group membership
        if(isIntroducer())
        {
            machineId = machineIp + machineTimestamp;
            machineLocation = introducerLocation;
            //TODO
        }else{
            // create machine id including machine IP address and timestamp
            machineIp = InetAddress.getLocalHost().getHostAddress().toString();
            machineId = machineIp + machineTimestamp;
            machineLocation ++;

            //start receiving messages from other members
            Receive = new ReceiveThread(machineId, receivePort);
            Receive.start();

            //set to send messages regularly
            ScheduledExecutorService sendScheduler = Executors.newScheduledThreadPool(4);
            sendScheduler.scheduleAtFixedRate(new SendThread(machineId,sendPort), 0, 500, TimeUnit.SECONDS);

            //send UDP package to introducer
            greetingIntroducer(machineId);

        }




    }


    //This function is used to create a log file which records all the log message.

    public static boolean logEntry(){
        try {
            boolean append = true;
            FileHandler handler = new FileHandler("CS425_MP2.log", append);
            logger.addHandler(handler);
            logger.info("CS425_MP2 log file has been created!");
            return true;
        }catch (Exception e)
        {
            logger.warning("CS425_MP2 log file cannot be created!");
            return false;
        }

    }

    // This function is used to check whether the current machine is the introducer.
    public static boolean isIntroducer()throws IOException {
        machineIp = InetAddress.getLocalHost().getHostAddress().toString();
        if (machineIp == introducerIp) {
            return true;
        } else {
            return false;
        }
    }
    // This function is used to send UDP package to the introducer i
    public static void greetingIntroducer(String machineId) throws IOException{
        //TODO

    }

}
