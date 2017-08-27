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
 * Created by Administrator on 2017/8/3.
 */

public class ChannelAdapter extends BaseAdapter{

	private final Context mContext;
	private final List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info;

	public ChannelAdapter(Context mContext, List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
		this.mContext = mContext;
		this.channel_info = channel_info;
	}

	@Override
	public int getCount() {
		return channel_info == null ? 0 : channel_info.size();
	}

	@Override
	public Object getItem(int position) {
		return  channel_info.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null){
			convertView = View.inflate(mContext, R.layout.channel_item,null);
			viewHolder = new ViewHolder();
			viewHolder.ivChannel = (ImageView) convertView.findViewById(R.id.iv_channel);
			viewHolder.tvChannel = (TextView) convertView.findViewById(R.id.tv_channel);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		//根据当前位置获得数据
		ResultBeanData.ResultBean.ChannelInfoBean channelInfoBean = channel_info.get(position);
		Glide.with(mContext).load(Constans.BASE_IMAGE_URL+channelInfoBean.getImage()).into(viewHolder.ivChannel);
		viewHolder.tvChannel.setText(channelInfoBean.getChannel_name());

		return convertView;
	}
	static class ViewHolder{
		ImageView ivChannel;
		TextView tvChannel;
	}
}
