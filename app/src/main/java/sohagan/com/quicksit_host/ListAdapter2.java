package sohagan.com.quicksit_host;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.Calendar;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class ListAdapter2 extends ArrayAdapter<Reservation> {
    private final Context context;
    private ArrayList<Reservation> reservations = new ArrayList<>();
    private final int resource;
    private LayoutInflater mInflater;
    private final Object mLock = new Object();

    private TextView res_name, res_date, res_time, res_size, res_until;


    public ListAdapter2(Context context, int resource, ArrayList<Reservation> reservations) {
        super(context, resource, reservations);
        this.context = context;
        this.resource = resource;
        this.reservations = reservations;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return reservations.size();
    }

    @Override
    public Reservation getItem(int position) {
        return reservations.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, resource);
    }

    private View createViewFromResource(int position, View convertView,
                                        ViewGroup parent, int resource) {
        View v;
        if (convertView == null) {
            v = mInflater.inflate(resource, parent, false);
            res_name = (TextView) v.findViewById(R.id.list_item_name2);

            res_date = (TextView) v.findViewById(R.id.list_item_date2);

            res_time = (TextView) v.findViewById(R.id.list_item_time2);

            res_size = (TextView) v.findViewById(R.id.list_item_size2);

            res_until = (TextView) v.findViewById(R.id.list_item_until2);
        } else {
            v = convertView;
        }

        res_name.setText(reservations.get(position).getpName());

        res_date.setText(reservations.get(position).getResDate());

        res_time.setText(reservations.get(position).getResTime());

        res_size.setText("( " + reservations.get(position).getpSize() + " )");

        Calendar now = Calendar.getInstance();
        Calendar resDateTime = Calendar.getInstance();
        resDateTime.clear();
        String rDate = reservations.get(position).getResDate();
        if (!rDate.equals("05/05/15")) { //dummy value, ignore
            String []rDateArray = rDate.split(",");
            String partialDate = rDateArray[0];
            String year = rDateArray[1];
            int yearInt = Integer.parseInt(year.split(" ")[1]);
            String []rDateArray2 = partialDate.split(" ");
            String monthString = rDateArray2[0];
            int monthInt = 0;
            switch (monthString.toLowerCase()){
                case ("january"):{
                    monthInt = 1;
                    break;
                }
                case ("february"):{
                    monthInt = 2;
                    break;
                }
                case ("march"):{
                    monthInt = 3;
                    break;
                }
                case ("april"):{
                    monthInt = 4;
                    break;
                }
                case ("may"):{
                    monthInt = 5;
                    break;
                }
                case ("june"):{
                    monthInt = 6;
                    break;
                }
                case ("july"):{
                    monthInt = 7;
                    break;
                }
                case ("august"):{
                    monthInt = 8;
                    break;
                }
                case ("september"):{
                    monthInt = 9;
                    break;
                }
                case ("october"):{
                    monthInt = 10;
                    break;
                }
                case ("november"):{
                    monthInt = 11;
                    break;
                }
                case ("december"):{
                    monthInt = 12;
                    break;
                }
            }
            String dayString = rDateArray2[1];
            int dayInt = Integer.parseInt(dayString);


            String rTime = reservations.get(position).getResTime();
            String []timeSplit = rTime.split(":");
            String hourString = timeSplit[0];
            String minuteString = timeSplit[1];
            int hourInt = Integer.parseInt(hourString);
            int minuteInt = Integer.parseInt(minuteString);
            resDateTime.set(yearInt, monthInt, dayInt, hourInt, minuteInt);
            LocalTime currentDate = LocalTime.fromCalendarFields(now);
            LocalTime reservationDate = LocalTime.fromCalendarFields(resDateTime);
            int minutes = Minutes.minutesBetween(currentDate, reservationDate).getMinutes();
            if (minutes < 0){
                minutes = 0;
            }
            int hours = minutes/60;
            int days = hours/24;
            minutes = minutes % 60;
            hours = hours % 24;
            String timeBetween = (days + " Days, " + hours + "Hrs, " + minutes + " Mins.");
            //FIND A WAY TO CALCULATE TIME UNTIL RESERVATION
            res_until.setText(timeBetween);
        }
        return v;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public void add(Reservation object) {
        synchronized (mLock) {
            if (reservations != null) {
                reservations.add(object);
            }
        }
        notifyDataSetChanged();
    }


}
