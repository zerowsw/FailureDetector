package CS425;

/***
 * This class is used to store information of Members in the Membership Group
 */
public class MemberInfo {


    private int location;
    private String memberId = "";
    private long lastActiveTime;
    private boolean isActive =true;




    public MemberInfo( String memberId,int location,long lastActiveTime, boolean isActive)
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

    public String getIsActive()
    {
        if(isActive == true)
        {
            return "YES";
        }else
        {
            return "NO";
        }
    }

    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
    }

}
