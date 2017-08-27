package com.example.administrator.shoppingmall.app;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.shoppingmall.R;
import com.example.administrator.shoppingmall.home.bean.GoodsBean;
import com.example.administrator.shoppingmall.shoppingcar.utils.CartStorage;
import com.example.administrator.shoppingmall.utils.Constans;

public class GoodsInfoActivity extends Activity implements View.OnClickListener {
	private ImageButton ibGoodInfoBack;
	private ImageButton ibGoodInfoMore;
	private ImageView ivGoodInfoImage;
	private TextView tvGoodInfoName;
	private TextView tvGoodInfoDesc;
	private TextView tvGoodInfoPrice;
	private TextView tvGoodInfoStore;
	private TextView tvGoodInfoStyle;
	private WebView wbGoodInfoMore;
	private LinearLayout llGoodsRoot;
	private TextView tvGoodInfoCallcenter;
	private TextView tvGoodInfoCollection;
	private TextView tvGoodInfoCart;
	private Button btnGoodInfoAddcart;

	private TextView tvMoreShare;
	private TextView tvMoreSearch;
	private Button btnMore;
	private GoodsBean goodsBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_info);
		findViews();

		//接受数据
		 goodsBean = (GoodsBean) getIntent().getSerializableExtra("goodsBean");
		if (goodsBean != null){
//			Toast.makeText(this,"goodsBean ==" + goodsBean.toString(),Toast.LENGTH_SHORT).show();
			setDataForView(goodsBean);
		}
	}

	private void setDataForView(GoodsBean goodsBean) {
		Glide.with(this).load(Constans.BASE_IMAGE_URL+goodsBean.getFirgure()).into(ivGoodInfoImage);
		tvGoodInfoName.setText(goodsBean.getName());
		tvGoodInfoPrice.setText("￥"+goodsBean.getCover_price());

		//设置网页
		setWebData(goodsBean.getProduct_id());
	}

	private void setWebData(String product_id) {
		if (product_id != null){
			wbGoodInfoMore.loadUrl("http://www.baidu.com");

			WebSettings webSetting = wbGoodInfoMore.getSettings();
			webSetting.setJavaScriptEnabled(true);//启用支持 javascript
			webSetting.setUseWideViewPort(true);//设置双击页面变大变小
			//优先使用缓存
			webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

			wbGoodInfoMore.setWebViewClient(new WebViewClient(){
				@Override
				public boolean shouldOverrideUrlLoading(WebView view,String url) {
					view.loadUrl(url);
					//返回值是 true 的时候控制去 WebView 打开，为 false 调用系统浏览器或第三方浏览器
					return true;
				}
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						view.loadUrl(request.getUrl().toString());
					}
					//返回值是 true 的时候控制去 WebView 打开，为 false 调用系统浏览器或第三方浏览器
					return true;
				}
			});
		}
	}


	/**
	 * Find the Views in the layout<br />
	 * <br />
	 * Auto-created on 2017-08-05 15:07:53 by Android Layout Finder
	 * (http://www.buzzingandroid.com/tools/android-layout-finder)
	 */
	private void findViews() {
		ibGoodInfoBack = (ImageButton)findViewById( R.id.ib_good_info_back );
		ibGoodInfoMore = (ImageButton)findViewById( R.id.ib_good_info_more );
		ivGoodInfoImage = (ImageView)findViewById( R.id.iv_good_info_image );
		tvGoodInfoName = (TextView)findViewById( R.id.tv_good_info_name );
		tvGoodInfoDesc = (TextView)findViewById( R.id.tv_good_info_desc );
		tvGoodInfoPrice = (TextView)findViewById( R.id.tv_good_info_price );
		tvGoodInfoStore = (TextView)findViewById( R.id.tv_good_info_store );
		tvGoodInfoStyle = (TextView)findViewById( R.id.tv_good_info_style );
		wbGoodInfoMore = (WebView)findViewById( R.id.wb_good_info_more );
		llGoodsRoot = (LinearLayout)findViewById( R.id.ll_goods_root );
		tvGoodInfoCallcenter = (TextView)findViewById( R.id.tv_good_info_callcenter );
		tvGoodInfoCollection = (TextView)findViewById( R.id.tv_good_info_collection );
		tvGoodInfoCart = (TextView)findViewById( R.id.tv_good_info_cart );
		btnGoodInfoAddcart = (Button)findViewById( R.id.btn_good_info_addcart );
		tvMoreShare = (TextView)findViewById( R.id.tv_more_share );
		tvMoreSearch = (TextView)findViewById( R.id.tv_more_search );
		btnMore = (Button)findViewById( R.id.btn_more );

		ibGoodInfoBack.setOnClickListener( this );
		ibGoodInfoMore.setOnClickListener( this );
		btnGoodInfoAddcart.setOnClickListener( this );

		tvGoodInfoCallcenter.setOnClickListener( this );
		tvGoodInfoCollection.setOnClickListener( this );
		tvGoodInfoCart.setOnClickListener( this );

		tvMoreShare.setOnClickListener( this );
		tvMoreSearch.setOnClickListener( this );
		btnMore.setOnClickListener( this );
	}

	/**
	 * Handle button click events<br />
	 * <br />
	 * Auto-created on 2017-08-05 15:07:53 by Android Layout Finder
	 * (http://www.buzzingandroid.com/tools/android-layout-finder)
	 */
	@Override
	public void onClick(View v) {
		if ( v == ibGoodInfoBack ) {
			// Handle clicks for ibGoodInfoBack
			finish();
		} else if ( v == ibGoodInfoMore ) {
			// Handle clicks for ibGoodInfoMore
			Toast.makeText(this,"更多",Toast.LENGTH_SHORT).show();
		} else if ( v == btnGoodInfoAddcart ) {
			// Handle clicks for btnGoodInfoAddcart
			CartStorage.getInstance().addData(goodsBean);
			Toast.makeText(this,"已添加到购物车中",Toast.LENGTH_SHORT).show();
		} else if ( v == tvGoodInfoCallcenter ) {
			// Handle clicks for btnGoodInfoAddcart
			Toast.makeText(this,"联系客服...",Toast.LENGTH_SHORT).show();
		} else if ( v == tvGoodInfoCollection ) {
			// Handle clicks for btnGoodInfoAddcart
			Toast.makeText(this,"已收藏",Toast.LENGTH_SHORT).show();
		} else if ( v == tvGoodInfoCart ) {
			// Handle clicks for btnGoodInfoAddcart
			Toast.makeText(this,"正在跳转到购物车中...",Toast.LENGTH_SHORT).show();
		} else if ( v == tvMoreShare ) {
			// Handle clicks for btnGoodInfoAddcart
			Toast.makeText(this,"正在分享...",Toast.LENGTH_SHORT).show();
		} else if ( v == tvMoreSearch ) {
			// Handle clicks for btnGoodInfoAddcart
			Toast.makeText(this,"正在搜索...",Toast.LENGTH_SHORT).show();
		}else if ( v == btnMore ) {
			// Handle clicks for btnMore
			Toast.makeText(this,"隐藏页面的更多...",Toast.LENGTH_SHORT).show();
		}
	}

}
