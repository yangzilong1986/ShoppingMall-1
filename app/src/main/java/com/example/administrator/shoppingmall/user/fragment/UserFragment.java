package com.example.administrator.shoppingmall.user.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.shoppingmall.base.BaseFragment;

/**
 * Created by Administrator on 2017/8/1.
 */

public class UserFragment extends BaseFragment {

	private TextView mTextView;
	@Override
	public View initView() {
		mTextView = new TextView(mContext);
		mTextView.setGravity(Gravity.CENTER);
		mTextView.setTextSize(25);
		mTextView.setTextColor(Color.LTGRAY);
		return mTextView;
	}
	@Override
	public void initData() {
		super.initData();
		mTextView.setText("我是User页面");
	}
}
