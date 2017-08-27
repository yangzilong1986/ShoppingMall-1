package com.example.administrator.shoppingmall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.shoppingmall.R;
import com.example.administrator.shoppingmall.home.bean.ResultBeanData;
import com.example.administrator.shoppingmall.utils.Constans;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class HotAdapter extends BaseAdapter {
	private final Context mContext;
	private final List<ResultBeanData.ResultBean.HotInfoBean> hot_info;

	public HotAdapter(Context mContext, List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
		this.mContext= mContext;
		this.hot_info = hot_info;
	}

	@Override
	public int getCount() {
		return hot_info.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null){
			convertView = View.inflate(mContext, R.layout.item_hot,null);
			viewHolder = new ViewHolder();
			viewHolder.ivHot = (ImageView) convertView.findViewById(R.id.iv_hot);
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
			viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Glide.with(mContext).load(Constans.BASE_IMAGE_URL+hot_info
				.get(position).getFigure()).into(viewHolder.ivHot);
		viewHolder.tvName.setText(hot_info.get(position).getName());
		viewHolder.tvPrice.setText(hot_info.get(position).getCover_price());

		return convertView;
	}
	static class ViewHolder{
		ImageView ivHot;
		TextView tvName;
		TextView tvPrice;
	}
}
