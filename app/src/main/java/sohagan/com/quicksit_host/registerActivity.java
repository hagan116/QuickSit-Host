package sohagan.com.quicksit_host;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.location.Location;

import java.io.IOException;
import java.util.List;


public class registerActivity extends Activity implements AdapterView.OnItemSelectedListener{
    Button register;
    EditText rest, pass, add;
    Spinner genre;
    String name, password, type, address;
    Location restaurantLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
    }

    public void initViews() {
        register = (Button) findViewById(R.id.register);
        register.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Geocoder geocoder = new Geocoder(getApplicationContext());
                List<Address> addresses;

                address = add.getText().toString();
                if (address.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter an address", Toast.LENGTH_SHORT).show();
                    return false;
                }
                try {
                    addresses = geocoder.getFromLocationName(address, 1);
                    Address restaurantAddress = addresses.get(0);

                    restaurantLocation = new Location("");
                    restaurantLocation.setLatitude(restaurantAddress.getLatitude());
                    restaurantLocation.setLongitude(restaurantAddress.getLongitude());
                }
                catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                name = rest.getText().toString();
                if (name.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter your restaurant name", Toast.LENGTH_SHORT).show();
                    return false;
                }
                password = pass.getText().toString();
                if (password.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter a password", Toast.LENGTH_SHORT).show();
                    return false;
                }
                Restaurant host = new Restaurant(name, type, password, restaurantLocation);
                Intent i = new Intent(getApplicationContext(), homepageActivity.class);
                startActivity(i);

                return true;
            }
        });

        rest = (EditText) findViewById(R.id.brand);
        pass = (EditText) findViewById(R.id.password);
        add = (EditText) findViewById(R.id.addressField);

        genre = (Spinner) findViewById(R.id.genre);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genres_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genre.setAdapter(adapter);
        genre.setOnItemSelectedListener(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        type = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
