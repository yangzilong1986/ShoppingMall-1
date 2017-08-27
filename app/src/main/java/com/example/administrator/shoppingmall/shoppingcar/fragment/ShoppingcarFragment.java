package com.example.administrator.shoppingmall.shoppingcar.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.shoppingmall.R;
import com.example.administrator.shoppingmall.base.BaseFragment;
import com.example.administrator.shoppingmall.home.bean.GoodsBean;
import com.example.administrator.shoppingmall.shoppingcar.adapter.ShoppingCartAdapter;
import com.example.administrator.shoppingmall.shoppingcar.utils.CartStorage;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class ShoppingcarFragment extends BaseFragment implements View.OnClickListener {

	private TextView tvShopcartEdit;
	private RecyclerView recyclerview;
	private LinearLayout llCheckAll;
	private CheckBox checkboxAll;
	private TextView tvShopcartTotal;
	private Button btnCheckOut;
	private LinearLayout llDelete;
	private CheckBox cbAll;
	private Button btnDelete;
	private Button btnCollection;

	private LinearLayout ll_empty_shopcart;
	private ImageView ivEmpty;
	private TextView tvEmptyCartTobuy;

	private ShoppingCartAdapter adapter;

	//编辑状态
	private static final int ACTION_EDIT = 1;
	//完成状态
	private static final int ACTION_COMPLETE = 2;

	@Override
	public View initView() {
		View view = View.inflate(mContext,R.layout.fragment_shoppingcart,null);

		tvShopcartEdit = (TextView)view.findViewById( R.id.tv_shopcart_edit );
		recyclerview = (RecyclerView)view.findViewById( R.id.recyclerview );
		llCheckAll = (LinearLayout)view.findViewById( R.id.ll_check_all );
		checkboxAll = (CheckBox)view.findViewById( R.id.checkbox_all );
		tvShopcartTotal = (TextView)view.findViewById( R.id.tv_shopcart_total );
		btnCheckOut = (Button)view.findViewById( R.id.btn_check_out );
		llDelete = (LinearLayout)view.findViewById( R.id.ll_delete );
		cbAll = (CheckBox)view.findViewById( R.id.cb_all );
		btnDelete = (Button)view.findViewById( R.id.btn_delete );
		btnCollection = (Button)view.findViewById( R.id.btn_collection );

		ll_empty_shopcart = (LinearLayout) view.findViewById(R.id.ll_empty_shopcart);
		ivEmpty = (ImageView)view.findViewById( R.id.iv_empty );
		tvEmptyCartTobuy = (TextView)view.findViewById( R.id.tv_empty_cart_tobuy );

		btnCheckOut.setOnClickListener( this );
		btnDelete.setOnClickListener( this );
		btnCollection.setOnClickListener( this );

		initListener();

		return view;
	}

	private void initListener() {
		//设置默认为编辑状态
		tvShopcartEdit.setTag(ACTION_EDIT);
		tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int action = (int) v.getTag();
				if (action == ACTION_EDIT){
					//切换成完成状态
					showDeleteView();
				}else {
					//切换成编辑状态
					hideDeleteView();
				}
			}
		});
	}

	private void showDeleteView() {
		//1.编辑变成完成
		tvShopcartEdit.setTag(ACTION_COMPLETE);
		tvShopcartEdit.setText("完成");
		//2.变成非全选
		if (adapter != null){
			adapter.CheckAll_none(false);
			adapter.CheckAll();
		}
		//3.删除视图显示
		llDelete.setVisibility(View.VISIBLE);
		//4.结算视图隐藏
		llCheckAll.setVisibility(View.GONE);
	}

	private void hideDeleteView() {
		//1.完成变成编辑
		tvShopcartEdit.setTag(ACTION_EDIT);
		tvShopcartEdit.setText("编辑");
		//2.变成全选
		if (adapter != null){
			adapter.CheckAll_none(true);
			adapter.CheckAll();
			adapter.showTotalPrice();
		}
		//3.删除视图隐藏
		llDelete.setVisibility(View.GONE);
		//4.结算视图显示
		llCheckAll.setVisibility(View.VISIBLE);
	}

	/**
	 * Handle button click events<br />
	 * <br />
	 * Auto-created on 2017-08-07 15:46:57 by Android Layout Finder
	 * (http://www.buzzingandroid.com/tools/android-layout-finder)
	 */
	@Override
	public void onClick(View v) {
		if ( v == btnCheckOut ) {
			// Handle clicks for btnCheckOut
		} else if ( v == btnDelete ) {
			// Handle clicks for btnDelete
			//删除选中的商品
			adapter.deleteData();
			//检验是否勾选的状态
			adapter.CheckAll();
			//当数据大小为0的时候
			if (adapter.getItemCount() == 0){
				emptyShoppingCart();
			}
		} else if ( v == btnCollection ) {
			// Handle clicks for btnCollection
		}
	}

	@Override
	public void initData() {
		super.initData();

	}


	/**
	 * 在不可见的视图中执行
	 */
	@Override
	public void onResume() {
		super.onResume();
		showData();
		hideDeleteView();
	}

	/**
	 * 显示购物车页面的数据
	 */
	private void showData() {
		List<GoodsBean> goodsBeanList = CartStorage.getInstance().getAllData();
		if (goodsBeanList != null && goodsBeanList.size()>0){
			//有数据
			tvShopcartEdit.setVisibility(View.VISIBLE);
			llCheckAll.setVisibility(View.VISIBLE);
			//把当没有数据时的布局隐藏
			ll_empty_shopcart.setVisibility(View.GONE);
			//设置适配器
			adapter = new ShoppingCartAdapter(mContext,goodsBeanList,
					tvShopcartTotal,checkboxAll,cbAll);
			recyclerview.setAdapter(adapter);
			//设置布局管理器
			recyclerview.setLayoutManager(new LinearLayoutManager(mContext,
					LinearLayoutManager.VERTICAL,false));
		}else {
			//没有数据
			//显示数据为空的布局
			emptyShoppingCart();
		}
	}

	private void emptyShoppingCart() {
		ll_empty_shopcart.setVisibility(View.VISIBLE);
		tvShopcartEdit.setVisibility(View.GONE);
		llDelete.setVisibility(View.GONE);
	}
}
