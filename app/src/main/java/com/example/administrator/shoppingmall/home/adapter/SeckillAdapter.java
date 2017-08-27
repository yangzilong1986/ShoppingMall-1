package com.example.administrator.shoppingmall.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.shoppingmall.R;
import com.example.administrator.shoppingmall.home.bean.ResultBeanData;
import com.example.administrator.shoppingmall.utils.Constans;

/**
 * Created by Administrator on 2017/8/3.
 */

public class SeckillAdapter extends RecyclerView.Adapter<SeckillAdapter.ViewHolder>{
	private final ResultBeanData.ResultBean.SeckillInfoBean seckill_info;
	private Context mContext;


	public SeckillAdapter(Context mContext, ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {
		this.mContext = mContext;
		this.seckill_info = seckill_info;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView  = View.inflate(mContext, R.layout.item_seckill,null);
		return new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
			//根据位置得到相应的数据
		ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = seckill_info.getList().get(position);
		Glide.with(mContext).load(Constans.BASE_IMAGE_URL+listBean.getFigure()).into(holder.iv_figure);
		holder.tv_cover_price.setText(listBean.getCover_price());
		holder.tv_origin_price.setText(listBean.getOrigin_price());
	}

	@Override
	public int getItemCount() {
		return seckill_info == null ? 0 : seckill_info.getList().size();
	}

	class ViewHolder extends RecyclerView.ViewHolder{
		private ImageView iv_figure;
		private TextView tv_cover_price;
		private TextView tv_origin_price;
		public ViewHolder(View itemView) {
			super(itemView);
			iv_figure = (ImageView) itemView.findViewById(R.id.iv_figure);
			tv_cover_price = (TextView) itemView.findViewById(R.id.tv_cover_price);
			tv_origin_price = (TextView) itemView.findViewById(R.id.tv_origin_price);

			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (onSeckillRecyclerView != null)
					onSeckillRecyclerView.onItemClick(getLayoutPosition());
				}
			});
		}
	}
	/**
	 * 监听器
	 */
	public interface OnSeckillRecyclerView{
		/**
		 * 当某一条被点击时进行回调
		 * @param position
		 */
		public void onItemClick(int position);
	}
	private OnSeckillRecyclerView onSeckillRecyclerView;

	/**
	 * 设置 item 的监听
	 * @param onSeckillRecyclerView
	 */
	public void setOnSeckillRecyclerView(OnSeckillRecyclerView onSeckillRecyclerView) {
		this.onSeckillRecyclerView = onSeckillRecyclerView;
	}
}
