package CS425;

import java.io.IOException;
import java.util.logging.Logger;

public class FailureDetect {
    public static Logger logger = Logger.getLogger(FailureDetect.class.getName());


    public void FailureDetect() throws IOException{
        // TODO
        // If both the successors and predecessor don't receive the heartbeats,
        //which means the lastActiveTime in those membership list minus current time >3s
        //then turn the isActive into false
    }

}