package sohagan.com.quicksit_host;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


public class TablesFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button t1,t2,t3,t4,t5,t6,t7; // buttons represent tables
    TextView total;
    private int current = 24; // the current number of tables in use
    private boolean[] inUse; // each slot corresponds to whether or not a table is in use

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


        total = (TextView) view.findViewById(R.id.total);

        t1 = (Button) view.findViewById(R.id.t1);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inUse[0]){
                    current = current + 2;
                    t1.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                    inUse[0] = false;
                }
                else {
                    current = current - 2;
                    t1.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                    inUse[0] = true;
                }
            }
        });
        t2 = (Button) view.findViewById(R.id.t2);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inUse[1]){
                    current = current + 2;
                    t2.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                    inUse[1] = false;
                }
                else {
                    current = current - 2;
                    t2.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                    inUse[1] = true;
                }
            }
        });
        t3 = (Button) view.findViewById(R.id.t3);
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inUse[2]){
                    current = current + 2;
                    t3.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                    inUse[2] = false;
                }
                else {
                    current = current - 2;
                    t3.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                    inUse[2] = true;
                }
            }
        });
        t4 = (Button) view.findViewById(R.id.t4);
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inUse[3]){
                    current = current + 2;
                    t4.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                    inUse[3] = false;
                }
                else {
                    current = current - 2;
                    t4.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                    inUse[3] = true;
                }
            }
        });
        t5 = (Button) view.findViewById(R.id.t5);
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inUse[4]){
                    current = current + 4;
                    t5.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                    inUse[4] = false;
                }
                else {
                    current = current - 4;
                    t5.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                    inUse[4] = true;
                }
            }
        });
        t6 = (Button) view.findViewById(R.id.t6);
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inUse[5]){
                    current = current + 4;
                    t6.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                    inUse[5] = false;
                }
                else {
                    current = current - 4;
                    t6.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                    inUse[5] = true;
                }
            }
        });
        t7 = (Button) view.findViewById(R.id.t7);
        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inUse[6]){
                    current = current + 8;
                    t7.setBackgroundColor(getActivity().getResources().getColor(R.color.available));
                    inUse[6] = false;
                }
                else {
                    current = current - 8;
                    t7.setBackgroundColor(getActivity().getResources().getColor(R.color.used));
                    inUse[6] = true;
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


}
