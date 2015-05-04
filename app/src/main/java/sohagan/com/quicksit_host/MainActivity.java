package sohagan.com.quicksit_host;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class MainActivity extends Activity implements TextView.OnEditorActionListener{

    Button login, skip;
    EditText username, pass;
    String name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //HIDE ACTIONBAR
        ActionBar bar = getActionBar();
        bar.hide();

        //INIT VIEWS
        initViews();
    }


    public void initViews() {

        username = (EditText) findViewById(R.id.username);
        username.setOnEditorActionListener(this);

        pass = (EditText) findViewById(R.id.passwordMain);
        pass.setOnEditorActionListener(this);

        login = (Button) findViewById(R.id.login);
        login.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //STYLE FOR TOUCH
                    login.setBackgroundColor(getResources().getColor(R.color.white));
                    login.setTextColor(getResources().getColor(R.color.shittyRoses));

                    name = username.getText().toString();
                    if (name.length() == 0) {
                        Toast.makeText(getApplicationContext(), "Please enter a username", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    password = pass.getText().toString();
                    if (password.length() == 0) {
                        Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    return false;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //STYLE FOR UNTOUCH
                    login.setBackgroundColor(getResources().getColor(R.color.shittyRoses));
                    login.setTextColor(getResources().getColor(R.color.white));
                    return false;
                }
                new AuthUserAsyncTask().execute(name,password);
                return true;
            }
        });

        skip = (Button) findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                i.putExtra("rest_id", 2);
                startActivity(i);
            }
        });

    }

    //TO HIDE KEYBOARD..smh
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(v.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            // Must return true here to consume event
            return true;
        }
        return false;
    }


    //ASYNC TASK TO POST WIAT TIME TO DB
    class AuthUserAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];

            HttpClient httpClient = new DefaultHttpClient();

            // In a POST request, we don't pass the values in the URL.
            //Therefore we use only the web page URL as the parameter of the HttpPost argument
            final String url = "http://cyberplays.com/quicksit/webservice/authorize_login.php";
            HttpPost httpPost = new HttpPost(url);

            // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
            //uniquely separate by the other end.
            //To achieve that we use BasicNameValuePair
            //Things we need to pass with the POST request
            BasicNameValuePair user = new BasicNameValuePair("username", username);
            BasicNameValuePair pass = new BasicNameValuePair("password", password);

            // We add the content that we want to pass with the POST request to as name-value pairs
            //Now we put those sending details to an ArrayList with type safe of NameValuePair
            ArrayList<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            nameValuePairList.add(user);
            nameValuePairList.add(pass);

            //Encoding POST data
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
            } catch (UnsupportedEncodingException e) {
                // log exception
                e.printStackTrace();
            }

            //making POST request.
            HttpResponse httpResponse;
            try {
                httpResponse = httpClient.execute(httpPost);
                // write response to log
                Log.d("Http Post Response:", httpResponse.toString());
                try {
                    String responseText = EntityUtils.toString(httpResponse.getEntity());
                    Log.d("RESPONSE",responseText);
                    return responseText;
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.i("Parse Exception", e + "");
                }
            } catch (ClientProtocolException e) {
                // Log exception
                e.printStackTrace();
            } catch (IOException e) {
                // Log exception
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                int success = jsonObject.getInt("success");

                if (success == 1) {
                    int rest_id = jsonObject.getInt("login_id");
                    Log.d("REST__ID TO PASS", Integer.toString(rest_id));
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    i.putExtra("rest_id", rest_id);
                    startActivity(i);
                } else if (success == 0) {
                    Toast.makeText(getApplicationContext(), "Invalid Login Information", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
