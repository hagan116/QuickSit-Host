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

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button t1;
    Button t2;
    Button t3;
    Button t4;
    CircleButton t5;
    CircleButton t6;
    Button t7; // buttons represent tables
    Spinner spinner;
    TextView total;
    TextView tables;
    private String choice;
    private int current = 24; // the current number of tables in use
    private int twos = 4;
    private int fours = 2;
    private int eights = 1;
    private boolean[] inUse; // each slot corresponds to whether or not a table is in use
    private at.markushi.ui.CircleButton b1, b2, b3;

    private String tabName;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TablesFragment.
     */
    //
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tables, container, false);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);

        final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
                .getDisplayMetrics());

        params.setMargins(margin, margin, margin, margin);
        inUse  = new boolean[7];

        //initialize views
        total = (TextView) view.findViewById(R.id.total);
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

            }
        });


        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.equals(t1)){
            if (inUse[0]){
                current = current + 2;
                twos++;
                t1.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                inUse[0] = false;
                total.setText(Integer.toString(current)+ "/24");
            }
            else {
                current = current - 2;
                twos--;
                t1.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                inUse[0] = true;
                total.setText(Integer.toString(current)+ "/24");
            }
        }
        else if (v.equals(t2)){
            if (inUse[1]){
                current = current + 2;
                twos++;
                t2.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                inUse[1] = false;
                total.setText(Integer.toString(current)+ "/24");
            }

            else {
                current = current - 2;
                twos--;
                t2.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                inUse[1] = true;
                total.setText(Integer.toString(current)+ "/24");
            }
        }
        else if (v.equals(t3)){
            if (inUse[2]){
                current = current + 2;
                twos++;
                t3.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                inUse[2] = false;
                total.setText(Integer.toString(current)+ "/24");
            }
            else {
                current = current - 2;
                twos--;
                t3.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                inUse[2] = true;
                total.setText(Integer.toString(current)+ "/24");
            }
        }
        else if (v.equals(t4)){
            if (inUse[3]){
                current = current + 2;
                twos++;
                t4.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                inUse[3] = false;
                total.setText(Integer.toString(current)+ "/24");
            }
            else {
                current = current - 2;
                twos--;
                t4.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                inUse[3] = true;
                total.setText(Integer.toString(current)+ "/24");
            }
        }
        else if (v.equals(t5)){
            if (inUse[4]){
                current = current + 4;
                fours++;
                t5.setColor(getActivity().getResources().getColor(R.color.available));
                inUse[4] = false;
                total.setText(Integer.toString(current)+ "/24");
            }
            else {
                current = current - 4;
                fours--;
                t5.setColor(getActivity().getResources().getColor(R.color.used));
                inUse[4] = true;
                total.setText(Integer.toString(current)+ "/24");
            }
        }
        else if (v.equals(t6)){
            if (inUse[5]){
                current = current + 4;
                fours++;
                t6.setColor(getActivity().getResources().getColor(R.color.available));
                inUse[5] = false;
                total.setText(Integer.toString(current)+ "/24");
            }
            else {
                current = current - 4;
                fours--;
                t6.setColor(getActivity().getResources().getColor(R.color.used));
                inUse[5] = true;
                total.setText(Integer.toString(current)+ "/24");
            }
        }
        else if (v.equals(t7)){
            if (inUse[6]){
                current = current + 8;
                eights++;
                t7.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                inUse[6] = false;
                total.setText(Integer.toString(current)+ "/24");
            }
            else {
                current = current - 8;
                eights--;
                t7.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                inUse[6] = true;
                total.setText(Integer.toString(current)+ "/24");
            }
        }
    }
}
