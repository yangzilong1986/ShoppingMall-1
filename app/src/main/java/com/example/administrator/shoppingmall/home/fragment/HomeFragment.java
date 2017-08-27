package com.example.administrator.shoppingmall.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.shoppingmall.R;
import com.example.administrator.shoppingmall.base.BaseFragment;
import com.example.administrator.shoppingmall.home.adapter.HomefragmentAdapter;
import com.example.administrator.shoppingmall.home.bean.ResultBeanData;
import com.example.administrator.shoppingmall.utils.Constans;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/1.
 */

public class HomeFragment extends BaseFragment {


	@Bind(R.id.tv_search_home)
	TextView mTvSearchHome;
	@Bind(R.id.tv_message_home)
	TextView mTvMessageHome;
	@Bind(R.id.rv_home)
	RecyclerView mRvHome;
	@Bind(R.id.ib_top)
	ImageButton mIbTop;
	private static final String TAG = HomeFragment.class.getSimpleName();
	private ResultBeanData.ResultBean resultBean;
	private HomefragmentAdapter adapter;

	@Override
	public View initView() {
		View view = View.inflate(mContext, R.layout.fragment_home, null);
		return view;
	}


	@Override
	public void initData() {
		super.initData();
		getDataFromNet();
	}

	private void getDataFromNet() {
		String url = Constans.HOME_URL;
		OkHttpUtils
				.get()
				.url(url)
				.build()
				.execute(new StringCallback()
				{
					@Override
					public void onError(Call call, Exception e, int id) {
						Log.e(TAG,"首页请求信息失败=="+e.getMessage());
					}

					@Override
					public void onResponse(String response, int id) {
						Log.e(TAG,"首页请求信成功=="+response);
						/*解析数据*/
						processData(response);
					}
					
				});
	}

	private void processData(String json) {
		ResultBeanData resultBeanData = JSON.parseObject(json,ResultBeanData.class);
		resultBean = resultBeanData.getResult();
		if (resultBean != null){
			//有数据
			 adapter = new HomefragmentAdapter(mContext,resultBean);
			mRvHome.setAdapter(adapter);

			GridLayoutManager manager = new GridLayoutManager(mContext,1);
			//设置滑动到哪个位置了的监听
			manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
				@Override
				public int getSpanSize(int position) {
					if (position <= 3){
						mIbTop.setVisibility(View.GONE);
					}else {
						mIbTop.setVisibility(View.VISIBLE);
					}
					//只能返回1
					return 1;
				}
			});

			//设置布局管理器
			mRvHome.setLayoutManager(manager);
		}else {
			//没有数据
		}
		Log.e(TAG,"请求成功=="+resultBean.getHot_info().get(0).getName());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO: inflate a fragment view
		View rootView = super.onCreateView(inflater, container, savedInstanceState);
		ButterKnife.bind(this, rootView);
		return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	@OnClick({R.id.tv_message_home, R.id.tv_search_home, R.id.ib_top})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.tv_message_home:
				Toast.makeText(mContext, "进入消息中心",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.tv_search_home:
				Toast.makeText(mContext, "搜索",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.ib_top:
				mRvHome.scrollToPosition(0);
				break;
		}
	}
}
