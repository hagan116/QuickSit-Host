package sohagan.com.quicksit_host;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.util.TypedValue;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class WaitFragment extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button waitButt;
    private TextView waitNum;
    private int rest_id = 1, wait_time;


    public static WaitFragment newInstance(int id, int wait) {
        WaitFragment fragment = new WaitFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        args.putInt(ARG_PARAM2, wait);
        fragment.setArguments(args);
        return fragment;
    }

    public WaitFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rest_id = getArguments().getInt(ARG_PARAM1);
            wait_time = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wait, container, false);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);

        final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
                .getDisplayMetrics());

        params.setMargins(margin, margin, margin, margin);

        //INITIALIZE WAIT TIME AND SET TIME OBTAINED FROM DB
        waitNum = (TextView) v.findViewById(R.id.wait_num);
        waitNum.setText(Integer.toString(wait_time));

        waitButt = (Button) v.findViewById(R.id.wait_butt);
        waitButt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //STYLE FOR TOUCH
                    waitButt.setBackgroundColor(getActivity().getResources().getColor(R.color.shittyRoses));
                    waitButt.setTextColor(getActivity().getResources().getColor(R.color.white));

                    openWaitDialog();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //STYLE FOR UNTOUCH
                    waitButt.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                    waitButt.setTextColor(getActivity().getResources().getColor(R.color.shittyRoses));
                }
                return false;
            }
        });

        return v;
    }


    public void openWaitDialog() {
        //INFLATE LAYOUT AND INITIALIZE DIALOG
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity().getApplicationContext());
        View dialogView = layoutInflater.inflate(R.layout.dialog_setwait, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setView(dialogView);
        //INITIALIZE EDIT TEXT
        final EditText input = (EditText) dialogView.findViewById(R.id.dialog_setwait_setwait);

        //SETUP DIALOG WINDOW
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //GET USER INPUT FOR WAIT TIME
                        Editable inputWait = input.getText();
                        waitNum.setText(inputWait);

                        int wait = Integer.parseInt(inputWait.toString());
                        //HTTPOST THE WAIT TIME
                        //PostWaitAsyncTask postWait = new PostWaitAsyncTask();
                        //postWait.execute(wait, rest_id);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,	int id) {
                                dialog.cancel();
                            }});

        //CREATE THE ALERT DIALOG
        AlertDialog alert = alertDialogBuilder.create();
        // SHOW THE ALERT
        alert.show();
    }

    class PostWaitAsyncTask extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... params) {
            int wait = params[0];
            int rest_id = params[1];
            Log.d("ASYNC PARAMS:", Integer.toString(wait));
            Log.d("ASYNC PARAMS:", Integer.toString(rest_id));

            HttpClient httpClient = new DefaultHttpClient();

            // In a POST request, we don't pass the values in the URL.
            //Therefore we use only the web page URL as the parameter of the HttpPost argument
            HttpPost httpPost = new HttpPost("http://www.cyberplays.com/somephpscript.php");

            // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
            //uniquely separate by the other end.
            //To achieve that we use BasicNameValuePair
            //Things we need to pass with the POST request
            BasicNameValuePair restaurantIdPair = new BasicNameValuePair("rest_id", Integer.toString(rest_id));
            BasicNameValuePair waitTimePair = new BasicNameValuePair("wait_time", Integer.toString(wait));

            // We add the content that we want to pass with the POST request to as name-value pairs
            //Now we put those sending details to an ArrayList with type safe of NameValuePair
            ArrayList<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            nameValuePairList.add(waitTimePair);
            nameValuePairList.add(restaurantIdPair);

            try {
                // UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs.
                //This is typically useful while sending an HTTP POST request.
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);

                // setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
                httpPost.setEntity(urlEncodedFormEntity);

                try {
                    // HttpResponse is an interface just like HttpPost.
                    //Therefore we can't initialize them
                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    // According to the JAVA API, InputStream constructor do nothing.
                    //So we can't initialize InputStream although it is not an interface
                    InputStream inputStream = httpResponse.getEntity().getContent();

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder stringBuilder = new StringBuilder();

                    String bufferedStrChunk = null;

                    while((bufferedStrChunk = bufferedReader.readLine()) != null){
                        stringBuilder.append(bufferedStrChunk);
                    }

                    return stringBuilder.toString();

                } catch (ClientProtocolException cpe) {
                    System.out.println("First Exception caz of HttpResponese :" + cpe);
                    cpe.printStackTrace();
                } catch (IOException ioe) {
                    System.out.println("Second Exception caz of HttpResponse :" + ioe);
                    ioe.printStackTrace();
                }

            } catch (UnsupportedEncodingException uee) {
                System.out.println("An Exception given because of UrlEncodedFormEntity argument :" + uee);
                uee.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result.equals("working")){
                Toast.makeText(getActivity().getApplicationContext(), "HTTP POST is working...", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getActivity().getApplicationContext(), "Invalid POST req...", Toast.LENGTH_LONG).show();
            }
        }
    }


}