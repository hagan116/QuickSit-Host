package sohagan.com.quicksit_host;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;



public class MainActivity extends Activity {

    Button login, register;
    EditText username, pass;
    String name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }


    public void initViews() {

        username = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.passwordMain);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(), homepageActivity.class);
                startActivity(i);
            }
        });

        register = (Button) findViewById(R.id.registerMain);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(), registerActivity.class);
                startActivity(i);
            }
        });
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
