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
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class ConfirmedFragment extends Fragment implements AdapterView.OnItemLongClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    public ArrayList<String> values;
    private ListAdapter2 adapter;
    public ArrayList<Reservation> array;

    //RESTAURANT BASIC DATA
    private int rest_id;
    private int rest_take;
    //RESERVATION ID
    private int reserve_id;

    //JSON NODES FOR RESERVATION HTTPGET
    private static final String TAG_RESERVATIONS = "reservations";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_RES_STATUS = "reserve_status";
    private static final String TAG_RES_ID = "reserve_id";
    private static final String TAG_RES_NAME = "reserve_name";
    private static final String TAG_RES_SIZE = "reserve_size";
    private static final String TAG_RES_DATE = "reserve_date";
    private static final String TAG_RES_TIME = "reserve_time";

    //URL TO POST CONFIRMED RESERVATION
    //private static String postRes_url = "http://cyberplays.com/quicksit/webservice/confirm_reservation.php";

    //CREATE RESERVATION JSON ARRAY
    JSONArray reservations = null;

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

        //INIT ARRAY AND GET RESERV REQUESTS FROM DB
        array = new ArrayList<>();
        new GetReservationsAsyncTask().execute();

        //DUMMY LOADING LIST ITEM
        array.add(new Reservation(5,"LOADING", 5, "05/05/15", "5:55PM"));

        //INIT + SET LISTVIEW ADAPTER
        adapter = new ListAdapter2(getActivity().getApplicationContext(), R.layout.accepted_listview_item, array);
        //Set the adapter between the ListView and its backing data.
        mListView.setAdapter(adapter);

        mListView.setOnItemLongClickListener(this);

        //BEGIN_INCLUDE (setup_refreshlistener)
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("REFRESH", "onRefresh called from SwipeRefreshLayout");
                initiateRefresh();
            }
        });
    }

    private void initiateRefresh() {
        Log.i("REFRESH", "initiateRefresh");
        mSwipeRefreshLayout.setRefreshing(true);
        //HTTPPOST TO DB TO REFRESH RESERVATIONS REQUEST
        new GetReservationsAsyncTask().execute();
        Toast.makeText(getActivity().getApplicationContext(), "Reservation removed.", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        reserve_id = array.get(position).getResId();

        array.remove(position); //where position is position of item you click
        adapter.notifyDataSetChanged();

        new ReservationReplyAsyncTask().execute();

        return false;
    }

    private class GetReservationsAsyncTask extends AsyncTask<Void, Void, ArrayList<Reservation>> {
        private InputStream is = null;
        private JSONObject jObj = null;
        private String json = null;
        private HttpResponse httpResponse = null;

        //URL TO GET RESERVATION REQUESTS
        final String url = "http://cyberplays.com/quicksit/webservice/pull_reservations.php";

        @Override
        protected ArrayList<Reservation> doInBackground(Void... voids) {
            //CALL getJSONFromURL to obtain JSONObject
            JSONObject json = getJSONFromUrl(url);

            //TO CHECK IF THE JSON IS NULL
            if (json == null)
                Log.d("JSON IS NULL:", "YES");
            else
                Log.d("All Reservations: ", json.toString());

            ArrayList<Reservation> temp = new ArrayList<>();

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Log.d("SUCCESS?", "YEAHH - 1");
                    //RESERVATIONS FOUND -> GETTING ARRAY OF RESERVATIONS
                    reservations = json.getJSONArray(TAG_RESERVATIONS);


                    //LOOP THROUGH RESERVS AND GET DATA TO CREATE RESERV OBJ
                    for (int i = 0; i < reservations.length(); i++) {
                        JSONObject c = reservations.getJSONObject(i);

                        //SEE IF ITS A PENDING RES OR CONFIRMED ONE (0=pending, 1=confirmed)
                        int reserv_status = c.getInt(TAG_RES_STATUS);

                        //STORE EACH JSON ITEM IN VARIABLE
                        if (reserv_status == 1){
                            int reserv_Id = c.getInt(TAG_RES_ID);
                            String reserv_name = c.getString(TAG_RES_NAME);
                            int reserv_size = c.getInt(TAG_RES_SIZE);
                            String reserv_date = c.getString(TAG_RES_DATE);
                            String reserv_time = c.getString(TAG_RES_TIME);

                            Reservation r = new Reservation(reserv_Id,reserv_name,reserv_size,reserv_date,reserv_time);
                            temp.add(r);
                        }
                    }
                } else {
                    //SUCCESS DID NOT RETURN 1. HTTPPOST FAIL
                    ArrayList<Reservation> fail = new ArrayList<>();
                    fail.add(new Reservation(5,"LOAD FAIL", 5, "05/05/15", "5:55PM"));
                    return fail;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //IF SUCCESSFUL BUT NO DATA WAS FOUND
            if (temp.size()==0)
                temp.add(new Reservation(5,"NO DATA", 5, "05/05/15", "5:55PM"));

            return temp;
        }

        @Override
        protected void onPostExecute(ArrayList<Reservation> result) {
            super.onPostExecute(result);
            //TELL THE FRAGMENT THAT THE REFRESH HAS COMPLETED
            onRefreshComplete(result);
        }

        //METHOD TO MAKE HTTPPOST AND OBTAIN RESERVATION JSON
        public JSONObject getJSONFromUrl(String url) {
            // Making HTTP request
            try {
                HttpParams params = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(params, 10000);
                HttpConnectionParams.setSoTimeout(params, 10000);
                HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
                HttpProtocolParams.setUseExpectContinue(params, true);
                //DEFAULT HTTP CLIENT
                DefaultHttpClient httpClient = new DefaultHttpClient(params);
                HttpPost httpPost = new HttpPost(url);

                //SET UP DATA FOR POST
                BasicNameValuePair restIdPair = new BasicNameValuePair("rest_id", Integer.toString(rest_id));
                ArrayList<NameValuePair> nameValuePairList = new ArrayList<>();
                nameValuePairList.add(restIdPair);

                //ENCODING POST DATA
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));

                httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            } catch (UnsupportedEncodingException ee) {
                Log.i("UnsupportedEncodingException...", is.toString());
            } catch (ClientProtocolException e) {
                Log.i("ClientProtocolException...", is.toString());
            } catch (IOException e) {
                Log.i("IOException...", is.toString());
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8); //old charset iso-8859-1
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                is.close();
                reader.close();
                json = sb.toString();
                Log.i("StringBuilder...", json);
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }
            // try parse the string to a JSON object
            try {
                jObj = new JSONObject(json);
                Log.i("TRY:", "1");
            } catch (Exception e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
                try {
                    jObj = new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
                    Log.i("TRY:", "2");
                } catch (Exception e0) {
                    Log.e("JSON Parser0", "Error parsing data [" + e0.getMessage() + "] " + json);
                    Log.e("JSON Parser0", "Error parsing data " + e0.toString());
                    try {
                        jObj = new JSONObject(json.substring(1));
                        Log.i("TRY:", "3");
                    } catch (Exception e1) {
                        Log.e("JSON Parser1", "Error parsing data [" + e1.getMessage() + "] " + json);
                        Log.e("JSON Parser1", "Error parsing data " + e1.toString());
                        try {
                            jObj = new JSONObject(json.substring(2));
                            Log.i("TRY:", "4");
                        } catch (Exception e2) {
                            Log.e("JSON Parser2", "Error parsing data [" + e2.getMessage() + "] " + json);
                            Log.e("JSON Parser2", "Error parsing data " + e2.toString());
                            try {
                                jObj = new JSONObject(json.substring(3));
                                Log.i("TRY:", "5");
                            } catch (Exception e3) {
                                Log.e("JSON Parser3", "Error parsing data [" + e3.getMessage() + "] " + json);
                                Log.e("JSON Parser3", "Error parsing data " + e3.toString());
                            }
                        }
                    }
                }
            }
            //return JSON String
            return jObj;
        }
    }

    //ASYNC TASK TO RETRIEVE ALL RESERVATIONS
    private class ReservationReplyAsyncTask extends AsyncTask<Void, Void, Integer> {
        final String url = "http://cyberplays.com/quicksit/webservice/remove_reservation.php";
        JSONObject json = null;

        @Override
        protected Integer doInBackground(Void... voids) {
            json = getJSONFromUrl(url);

            //TO CHECK IF THE JSON IS NULL
            if (json == null)
                Log.d("JSON IS NULL:", "YES");
            else
                Log.d("All Reservations: ", json.toString());

            int success = 0;
            try {
                // Checking for SUCCESS TAG
                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Log.d("SUCCESS?", "YEAHH");
                } else {
                    Log.d("SUCCESS?", "NOOOO");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return success;
        }

        @Override
        protected void onPostExecute(Integer success) {
            super.onPostExecute(success);
            //IF SUCCESSFUL -> REFRESH LISTVIEW
            initiateRefresh();
        }

        //METHOD TO MAKE HTTPPOST AND OBTAIN RETURNED JSON OF RESERVATIONS
        public JSONObject getJSONFromUrl(String url) {
            InputStream is = null;
            JSONObject jObj = null;
            String json = null;
            HttpResponse httpResponse;

            // Making HTTP request
            try {
                HttpParams params = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(params, 10000);
                HttpConnectionParams.setSoTimeout(params, 10000);
                HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
                HttpProtocolParams.setUseExpectContinue(params, true);
                //DEFAULT HTTP CLIENT
                DefaultHttpClient httpClient = new DefaultHttpClient(params);
                HttpPost httpPost = new HttpPost(url);

                //SET UP DATA FOR POST
                BasicNameValuePair restIdPair = new BasicNameValuePair("rest_id", Integer.toString(rest_id));
                BasicNameValuePair reserveIdPair = new BasicNameValuePair("reserve_id", Integer.toString(reserve_id));
                Log.d("RESERVE ID CHECK", Integer.toString(reserve_id));
                ArrayList<NameValuePair> nameValuePairList = new ArrayList<>();
                nameValuePairList.add(restIdPair);
                nameValuePairList.add(reserveIdPair);

                //ENCODING POST DATA
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));

                httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            } catch (UnsupportedEncodingException ee) {
                Log.i("UnsupportedEncodingException...", is.toString());
            } catch (ClientProtocolException e) {
                Log.i("ClientProtocolException...", is.toString());
            } catch (IOException e) {
                Log.i("IOException...", is.toString());
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8); //old charset iso-8859-1
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                is.close();
                reader.close();
                json = sb.toString();
                Log.i("StringBuilder...", json);
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }
            // try parse the string to a JSON object
            try {
                jObj = new JSONObject(json);
                Log.i("TRY:", "1");
            } catch (Exception e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
                try {
                    jObj = new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
                    Log.i("TRY:", "2");
                } catch (Exception e0) {
                    Log.e("JSON Parser0", "Error parsing data [" + e0.getMessage() + "] " + json);
                    Log.e("JSON Parser0", "Error parsing data " + e0.toString());
                    try {
                        jObj = new JSONObject(json.substring(1));
                        Log.i("TRY:", "3");
                    } catch (Exception e1) {
                        Log.e("JSON Parser1", "Error parsing data [" + e1.getMessage() + "] " + json);
                        Log.e("JSON Parser1", "Error parsing data " + e1.toString());
                        try {
                            jObj = new JSONObject(json.substring(2));
                            Log.i("TRY:", "4");
                        } catch (Exception e2) {
                            Log.e("JSON Parser2", "Error parsing data [" + e2.getMessage() + "] " + json);
                            Log.e("JSON Parser2", "Error parsing data " + e2.toString());
                            try {
                                jObj = new JSONObject(json.substring(3));
                                Log.i("TRY:", "5");
                            } catch (Exception e3) {
                                Log.e("JSON Parser3", "Error parsing data [" + e3.getMessage() + "] " + json);
                                Log.e("JSON Parser3", "Error parsing data " + e3.toString());
                            }
                        }
                    }
                }
            }
            //return JSON String
            return jObj;
        }
    }//END GET ASYNC


}
