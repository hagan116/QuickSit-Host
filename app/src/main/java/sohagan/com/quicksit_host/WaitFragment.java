package sohagan.com.quicksit_host;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WaitFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WaitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String tabName;

    private OnFragmentInteractionListener mListener;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wait, container, false);


        waitNum = (TextView) view.findViewById(R.id.wait_num);
        //value = restaurant.getWait();
        //waitNum.setText(Integer.toString(value));

        waitButt = (Button) view.findViewById(R.id.wait_butt);
        waitButt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //stlye for touches
                    waitButt.setBackgroundColor(getResources().getColor(R.color.shittyRoses));
                    waitButt.setTextColor(getResources().getColor(R.color.white));

                    //CALL TO CHANGE THE WAIT TIME
                    openWaitDialog();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //style for un-touche
                    waitButt.setBackgroundColor(getResources().getColor(R.color.white));
                    waitButt.setTextColor(getResources().getColor(R.color.shittyRoses));
                }
                return false;
            }
        });


        return view;
    }


    public void openWaitDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity().getApplicationContext());
        View dialogView = layoutInflater.inflate(R.layout.dialog_setwait, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity().getApplicationContext());

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
