package sohagan.com.quicksit_host;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import com.astuetz.PagerSlidingTabStrip;


public class HomeActivity extends FragmentActivity {

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //HIDE ACTION BAR
        ActionBar bar = getActionBar();
        bar.hide();

        //INITIALIZE VIEWS
        initViews();
    }

    public void initViews(){
        //INTIALIZE TABS AND THE PAGER ADAPTER TO CHANGE FRAGMENTS
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setShouldExpand(true);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        // SET PAGE VIEWER AND COLOR STYLING
        tabs.setViewPager(pager);
        tabs.setIndicatorColor(getResources().getColor(R.color.shittyRoses));
        tabs.setDividerColor(getResources().getColor(R.color.shittyRoses));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
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


    public class MyPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
        private final int[] ICONS = { R.drawable.ic_wait, R.drawable.ic_tables, R.drawable.ic_request, R.drawable.ic_confirmed};
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getPageIconResId(int position) {
            return ICONS[position];
        }

        @Override
        public int getCount() {
            return ICONS.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return WaitFragment.newInstance(5,10);
                case 1:
                    return TablesFragment.newInstance("hey", "hey");
                case 2:
                    return RequestFragment.newInstance("Hello", "Hello");
                case 3:
                    return ConfirmedFragment.newInstance("Hi", "Hi");
                default:
                    return WaitFragment.newInstance(5,10);
            }
        }

    }

}
