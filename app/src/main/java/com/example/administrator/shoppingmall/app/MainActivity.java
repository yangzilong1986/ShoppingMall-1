package com.example.administrator.shoppingmall.app;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.administrator.shoppingmall.R;
import com.example.administrator.shoppingmall.base.BaseFragment;
import com.example.administrator.shoppingmall.community.fragment.CommunityFragment;
import com.example.administrator.shoppingmall.home.fragment.HomeFragment;
import com.example.administrator.shoppingmall.shoppingcar.fragment.ShoppingcarFragment;
import com.example.administrator.shoppingmall.type.fragment.TypeFragment;
import com.example.administrator.shoppingmall.user.fragment.UserFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

	@Bind(R.id.framelayout)
	FrameLayout mFramelayout;
	@Bind(R.id.rg_main)
	RadioGroup mRgMain;

	private ArrayList<BaseFragment> mFragmentList;
	private int position = 0;
	private Fragment tempFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		initFragment();
		initFragmentListener();
	}

	private void initFragment() {
		mFragmentList = new ArrayList<>();
		mFragmentList.add(new HomeFragment());
		mFragmentList.add(new TypeFragment());
		mFragmentList.add(new CommunityFragment());
		mFragmentList.add(new ShoppingcarFragment());
		mFragmentList.add(new UserFragment());
	}
	private void initFragmentListener() {
		mRgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
				switch (checkedId){
					case R.id.rb_home:
						position = 0;
						break;
					case R.id.rb_type:
						position = 1;
						break;
					case R.id.rb_find:
						position = 2;
						break;
					case R.id.rb_car:
						position = 3;
						break;
					case R.id.rb_user:
						position = 4;
						break;
					default:
						position = 0;
						break;
				}
				BaseFragment baseFragment = getFragment(position);
				switchFragment(tempFragment,baseFragment);
			}
		});
		mRgMain.check(R.id.rb_home);//默认选中图标

	}
	private BaseFragment getFragment(int position){
		if (mFragmentList != null && mFragmentList.size() > 0){
			BaseFragment baseFragment = mFragmentList.get(position);
			return baseFragment;
		}
		return null;
	}
	private void switchFragment(Fragment fromFragment,BaseFragment nextFragment){
		if (tempFragment != nextFragment) {
			tempFragment = nextFragment;
			if (nextFragment != null) {
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				//判断 nextFragment 是否添加
				if (!nextFragment.isAdded()) {
					//隐藏当前的fragment
					if (fromFragment != null) {
						transaction.hide(fromFragment);
					}
					transaction.add(R.id.framelayout, nextFragment).commit();
				} else {
					//隐藏当前 Fragment
					if (fromFragment != null) {
						transaction.hide(fromFragment);
					}
					transaction.show(nextFragment).commit();
				}
			}
		}
	}

}
