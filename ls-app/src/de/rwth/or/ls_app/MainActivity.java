package de.rwth.or.ls_app;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import de.rwth.or.ls_app.adapter.TabsPagerAdapter;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
 
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
        // Adding Tabs
        for (String tab_name : mAdapter.getFragmentNames()) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
 
        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
 
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}

			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
 
        });
    }
 
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }
 
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
//        if (scanResult != null) {
//            String re = scanResult.getContents();
//            Log.d("code", re);
//        }
        // else continue with any other code you need in the method

    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
    	if (actionBar.getSelectedTab().getText().equals(mAdapter.getNameOfFragment(0))) {
    		if (mAdapter.onWebsiteKeyDown(keyCode, event)) {
    			return true;
    		}
    	}
    	// If it wasn't the Back key or there's no web page history, bubble up to the default
	    // system behavior (probably exit the activity)
	    return super.onKeyDown(keyCode, event);
    }


	/* (non-Javadoc)
	 * @see android.app.ActionBar.TabListener#onTabReselected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	 */
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see android.app.ActionBar.TabListener#onTabUnselected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	 */
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
 
}
