package sohagan.com.quicksit_host;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by malcolmnavarro on 2/3/15.
 */
public class ListAdapter extends ArrayAdapter<Reservation> implements View.OnTouchListener {
    private final Context context;
    private ArrayList<Reservation> reservations = new ArrayList<>();
    private final int resource;
    private LayoutInflater mInflater;
    private final Object mLock = new Object();
    public RequestFragment frag = null;

    private TextView res_name, res_date, res_time, res_size;
    private Button yesButton, noButton;

    //LIST ADAPTER CONSTRUCTOR
    public ListAdapter(Context context, int resource, ArrayList<Reservation> reservations, RequestFragment frag) {
        super(context, resource, reservations);
        this.context = context;
        this.resource = resource;
        this.reservations = reservations;
        this.frag = frag;

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

    //INITIALIZES AND SETS VIEWS
    private View createViewFromResource(int position, View convertView,
                                        ViewGroup parent, int resource) {
        View v;
        if (convertView == null) {
            v = mInflater.inflate(resource, parent, false);
            res_name = (TextView) v.findViewById(R.id.list_item_name1);

            res_date = (TextView) v.findViewById(R.id.list_item_date1);

            res_time = (TextView) v.findViewById(R.id.list_item_time1);

            res_size = (TextView) v.findViewById(R.id.list_item_size1);

            yesButton = (Button) v.findViewById(R.id.yes);

            noButton = (Button) v.findViewById(R.id.no);
        } else {
            v = convertView;
        }

        //SET TEXT OF TEXT VIEWS
        res_name.setText(reservations.get(position).getpName());

        res_date.setText(reservations.get(position).getResDate());

        res_time.setText(reservations.get(position).getResTime());

        res_size.setText("( " + reservations.get(position).getpSize() + " )");

        //SET BUTTON ONCLICK LISTENER
        yesButton.setOnTouchListener(this);
        yesButton.setTag(new Pair(1,reservations.get(position).getResId()));

        noButton.setOnTouchListener(this);
        noButton.setTag(new Pair(0,reservations.get(position).getResId()));

        return v;
    }


    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    //ADD RESERVATIONT TO LIST
    @Override
    public void add(Reservation object) {
        synchronized (mLock) {
            if (reservations != null) {
                reservations.add(object);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Pair p = (Pair) v.getTag();
        int type = p.getX();
        int reserve_id = p.getY();
        //Log.d("type & reservation id: ", Integer.toString(type) + " " + Integer.toString(reserve_id));

        if (type==0){
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //STYLE FOR TOUCH
                //noButton.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
                //noButton.setTextColor(getActivity().getResources().getColor(R.color.shittyRoses));
                Log.d("NO BUTTON:", " CLICK");
                frag.denyReservation(reserve_id);
                //new NoAsyncPost().execute();
                return false;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                //STYLE FOR UNTOUCH
                //noButton.setBackgroundColor(getResources().getColor(R.color.shittyRoses));
                //noButton.setTextColor(getResources().getColor(R.color.white));
                return false;
            }
        }

        if (type==1) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //STYLE FOR TOUCH
                //yesButton.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
                //yesButton.setTextColor(getActivity().getResources().getColor(R.color.shittyRoses));
                Log.d("YES BUTTON:", " CLICK");
                frag.confirmReservation(reserve_id);
                return false;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                //STYLE FOR UNTOUCH
                //yesButton.setBackgroundColor(getResources().getColor(R.color.shittyRoses));
                //yesButton.setTextColor(getResources().getColor(R.color.white));
                return false;
            }
        }
        return true;
    }

    private class Pair {
        int x, y;
        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int getX(){ return x;}
        public int getY(){ return y;}
    }
}
