package sohagan.com.quicksit_host;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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


public class HomeActivity extends FragmentActivity {

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;
    private int rest_id;
    String rest_name;
    int rest_reservation;
    int rest_wait;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_RESTAURANTS = "restaurants";
    private static final String TAG_REST_ID = "rest_id";
    private static final String TAG_REST_NAME = "rest_name";
    private static final String TAG_REST_WAIT = "rest_wait_time";
    private static final String TAG_REST_RES = "rest_res";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //HIDE ACTION BAR
        ActionBar bar = getActionBar();
        bar.hide();

        Intent i = getIntent();
        rest_id = i.getIntExtra("rest_id", 99);
        Log.d("REST ID RECEIVED", Integer.toString(rest_id));


        LoadAllRestaurants getAsyncTask = new LoadAllRestaurants();
        getAsyncTask.execute(rest_id);
    }

    public void initViews(){
        //INTIALIZE TABS AND THE PAGER ADAPTER TO CHANGE FRAGMENTS
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setShouldExpand(true);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        // SET PAGE VIEWER AND COLOR STYLING
        tabs.setViewPager(pager);
        tabs.setIndicatorColor(getResources().getColor(R.color.shittyRoses));
        tabs.setDividerColor(getResources().getColor(R.color.shittyRoses));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
        private final int[] ICONS = { R.drawable.ic_wait, R.drawable.ic_tables, R.drawable.ic_request, R.drawable.ic_confirmed};
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getPageIconResId(int position) {
            return ICONS[position];
        }

        @Override
        public int getCount() {
            return ICONS.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return WaitFragment.newInstance(rest_id,rest_wait);
                case 1:
                    return TablesFragment.newInstance("hey", "hey");
                case 2:
                    return RequestFragment.newInstance(rest_id, rest_reservation);
                case 3:
                    return ConfirmedFragment.newInstance(rest_id, rest_reservation);
                default:
                    return WaitFragment.newInstance(rest_id,rest_wait);
            }
        }

    }

    //Background Async Task to Load all restaurants by making HTTP Request
    class LoadAllRestaurants extends AsyncTask<Integer, String, String> {

        InputStream is = null;
        JSONObject jObj = null;
        String json = null;
        HttpResponse httpResponse = null;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(HomeActivity.this);
            pDialog.setMessage("Loading restaurant information. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        //GET ALL RESTAURANTS IN BACKGROUND THREAD
        protected String doInBackground(Integer... params) {
            rest_id = params[0];

            //CALL getJSONFromURL to obtain JSONObject
            String url = "http://www.cyberplays.com/quicksit/webservice/connect_and_pull_rests.php";
            JSONObject json = getJSONFromUrl(url);


            //TO CHECK IF THE JSON IS NULL
            if (json == null)
                Log.d("JSON IS NULL:", "YES");
            else {
                Log.d("JSON IS NULL:", "NO");
            }


            // Check your log cat for JSON reponse
            //Log.d("All Restaurants: ", json.toString());


            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Log.d("SUCCESS?", "YEAHH - 1");
                    // restaurants found
                    // Getting Array of Restaurants
                    JSONArray restaurants = json.getJSONArray(TAG_RESTAURANTS);
                    Log.d("JSON ARRAY",restaurants.toString());


                    // looping through All Restaurants and add to array
                    for (int i = 0; i < restaurants.length(); i++) {
                        JSONObject c = restaurants.getJSONObject(i);

                        // Storing each json item in variable
                        int temp_id = c.getInt(TAG_REST_ID);
                        if (rest_id == temp_id) {
                            //Log.d("GOT EEEEM", "THE RESTAURANT WE NEED");
                            rest_name = c.getString(TAG_REST_NAME);
                            rest_reservation = c.getInt(TAG_REST_RES);
                            rest_wait = c.getInt(TAG_REST_WAIT);
                            Log.d("THE WAIT", Integer.toString(rest_wait));
                        }
                    }
                } else {
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        //After completing background task Dismiss the progress dialog and set up UI
        @Override
        protected void onPostExecute(String nothing) {
            // DISMISS DIALOG AFTER THE DB HAS BEENN PULLED
            pDialog.dismiss();

            //INITIALIZE VIEWS
            initViews();
        }

        //METHOD TO MAKE HTTP REQUEST AND OBTAIN RESTAURANT JSON
        public JSONObject getJSONFromUrl(String url) {
            // Making HTTP request
            try {
                HttpParams params = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(params, 10000);
                HttpConnectionParams.setSoTimeout(params, 10000);
                HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
                HttpProtocolParams.setUseExpectContinue(params, true);
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient(params);
                HttpGet httpPost = new HttpGet(url);
                httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            } catch (UnsupportedEncodingException ee) {
                //Log.i("UnsupportedEncodingException...", is.toString());
            } catch (ClientProtocolException e) {
                //Log.i("ClientProtocolException...", is.toString());
            } catch (IOException e) {
                Log.i("IOException...", is.toString());
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "utf-8"), 8); //old charset iso-8859-1
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
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

            // return JSON String
            return jObj;

        }
    }

}
