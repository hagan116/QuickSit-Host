package sohagan.com.quicksit_host;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import at.markushi.ui.CircleButton;


public class TablesFragment extends Fragment implements View.OnClickListener{

    //THE FRAGMENT PARAMETERS ARE USELESS IN THIS FRAGMENT  (DONT NEED ANY DATA)
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button t1,t2,t3,t4,t7;
    private CircleButton t5,t6;
    private Spinner spinner;
    private TextView total,tables;
    private String choice;
    private int current = 24; // the current number of tables in use
    private int twos = 4;
    private int fours = 2;
    private int eights = 1;
    private boolean[] inUse = new boolean[7]; // each slot corresponds to whether or not a table is in use

    private String tabName;
    private String mParam2;

    public static TablesFragment newInstance(String param1, String param2) {
        TablesFragment fragment = new TablesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TablesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabName = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tables, container, false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);

        //SET MARGINS
        final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources()
                .getDisplayMetrics());
        params.setMargins(margin, margin, margin, margin);
        view.setLayoutParams(params);
        view.setLayoutParams(params);


        //initialize views
        total = (TextView) view.findViewById(R.id.total);
        total.setText(Integer.toString(current)+ "/24");
        tables = (TextView) view.findViewById(R.id.table_avail);
        t1 = (Button) view.findViewById(R.id.t1);
        t1.setOnClickListener(this);
        t2 = (Button) view.findViewById(R.id.t2);
        t2.setOnClickListener(this);
        t3 = (Button) view.findViewById(R.id.t3);
        t3.setOnClickListener(this);
        t4 = (Button) view.findViewById(R.id.t4);
        t4.setOnClickListener(this);
        t5 = (at.markushi.ui.CircleButton) view.findViewById(R.id.t5);
        t5.setOnClickListener(this);
        t6 = (at.markushi.ui.CircleButton) view.findViewById(R.id.t6);
        t6.setOnClickListener(this);
        t7 = (Button) view.findViewById(R.id.t7);
        t7.setOnClickListener(this);

        //restore the table colors
        restoreColors();

        spinner = (Spinner) view.findViewById(R.id.choice);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.tables_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice = (String) parent.getItemAtPosition(position);
                if (choice.equalsIgnoreCase("two person")){
                    tables.setText(Integer.toString(twos) + "/4");

                }
                else if (choice.equalsIgnoreCase("four person")){
                    tables.setText(Integer.toString(fours) + "/2");
                }
                else if (choice.equalsIgnoreCase("eight person")){
                    tables.setText(Integer.toString(eights) + "/1");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                choice = "Two Person";
            }
        });


        fl.addView(view);
        return fl;
    }


    //ON CLICK TO HANDLE CHANGING COLORS AND VALUE OF AVAILABLE SEATS
    @Override
    public void onClick(View v) {
        if (v.equals(t1)){
            if (inUse[0]){
                current = current + 2;
                twos++;
                t1.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                inUse[0] = false;
                total.setText(Integer.toString(current)+ "/24");
                if (choice.equalsIgnoreCase("two person")){
                    tables.setText(Integer.toString(twos) + "/4");
                }
            }
            else {
                current = current - 2;
                twos--;
                t1.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                inUse[0] = true;
                total.setText(Integer.toString(current)+ "/24");
                if (choice.equalsIgnoreCase("two person")){
                    tables.setText(Integer.toString(twos) + "/4");
                }
            }
        }
        else if (v.equals(t2)){
            if (inUse[1]){
                current = current + 2;
                twos++;
                t2.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                inUse[1] = false;
                total.setText(Integer.toString(current)+ "/24");
                if (choice.equalsIgnoreCase("two person")){
                    tables.setText(Integer.toString(twos) + "/4");
                }
            }

            else {
                current = current - 2;
                twos--;
                t2.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                inUse[1] = true;
                total.setText(Integer.toString(current)+ "/24");
                if (choice.equalsIgnoreCase("two person")){
                    tables.setText(Integer.toString(twos) + "/4");
                }
            }
        }
        else if (v.equals(t3)){
            if (inUse[2]){
                current = current + 2;
                twos++;
                t3.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                inUse[2] = false;
                total.setText(Integer.toString(current)+ "/24");
                if (choice.equalsIgnoreCase("two person")){
                    tables.setText(Integer.toString(twos) + "/4");
                }
            }
            else {
                current = current - 2;
                twos--;
                t3.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                inUse[2] = true;
                total.setText(Integer.toString(current)+ "/24");
                if (choice.equalsIgnoreCase("two person")){
                    tables.setText(Integer.toString(twos) + "/4");
                }
            }
        }
        else if (v.equals(t4)){
            if (inUse[3]){
                current = current + 2;
                twos++;
                t4.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                inUse[3] = false;
                total.setText(Integer.toString(current)+ "/24");
                if (choice.equalsIgnoreCase("two person")){
                    tables.setText(Integer.toString(twos) + "/4");
                }
            }
            else {
                current = current - 2;
                twos--;
                t4.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                inUse[3] = true;
                total.setText(Integer.toString(current)+ "/24");
                if (choice.equalsIgnoreCase("two person")){
                    tables.setText(Integer.toString(twos) + "/4");
                }
            }
        }
        else if (v.equals(t5)){
            if (inUse[4]){
                current = current + 4;
                fours++;
                t5.setColor(getActivity().getResources().getColor(R.color.available));
                inUse[4] = false;
                total.setText(Integer.toString(current)+ "/24");
                if (choice.equalsIgnoreCase("four person")){
                    tables.setText(Integer.toString(fours) + "/2");
                }
            }
            else {
                current = current - 4;
                fours--;
                t5.setColor(getActivity().getResources().getColor(R.color.used));
                inUse[4] = true;
                total.setText(Integer.toString(current)+ "/24");
                if (choice.equalsIgnoreCase("four person")){
                    tables.setText(Integer.toString(fours) + "/2");
                }
            }
        }
        else if (v.equals(t6)){
            if (inUse[5]){
                current = current + 4;
                fours++;
                t6.setColor(getActivity().getResources().getColor(R.color.available));
                inUse[5] = false;
                total.setText(Integer.toString(current)+ "/24");
                if (choice.equalsIgnoreCase("four person")){
                    tables.setText(Integer.toString(fours) + "/2");
                }
            }
            else {
                current = current - 4;
                fours--;
                t6.setColor(getActivity().getResources().getColor(R.color.used));
                inUse[5] = true;
                total.setText(Integer.toString(current)+ "/24");
                if (choice.equalsIgnoreCase("four person")){
                    tables.setText(Integer.toString(fours) + "/2");
                }
            }
        }
        else if (v.equals(t7)){
            if (inUse[6]){
                current = current + 8;
                eights++;
                t7.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                inUse[6] = false;
                total.setText(Integer.toString(current)+ "/24");
                if (choice.equalsIgnoreCase("eight person")){
                    tables.setText(Integer.toString(eights) + "/1");
                }
            }
            else {
                current = current - 8;
                eights--;
                t7.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                inUse[6] = true;
                total.setText(Integer.toString(current)+ "/24");
                if (choice.equalsIgnoreCase("eight person")){
                    tables.setText(Integer.toString(eights) + "/1");
                }
            }
        }
    }

    //WHEN RECYCLING THE TAB, CHECK IF WE NEED TO RESTORE THE COLOR OF THE TABLE
    public void restoreColors() {
        for (int i = 0; i < inUse.length; i++){
            if (inUse[i]){
                switch (i) {
                    case 0:{
                        t1.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                    }
                    case 1:{
                        t2.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                    }
                    case 2:{
                        t3.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                    }
                    case 3:{
                        t4.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                    }
                    case 4:{
                        t5.setColor(getActivity().getResources().getColor(R.color.used));
                    }
                    case 5:{
                        t6.setColor(getActivity().getResources().getColor(R.color.used));
                    }
                    case 6:{
                        t7.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                    }
                }
            }
            else {
                switch (i) {
                    case 0:{
                        t1.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                    }
                    case 1:{
                        t2.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                    }
                    case 2:{
                        t3.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                    }
                    case 3:{
                        t4.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                    }
                    case 4:{
                        t5.setColor(getActivity().getResources().getColor(R.color.available));
                    }
                    case 5:{
                        t6.setColor(getActivity().getResources().getColor(R.color.available));
                    }
                    case 6:{
                        t7.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                    }
                }
            }
        }
    }
}
