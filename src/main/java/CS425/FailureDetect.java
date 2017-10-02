package CS425;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.Map.Entry;

public class FailureDetect extends Thread {
    public static Logger logger = Logger.getLogger(FailureDetect.class.getName());
    public static int failTime = 2000;
    public static long currentTime = System.currentTimeMillis();

    public FailureDetect(String memberId) throws IOException{
        // If both the successors and predecessor don't receive the heartbeats,
        //which means the lastActiveTime in those membership list minus current time >2000
        //then turn the isActive into false
        for(Entry<String, MemberInfo> entry : MemberGroup.membershipList.entrySet())
        {
            MemberInfo list = entry.getValue();

            // set the member which is inactive for a failTime unit to inactive
            if(entry.getKey()!= memberId)
            {
                if(list.getIsActive().equalsIgnoreCase("YES")& ((currentTime- list.getActiveTime())>failTime))
                {
                    list.setActive(false);
                    logger.info("The member "+entry.getKey()+"has failed");
                }
            }
        }


    }

}
