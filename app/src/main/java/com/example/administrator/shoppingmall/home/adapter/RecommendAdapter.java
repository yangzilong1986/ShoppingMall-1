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

public class RecommendAdapter extends BaseAdapter {

	private  List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info;
	private  Context mContext;

	public RecommendAdapter(Context mContext, List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
		this.mContext = mContext;
		this.recommend_info = recommend_info;
	}

	@Override
	public int getCount() {
		return recommend_info == null ? 0: recommend_info.size();
	}

	@Override
	public Object getItem(int position) {
		return recommend_info.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null){
			convertView = View.inflate(mContext, R.layout.item_recommend,null);
			viewHolder = new ViewHolder();
			viewHolder.iv_recommend = (ImageView) convertView.findViewById(R.id.iv_recommend);
			viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		//根据当前位置获得数据
		Glide.with(mContext).load(Constans.BASE_IMAGE_URL+recommend_info.get(position).getFigure()).into(viewHolder.iv_recommend);
		viewHolder.tv_name.setText(recommend_info.get(position).getName());
		viewHolder.tv_price.setText("￥"+recommend_info.get(position).getCover_price());


		return convertView;
	}
	static class ViewHolder{
		ImageView iv_recommend;
		TextView tv_name;
		TextView tv_price;
	}
}
