package CS425;

/***
 * This class is used to store information of Members in the Membership Group
 */
public class MemberInfo {


    private int location;
    private String memberId = "";
    private long lastActiveTime;
    private boolean isActive =true;




    public MemberInfo(int location, String memberId,long lastActiveTime, boolean isActive)
    {
        this.location = location;
        this.memberId = memberId;
        this.lastActiveTime = lastActiveTime;
        this.isActive = isActive;
    }


    // here we define some function to get or set the members' information
    public int getmemberLocation()
    {

        return location;
    }

    public void setMemberLocation()
    {
        this.location =  location;
    }

    public String getMemberId()
    {
        return memberId;
    }

    public void setMemberId(String machineId)
    {
        this.memberId = memberId;
    }

    public long getActiveTime()
    {
        return lastActiveTime;
    }

    public void setLastActiveTime(long lastActiveTime)
    {
        this.lastActiveTime = lastActiveTime;
    }
    public boolean isActive()
    {
        return isActive;
    }
    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
    }
    public void setInactive(boolean isActive)
    {
        this.isActive = false;
    }
}
