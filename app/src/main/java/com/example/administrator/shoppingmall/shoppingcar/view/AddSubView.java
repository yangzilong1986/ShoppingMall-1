package com.example.administrator.shoppingmall.shoppingcar.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.shoppingmall.R;

/**
 * Created by Administrator on 2017/8/7.
 * 自定义增删按钮
 */

public class AddSubView extends LinearLayout implements View.OnClickListener {
	private Context mContext;

	private ImageView ivSub;
	private TextView tvValue;
	private ImageView ivAdd;

	private int value = 1;
	private int minValue =1;
	private int maxValue = 5;

	public AddSubView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		//把布局文件实例化，并加载到AddSubView中
		View.inflate(context, R.layout.add_sub_view,this);
		ivSub = (ImageView) findViewById(R.id.iv_sub);
		tvValue = (TextView) findViewById(R.id.tv_value);
		ivAdd = (ImageView) findViewById(R.id.iv_add);

		int value = getValue();
		setValue(value);

		ivSub.setOnClickListener(this);
		ivAdd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.iv_sub:
				subNumber();
				break;
			case R.id.iv_add:
				addNumber();
				break;
		}
//		Toast.makeText(mContext,"当前商品数=="+value,Toast.LENGTH_SHORT).show();
	}

	private void addNumber() {
		if (value < maxValue){
			value ++;
		}
		setValue(value);
		if (onNumberChangeListener != null){
			onNumberChangeListener.OnNumberChange(value);
		}

	}

	private void subNumber() {
		if (value > minValue){
			value --;
		}
		setValue(value);
		if (onNumberChangeListener != null){
			onNumberChangeListener.OnNumberChange(value);
		}
	}

	public int getValue() {
		String valueStr = tvValue.getText().toString().trim();
		if (!TextUtils.isEmpty(valueStr)){
			value = Integer.parseInt(valueStr);
		}
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		tvValue.setText(value+"");
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}


	/**
	 * 当数量发生变化时回调
	 */
	public interface OnNumberChangeListener{
		 void OnNumberChange(int value);
	}
	private OnNumberChangeListener onNumberChangeListener;

	public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
		this.onNumberChangeListener = onNumberChangeListener;
	}

}
