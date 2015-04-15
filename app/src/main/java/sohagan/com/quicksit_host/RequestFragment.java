package sohagan.com.quicksit_host;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class RequestFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static RequestFragment newInstance(String param1, String param2) {
        RequestFragment fragment = new RequestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //CREATE FRAGMENT VIEWS
        View v = inflater.inflate(R.layout.fragment_reservations, container, false);
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
