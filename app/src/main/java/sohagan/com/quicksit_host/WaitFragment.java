package sohagan.com.quicksit_host;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
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



public class WaitFragment extends Fragment{

    private static final String ARG_PARAM1 = "param1";

    private String tabName;


    private Button waitButt;
    private TextView waitNum;


    public static WaitFragment newInstance(String tabName) {
        WaitFragment fragment = new WaitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, tabName);
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
            tabName = getArguments().getString(ARG_PARAM1);
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
        //value = restaurant.getWait();
        //waitNum.setText(Integer.toString(value));

        waitButt = (Button) v.findViewById(R.id.wait_butt);
        waitButt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //STYLE FOR TOUCH
                    //waitButt.setBackgroundColor(getActivity().getResources().getColor(R.color.shittyRoses));
                    //waitButt.setTextColor(getActivity().getResources().getColor(R.color.white));

                    openWaitDialog();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //STYLE FOR UNTOUCH
                    //waitButt.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                    //waitButt.setTextColor(getActivity().getResources().getColor(R.color.shittyRoses));
                }
                return false;
            }
        });

        return v;
    }


    public void openWaitDialog() {
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
                        // get user input and set it to range value
                        Editable temp = input.getText();
                        int val = Integer.parseInt(temp.toString());

                        waitNum.setText(temp);
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



}