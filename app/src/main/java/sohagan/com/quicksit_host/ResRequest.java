package sohagan.com.quicksit_host;

/**
 * Created by Sam on 4/15/15.
 */
public class ResRequest {


    private String pName;
    private int pSize;
    private String resDate;
    private String resTime;

    public ResRequest(String name, int size, String date, String time){
        this.pName = name;
        this.pSize = size;
        this.resDate = date;
        this.resTime = time;
    }

    public String getpName() {
        return pName;
    }
    public void setpName(String pName) {
        this.pName = pName;
    }
    public int getpSize() {
        return pSize;
    }
    public void setpSize(int pSize) {
        this.pSize = pSize;
    }
    public String getResDate() {
        return resDate;
    }
    public void setResDate(String resDate) {
        this.resDate = resDate;
    }
    public String getResTime() {
        return resTime;
    }
    public void setResTime(String resTime) {
        this.resTime = resTime;
    }
}
