/**
 * 
 */
package com.example.polymerization.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/**
 * @author wjy
 */
public class TabPageIndicatorAdapter1 extends FragmentStatePagerAdapter {
	private List<String> titleList;
	private List<Fragment>  fragList;

	public TabPageIndicatorAdapter1(FragmentManager fm,List<String> titleList,List<Fragment>  fragList) {
		super(fm);
		this.titleList=titleList;
		this.fragList=fragList;
	}
		@Override
		public Fragment getItem(int position) {
			return fragList.get(position);
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			super.destroyItem(container, position, object);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titleList.get(position % titleList.size());
		}

		@Override
		public int getCount() {
			return titleList.size();
		}
	}

