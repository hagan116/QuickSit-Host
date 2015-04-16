package sohagan.com.quicksit_host;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class ConfirmedFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int rest_id;
    private int rest_take;

    //JSON NODES FOR RESERVATION HTTPGET
    private static final String TAG_RES_NAME = "name";
    private static final String TAG_RES_SIZE = "p_size";
    private static final String TAG_RES_DATE = "rest_data";
    private static final String TAG_RES_TIME = "rest_time";

    public static ConfirmedFragment newInstance(int param1, int param2) {
        ConfirmedFragment fragment = new ConfirmedFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ConfirmedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rest_id = getArguments().getInt(ARG_PARAM1);
            rest_take = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //CREATE FRAGMENT VIEWS
        View v = inflater.inflate(R.layout.fragment_confirmed, container, false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);

        //SET MARGINS
        final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources()
                .getDisplayMetrics());
        params.setMargins(margin, margin, margin, margin);
        v.setLayoutParams(params);
        v.setLayoutParams(params);





        fl.addView(v);
        return fl;
    }

}
