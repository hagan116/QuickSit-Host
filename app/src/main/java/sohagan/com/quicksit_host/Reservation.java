package sohagan.com.quicksit_host;

public class Reservation {


    private String pName;
    private int pSize;
    private String resDate;
    private String resTime;


    public Reservation(String name, int size, String date, String time){
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
