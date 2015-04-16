package sohagan.com.quicksit_host;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class RequestFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    private ArrayAdapter<String> mListAdapter;
    public ArrayList<String> values;
    private int i = 1;

    private int rest_id;
    private int rest_take;

    //JSON NODES FOR RESERVATION HTTPGET
    private static final String TAG_RES_NAME = "name";
    private static final String TAG_RES_SIZE = "p_size";
    private static final String TAG_RES_DATE = "rest_data";
    private static final String TAG_RES_TIME = "rest_time";

    public static RequestFragment newInstance(int param1, int param2) {
        RequestFragment fragment = new RequestFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RequestFragment() {
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
        View v = inflater.inflate(R.layout.fragment_request, container, false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);

        //SET MARGINS
        final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        params.setMargins(margin, margin, margin, margin);
        v.setLayoutParams(params);
        v.setLayoutParams(params);


        //RETRIEVE SWIPEREFRESHLAYOUT
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh1);

        //SET COLOR SCHEME FOR REFRESH
        mSwipeRefreshLayout.setColorSchemeColors(R.color.shittyRoses, R.color.white, R.color.shittyRoses, R.color.grey);

        //RETRIEVE THE LISTVIEW
        mListView = (ListView) v.findViewById(R.id.list1);


        fl.addView(v);
        return fl;
    }


    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);


        if (rest_take ==1)
            yesReservs(v);
        else
            noReservs(v);


    }

    public void noReservs(View v){
        mListView.setVisibility(v.INVISIBLE);
        Toast.makeText(getActivity().getApplicationContext(),"This Restaurant does not take reservations", Toast.LENGTH_SHORT).show();
    }

    public void yesReservs(View v){
        values = new ArrayList<>();
        values.add("HEY");
        values.add("HEY");
        values.add("HEY");
        values.add("HEY");
        values.add("HEY");

        mListAdapter = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                values);

        // Set the adapter between the ListView and its backing data.
        mListView.setAdapter(mListAdapter);

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
        new GetRequestsAsyncTask().execute(i);
    }
    private void onRefreshComplete(ArrayList<String> result) {
        Log.i("REFRESH", "onRefreshComplete");

        // Remove all items from the ListAdapter, and then replace them with the new items
        mListAdapter.clear();
        for (String temp : result) {
            mListAdapter.add(temp);
        }
        mListAdapter.notifyDataSetChanged();

        // Stop the refreshing indicator
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private class GetRequestsAsyncTask extends AsyncTask<Integer, Void, ArrayList<String>> {

        static final int TASK_DURATION = 3 * 1000; // 3 seconds

        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            int ii = params[0];
            String iii = Integer.toString(ii);
            Log.d("ASYNC TASK PARAM:", iii);

            // Sleep for a small amount of time to simulate a background-task
            try {
                Thread.sleep(TASK_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //RETURN NEW ARRAY OF STRINGS
            ArrayList<String> temp = new ArrayList<>();
            for (int e = 0; e< i+3; e++){
                temp.add(iii);
            }
            i++;
            return temp;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);

            // Tell the Fragment that the refresh has completed
            onRefreshComplete(result);
        }

    }

}
