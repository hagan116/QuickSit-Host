package sohagan.com.quicksit_host;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by malcolmnavarro on 2/3/15.
 */
public class ListAdapter extends ArrayAdapter<Reservation> {
    private final Context context;
    private ArrayList<Reservation> reservations = new ArrayList<>();
    private final int resource;
    private LayoutInflater mInflater;
    private final Object mLock = new Object();

    private TextView res_name, res_date, res_time, res_size, res_until;

    public ListAdapter(Context context, int resource, ArrayList<Reservation> reservations) {
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
            res_name = (TextView) v.findViewById(R.id.list_item_name1);

            res_date = (TextView) v.findViewById(R.id.list_item_date1);

            res_time = (TextView) v.findViewById(R.id.list_item_time1);

            res_size = (TextView) v.findViewById(R.id.list_item_size1);
        } else {
            v = convertView;
        }

        res_name.setText(reservations.get(position).getpName());

        res_date.setText(reservations.get(position).getResDate());

        res_time.setText(reservations.get(position).getResTime());

        res_size.setText("( " + reservations.get(position).getpSize() + " )");

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
