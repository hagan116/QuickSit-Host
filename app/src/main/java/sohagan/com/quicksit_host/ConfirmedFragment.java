package sohagan.com.quicksit_host;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ConfirmedFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    public ArrayList<String> values;
    private ListAdapter adapter;
    public ArrayList<Reservation> array = new ArrayList<Reservation>();

    private int rest_id;
    private int rest_take;

    //JSON NODES FOR RESERVATION HTTPGET
    private static final String TAG_RES_NAME = "name";
    private static final String TAG_RES_SIZE = "p_size";
    private static final String TAG_RES_DATE = "rest_data";
    private static final String TAG_RES_TIME = "rest_time";


    public static ConfirmedFragment newInstance(int param1, int param2) {
        ConfirmedFragment fragment = new ConfirmedFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ConfirmedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rest_id = getArguments().getInt(ARG_PARAM1);
            rest_take = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //CREATE FRAGMENT VIEWS
        View v = inflater.inflate(R.layout.fragment_confirmed, container, false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);

        //SET MARGINS
        final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources()
                .getDisplayMetrics());
        params.setMargins(margin, margin, margin, margin);
        v.setLayoutParams(params);
        v.setLayoutParams(params);

        if (rest_take == 1)
            yesReservs(v);
        else
            noReservs(v);

        fl.addView(v);
        return fl;
    }


    public void noReservs(View v){
        mListView = (ListView) v.findViewById(R.id.list2);

        Toast.makeText(getActivity().getApplicationContext(), "This Restaurant does not take reservations", Toast.LENGTH_SHORT).show();
    }

    public void yesReservs(View v){
        //RETRIEVE SWIPEREFRESHLAYOUT
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh2);

        //SET COLOR SCHEME FOR REFRESH
        mSwipeRefreshLayout.setColorSchemeColors(R.color.shittyRoses, R.color.white, R.color.shittyRoses, R.color.grey);

        //RETRIEVE THE LISTVIEW
        mListView = (ListView) v.findViewById(R.id.list2);

        array.add(new Reservation("Sam", 3, "4/16/15", "6:30PM"));
        array.add(new Reservation("Malcolm", 4, "4/16/15", "6:45PM"));
        array.add(new Reservation("Kevin", 2, "4/16/15", "7:00PM"));
        array.add(new Reservation("Sally", 2, "4/16/15", "7:15PM"));
        array.add(new Reservation("Matt", 6, "4/17/15", "8:00PM"));

        adapter = new ListAdapter(getActivity().getApplicationContext(), R.layout.accepted_listview_item, array);
        // Set the adapter between the ListView and its backing data.
        mListView.setAdapter(adapter);

        // BEGIN_INCLUDE (setup_refreshlistener)
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("REFRESH", "onRefresh called from SwipeRefreshLayout");
                mSwipeRefreshLayout.setRefreshing(true);
                initiateRefresh();
            }
        });
    }

    private void initiateRefresh() {
        Log.i("REFRESH", "initiateRefresh");

        //HTTPPOST TO DB TO REFRESH RESERVATIONS REQUEST
        new GetRequestsAsyncTask().execute(array);
    }
    private void onRefreshComplete(ArrayList<Reservation> result) {
        Log.i("REFRESH", "onRefreshComplete");

        // Remove all items from the ListAdapter, and then replace them with the new items
        adapter.clear();
        for (Reservation temp : result) {
            adapter.add(temp);
        }
        adapter.notifyDataSetChanged();

        // Stop the refreshing indicator
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private class GetRequestsAsyncTask extends AsyncTask<ArrayList<Reservation>, Void, ArrayList<Reservation>> {

        static final int TASK_DURATION = 2 * 1000; // 3 seconds

        @Override
        protected ArrayList<Reservation> doInBackground(ArrayList<Reservation>... params) {
            ArrayList<Reservation> temp = new ArrayList<Reservation>(params[0]);
            temp.add(new Reservation("Tom", 1, "4/18/15", "9:00PM"));

            // Sleep for a small amount of time to simulate a background-task
            try {
                Thread.sleep(TASK_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return temp;
        }

        @Override
        protected void onPostExecute(ArrayList<Reservation> result) {
            super.onPostExecute(result);

            // Tell the Fragment that the refresh has completed
            onRefreshComplete(result);
        }

    }


}
