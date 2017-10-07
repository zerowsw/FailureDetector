package CS425;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class FailureDetect extends Thread {
    public static Logger logger = Logger.getLogger(FailureDetect.class);
    public static int failTime = 2000;
    public static long currentTime = System.currentTimeMillis();

    public void run() {
        //scan the entries of those node
        ConcurrentHashMap<String, MemberInfo> list = MemberGroup.membershipList;
        ArrayList<String> ips = new ArrayList<String>();
        //collect all the alive node ips
        for (Map.Entry<String, MemberInfo> entry : list.entrySet()) {
            if (entry.getValue().getIsActive()) {
                ips.add(entry.getValue().getIp());
            }
        }

        //sort by ip
        Collections.sort(ips);
        int size = ips.size();
        //collect all the ips that need to be scaned
        ArrayList<String> scans = new ArrayList<String>();
        int index = ips.indexOf(MemberGroup.machineIp);
        if (size <= 5) {
            for (String ip : ips) {
                if (!ip.equals(MemberGroup.machineIp)) {
                    scans.add(ip);
                }
            }
        } else {
            scans.add(ips.get((index + 1) % size));
            scans.add(ips.get((index + 2) % size));

            int newIndex = index - 1;
            newIndex = newIndex < 0 ? newIndex + size : newIndex;
            scans.add(ips.get(newIndex));
            newIndex = index - 2;
            newIndex = newIndex < 0 ? newIndex + size : newIndex;
            scans.add(ips.get(newIndex));
        }

        for (Entry<String, MemberInfo> entry: MemberGroup.membershipList.entrySet()) {
            if (scans.contains(entry.getValue().getIp())) {
                if (currentTime - entry.getValue().getActiveTime() > failTime) {
                    //set the state of the node false
                    entry.getValue().setIsActive(false);
                    //disseminate the failure
                    for (String ip : ips) {
                        if (ip != MemberGroup.machineIp && ip != entry.getValue().getIp()) {
                            MemberGroup.singleRequest(ip, "disseminate", entry.getKey());
                        }
                    }
                }
            }
        }
    }
}
