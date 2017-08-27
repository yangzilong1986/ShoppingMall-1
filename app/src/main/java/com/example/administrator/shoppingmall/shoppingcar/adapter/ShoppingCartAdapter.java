package com.example.administrator.shoppingmall.shoppingcar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.shoppingmall.R;
import com.example.administrator.shoppingmall.home.bean.GoodsBean;
import com.example.administrator.shoppingmall.shoppingcar.utils.CartStorage;
import com.example.administrator.shoppingmall.shoppingcar.view.AddSubView;
import com.example.administrator.shoppingmall.utils.Constans;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>{

	private final Context mContext;
	private final List<GoodsBean> datas;
	private final TextView tvShopcartTotal;
	private final CheckBox checkboxAll;
	//完成状态
	private final CheckBox cbAll;

	public ShoppingCartAdapter(Context context, List<GoodsBean> goodsBeanList,
							   TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox cbAll) {

		this.mContext = context;
		this.datas = goodsBeanList;
		this.tvShopcartTotal = tvShopcartTotal;
		this.checkboxAll = checkboxAll;
		this.cbAll = cbAll;

		showTotalPrice();

		//设置监听
		setListener();

		//检验一加载进来是否全选
		CheckAll();
	}

	private void setListener() {
		setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClickListener(int position) {
				//根据位置获得相应的Bean对象
				GoodsBean goodsBean = datas.get(position);
				//设置CheckBox的取反状态
				goodsBean.setChecked(!goodsBean.getChecked());
				//刷新状态
				notifyItemChanged(position);
				//检验是否全选
				CheckAll();
				//重新计算
				showTotalPrice();
			}
		});

		//checkboxAll的点击事件
		checkboxAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//1.得到状态
				boolean isCheck =  checkboxAll.isChecked();
				//2.根据状态设置全选或非全选
				CheckAll_none(isCheck);
				//3.重新计算
				showTotalPrice();
			}
		});

		//  完成状态下的cbAll的点击事件
		cbAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//1.得到状态
				boolean isCheck =  cbAll.isChecked();
				//2.根据状态设置全选或非全选
				CheckAll_none(isCheck);
			}
		});
	}


	/**
	 * 设置全选或非全选
	 * @param isCheck
	 */
	public void CheckAll_none(boolean isCheck) {
		if (datas != null && datas.size()>0){
			for (int i=0;i<datas.size();i++){
				GoodsBean goodsBean = datas.get(i);
				goodsBean.setChecked(isCheck);
				notifyItemChanged(i);
			}

		}
	}

	/**
	 * 检验是否全选
	 */
	public void CheckAll() {
		if (datas != null && datas.size()>0){
			int number = 0;
			for (int i=0;i<datas.size();i++){
				GoodsBean goodsBean = datas.get(i);
				if (!goodsBean.getChecked()){
					//非全选
					checkboxAll.setChecked(false);
					cbAll.setChecked(false);
				}else {
					number ++;
				}
			}
			if (number == datas.size()){
				//全选
				checkboxAll.setChecked(true);
				cbAll.setChecked(true);
			}
		}else {
			//没有数据时也是默认不选中
			checkboxAll.setChecked(false);
			cbAll.setChecked(false);
		}
	}

	public void showTotalPrice() {
		tvShopcartTotal.setText(""+getTotalPrice());
	}

	private double getTotalPrice() {
		double totalPrice = 0.00;
		if (datas != null && datas.size()>0){
			for (int i=0;i<datas.size();i++){
				GoodsBean goodsBean = datas.get(i);
				if (goodsBean.getChecked()){
					totalPrice = totalPrice + Double.valueOf(goodsBean.getNumber()) * Double.valueOf(goodsBean.getCover_price());
				}
			}
		}
		return totalPrice;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = View.inflate(mContext, R.layout.item_shopping_cart,null);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position) {
		// 1 .根据对应的位置得到Bean的数据
		final GoodsBean goodsBean = datas.get(position);
		//2.设置数据
		holder.cb_gov.setChecked(goodsBean.getChecked());
		Glide.with(mContext).load(Constans.BASE_IMAGE_URL+goodsBean.getFirgure()).into(holder.iv_gov);
		holder.tv_desc_gov.setText(goodsBean.getName());
		holder.tv_price_gov.setText("￥"+goodsBean.getCover_price());
		holder.addSubView.setValue(goodsBean.getNumber());
		holder.addSubView.setMinValue(1);
		holder.addSubView.setMaxValue(20);


		//设置商品数量变化的监听
		holder.addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
			@Override
			public void OnNumberChange(int value) {
				//1.内存更新数据
				goodsBean.setNumber(value);
				//2.本地更新数据
				CartStorage.getInstance().updateData(goodsBean);
				//3.适配器要刷新
				notifyItemChanged(position);
				//4.再次计算价格
				showTotalPrice();
			}
		});

	}

	@Override
	public int getItemCount() {
		return datas.size();
	}

	public void deleteData() {
		if (datas != null && datas.size()>0){
			for (int i=0;i<datas.size();i++){
				GoodsBean goodsBean = datas.get(i);
				if (goodsBean.getChecked()){
					//删除选中的商品
					datas.remove(goodsBean);
					//保存到本地
					CartStorage.getInstance().deleteData(goodsBean);
					//更新数据
					notifyItemRemoved(i);

					//注意
					i--;
				}
			}
		}
	}

	class ViewHolder extends RecyclerView.ViewHolder{
		private CheckBox cb_gov;
		private ImageView iv_gov;
		private TextView tv_desc_gov;
		private TextView tv_price_gov;
		private AddSubView addSubView;
		public ViewHolder(View itemView) {
			super(itemView);
			cb_gov = (CheckBox) itemView.findViewById(R.id.cb_gov);
			iv_gov = (ImageView) itemView.findViewById(R.id.iv_gov);
			tv_desc_gov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
			tv_price_gov = (TextView) itemView.findViewById(R.id.tv_price_gov);
			addSubView = (AddSubView) itemView.findViewById(R.id.addSubView);

			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (onItemClickListener != null){
						onItemClickListener.onItemClickListener(getLayoutPosition());
					}
				}
			});
		}

	}

	/**
	 * 点击Item的监听者
	 */
	public interface OnItemClickListener{
		/** 当点击某条时被回调
		 * @param position
		 */
		public void onItemClickListener(int position);
	}
	private OnItemClickListener onItemClickListener;

	/**
	 * 设置item被点击的监听
	 * @param onItemClickListener
	 */
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}
}
