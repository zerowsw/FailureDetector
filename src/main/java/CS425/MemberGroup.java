package CS425;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;

/***
 * This is the entry of the project, where nodes join into the group and
 *   generate their membership lists.
 */


public class MemberGroup {

    public static Logger logger = Logger.getLogger(MemberGroup.class.getName());

    // set the send port and receive port of every potential member
    public final static int sendPort = 8080;
    public final static int receivePort = 8088;

    //set the introducer's Ip and location
    public static String introducerIp = "192.168.1.1";
    public static int introducerLocation = 1;

    //define some variable of machine
    public static String machineIp = "";
    public static String machineId = "";
    public static int machineLocation;
    public static long currentTime = System.currentTimeMillis();
    public static String machineTimestamp = String.valueOf(currentTime);

    //define the action of member
    public static String memberAction = "";

    // here we use hashmap to store the information of membership list
    public static HashMap<String, MemberInfo> membershipList = new HashMap<String, MemberInfo>();

    public static void main(String[] args) throws IOException {

        // create a file to store log
        logEntry();


        // ask the member to choose its action
        do {
            System.out.println("\nYou can choose the following action: ");
            System.out.println("\nEnter 'membership' to list membership list");
            System.out.println("\nEnter 'id' to list your id ");
            System.out.println("\nEnter 'join' to join the group ");
            System.out.println("\nEnter 'leave' to leave the group ");

            InputStreamReader is_reader = new InputStreamReader(System.in);
            memberAction = new BufferedReader(is_reader).readLine();
        } while (memberAction.equals(""));

        // act accroding to member's action
        if(memberAction.equalsIgnoreCase("membership"))
        {
            listMembership();
        }
        else if(memberAction.equalsIgnoreCase("id"))
        {
            listMemberId();
        }
        else if(memberAction.equalsIgnoreCase("join"))
        {
            joinGroup();
        }
        else if(memberAction.equalsIgnoreCase("leave"))
        {
            leaveGroup();
        }

    }



    //This function is used to create a log file which records all the log message.

    public static boolean logEntry() {
        try {
            boolean append = true;
            FileHandler handler = new FileHandler("CS425_MP2.log", append);
            logger.addHandler(handler);
            logger.info("CS425_MP2 log file has been created!");
            return true;
        } catch (Exception e) {
            logger.warning("CS425_MP2 log file cannot be created!");
            return false;
        }

    }

    // This function is used to check whether the current machine is the introducer.
    public static boolean isIntroducer() throws IOException {
        machineIp = InetAddress.getLocalHost().getHostAddress().toString();
        if (machineIp == introducerIp) {
            return true;
        } else {
            return false;
        }
    }

    // This function is used to send UDP package to the introducer
    public static void greetingIntroducer(String machineId) throws IOException {
        String message = "greeting";
        InetAddress sendAddress=InetAddress.getLocalHost();
        DatagramSocket ds = new DatagramSocket(8088);
        DatagramPacket dp_send= new DatagramPacket(message.getBytes(),message.length(),sendAddress,sendPort);
        ds.send(dp_send);

    }

    public static void listMembership() throws IOException {

        if(membershipList.isEmpty()){
            System.out.println("Please join the group first!");
            return;
        }else
        {
            for(Entry<String, MemberInfo> entry : membershipList.entrySet())
            {
                MemberInfo list = entry.getValue();
                machineIp = InetAddress.getLocalHost().getHostAddress().toString();
                machineId = machineIp + machineTimestamp;
                System.out.println("Your membership list is as follows:");
                System.out.println("MemberId      Location      LastActiveTime   IsActive");
                System.out.println(entry.getKey() + list.getmemberLocation()+list.getActiveTime()+list.getIsActive());
                logger.info("Member"+machineId+"is viewing its membership list.");
                logger.info("The membershiplist is as follows:");
                logger.info(entry.getKey() + list.getmemberLocation()+list.getActiveTime()+list.getIsActive());
            }
        }
    }

    public static void listMemberId() throws IOException {

        String memberId;
        String memberIp;
        memberIp = InetAddress.getLocalHost().getHostAddress().toString();
        memberId = memberIp + machineTimestamp;
        System.out.println("Your id is :"+memberId);
        logger.info("The member "+ memberId+"is viewing its id.");
    }

    public static void joinGroup() throws IOException {
        // join the current machine into the distributed group membership
        if (isIntroducer()) {
            machineId = introducerIp + machineTimestamp;

            //start receiving messages from other members

            ReceiveThread.introReceiveThread(machineId, receivePort, introducerLocation);

            //send request to all members and ask them to send heartbeats
            SendThread.introSendThread(machineId, sendPort, introducerLocation);

        } else {

            // create machine id including machine IP address and timestamp
            machineIp = InetAddress.getLocalHost().getHostAddress().toString();
            machineId = machineIp + machineTimestamp;

            //send UDP package to introducer
            greetingIntroducer(machineId);

            //start receiving messages from other members
            ReceiveThread.ReceiveThread(machineId, receivePort);
        }


        ScheduledExecutorService sendScheduler = Executors.newScheduledThreadPool(4);

        //before send heartbeat, set to detect the failure regularly
        sendScheduler.scheduleAtFixedRate(new FailureDetect(machineId), 0, 500, TimeUnit.SECONDS);

        Runnable sendHeartbeat = new Runnable() {
            public void run() {
                try {
                    SendThread.SendThread(machineId, sendPort);
                } catch (Exception ex) {
                    logger.warning("Failed to send message");
                }
            }
        };

        //set to send heartbeat regularly
        sendScheduler.scheduleAtFixedRate(sendHeartbeat, 0, 500, TimeUnit.SECONDS);



    }

    public static void leaveGroup() throws IOException {
        String message = "leaving";
        InetAddress sendAddress=InetAddress.getLocalHost();
        DatagramSocket ds = new DatagramSocket(8088);
        DatagramPacket dp_send= new DatagramPacket(message.getBytes(),message.length(),sendAddress,sendPort);
        ds.send(dp_send);
    }

}
