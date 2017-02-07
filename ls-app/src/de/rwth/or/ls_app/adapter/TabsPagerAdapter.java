package de.rwth.or.ls_app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

	private static final Fragment[] fragments = new Fragment[]{
			new WebsiteFragment(),
			new ScannerFragment(),
			new MoviesFragment()
	};
	
	private static final String[] fragments_name = new String[]{
			"Website",
			"Scanner",
			"Settins"
	};
	
	public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
	
	public String[] getFragmentNames() {
		return fragments_name;
	}
	
	public String getNameOfFragment(int position) {
		if (position < getCount()) {
			return fragments_name[position];
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem(int position) {
		if (position < getCount()) {
			return fragments[position];
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return fragments.length;
	}

	/**
	 * @return
	 */
	public boolean onWebsiteKeyDown(int keyCode, KeyEvent event) {
		return ((WebsiteFragment)fragments[0]).onKeyDown(keyCode, event);
	}

}
