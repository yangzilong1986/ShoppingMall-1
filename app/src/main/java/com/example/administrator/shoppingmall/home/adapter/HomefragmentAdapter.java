package com.example.administrator.shoppingmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.shoppingmall.R;
import com.example.administrator.shoppingmall.app.GoodsInfoActivity;
import com.example.administrator.shoppingmall.home.bean.GoodsBean;
import com.example.administrator.shoppingmall.home.bean.ResultBeanData;
import com.example.administrator.shoppingmall.utils.Constans;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;
import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.RotateYTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 */

public class HomefragmentAdapter extends RecyclerView.Adapter {

	/**
	 * 五种类型
	 */
	/**
	 * 横幅广告
	 */
	public static final int BANNER = 0;
	/**
	 * 频道
	 */
	public static final int CHANNEL = 1;
	/**
	 * 活动
	 */
	public static final int ACT = 2;
	/**
	 * 秒杀
	 */
	public static final int SECKILL = 3;
	/**
	 * 推荐
	 */
	public static final int RECOMMEND = 4;
	/**
	 * 热卖
	 */
	public static final int HOT = 5;
	private LayoutInflater mLayoutInflater;
	private Context mContext;
	private ResultBeanData.ResultBean resultBean;

	//当前类型
	public int currentType = BANNER;
	private static final String GOODS_BEAN = "goodsBean";

	public HomefragmentAdapter(Context mContext, ResultBeanData.ResultBean resultBean) {
		this.mContext = mContext;
		this.resultBean = resultBean;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == BANNER){
			return new BannerViewHolder(mContext,mLayoutInflater.inflate(R.layout.banner_viewpager,null));
		}else if (viewType == CHANNEL){
			return new ChannelViewHolder(mContext,mLayoutInflater.inflate(R.layout.gv_channel,null));
		}else if (viewType == ACT){
			return new ActViewHolder(mContext,mLayoutInflater.inflate(R.layout.act_item,null));
		}else if (viewType == SECKILL){
			return new SeckillViewHolder(mContext,mLayoutInflater.inflate(R.layout.seckill_item,null));
		}else if (viewType == RECOMMEND){
			return new RecommendViewHolder(mContext,mLayoutInflater.inflate(R.layout.recommend_item,null));
		}else if (viewType == HOT){
			return new HotViewHolder(mContext,mLayoutInflater.inflate(R.layout.hot_item,null));
		}
		return null;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (getItemViewType(position) == BANNER){
			BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
			bannerViewHolder.setData(resultBean.getBanner_info());
		}else if (getItemViewType(position) == CHANNEL){
			ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
			channelViewHolder.setData(resultBean.getChannel_info());
		}else if (getItemViewType(position) == ACT){
			ActViewHolder actViewHolder = (ActViewHolder) holder;
			actViewHolder.setData(resultBean.getAct_info());
		}else if (getItemViewType(position) == SECKILL){
			SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
			seckillViewHolder.setData(resultBean.getSeckill_info());
		}else if (getItemViewType(position) == RECOMMEND){
			RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
			recommendViewHolder.setData(resultBean.getRecommend_info());
		}else if (getItemViewType(position) == HOT){
			HotViewHolder hotViewHolder = (HotViewHolder) holder;
			hotViewHolder.setData(resultBean.getHot_info());
		}

	}

	//横幅广告
	class BannerViewHolder extends RecyclerView.ViewHolder{

		private Context mContext;
		private Banner banner;
		public BannerViewHolder(Context mContext, View itemView) {
			super(itemView);
			this.mContext = mContext;
			this.banner = (Banner) itemView.findViewById(R.id.banner);
		}

		public void setData(List<ResultBeanData.ResultBean.BannerInfoBean> banner_info) {
			List<String> imagesUrls = new ArrayList<>();
			for (int i =0;i<resultBean.getBanner_info().size();i++){
				imagesUrls.add(banner_info.get(i).getImage());
			}
			banner.setImages(imagesUrls, new OnLoadImageListener() {
				@Override
				public void OnLoadImage(ImageView view, Object url) {
					Glide.with(mContext).load(Constans.BASE_IMAGE_URL+url).into(view);
				}
			});
			//设置轮播图
			//设置轮播图动画
			banner.setBannerAnimation(Transformer.Accordion);
			//设置轮播图的样式
			banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
			//设置轮播图的点击事件
			banner.setOnBannerClickListener(new OnBannerClickListener() {
				@Override
				public void OnBannerClick(int position) {
					Toast.makeText(mContext,"点击了第"+position+"张图片",Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
	//频道
	class ChannelViewHolder extends RecyclerView.ViewHolder{
		private Context mContext;
		private GridView gv_channel;
		private ChannelAdapter adapter;
		public ChannelViewHolder(final Context mContext, View itemView) {
			super(itemView);
			this.mContext = mContext;
			gv_channel = (GridView) itemView.findViewById(R.id.gv_channel);

			//设置点击事件
			gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Toast.makeText(mContext,"position=="+position,Toast.LENGTH_SHORT).show();
				}
			});
		}

		public void setData(List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
			//设置GridView的设配器
			adapter = new ChannelAdapter(mContext,channel_info);
			gv_channel.setAdapter(adapter);
		}
	}

	//活动
	class ActViewHolder extends RecyclerView.ViewHolder{
		private Context mContext;
		private ViewPager act_viewpager;
		public ActViewHolder(Context mContext, View itemView) {
			super(itemView);
			this.mContext = mContext;
			act_viewpager = (ViewPager) itemView.findViewById(R.id.act_viewpager);
		}

		public void setData(final List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
			//设置适配器的属性
			act_viewpager.setPageMargin(40);
			act_viewpager.setPageTransformer(true, new RotateYTransformer(new AlphaPageTransformer()));
			//设置viewPager的设配器
			act_viewpager.setAdapter(new PagerAdapter() {
				@Override
				public int getCount() {
					return act_info==null ? 0 :act_info.size();
				}

				@Override
				public boolean isViewFromObject(View view, Object object) {
					return view == object;
				}

				@Override
				public Object instantiateItem(ViewGroup container, final int position) {
					ImageView imageView = new ImageView(mContext);
					//设置imageView的属性
					imageView.setScaleType(ImageView.ScaleType.FIT_XY);
					//联网获取图片
					Glide.with(mContext).load(Constans.BASE_IMAGE_URL
							+act_info.get(position).getIcon_url()).into(imageView);

					//添加到容器中
					container.addView(imageView);

					//设置ImageView的点击事件
					imageView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Toast.makeText(mContext,"position=="+position,Toast.LENGTH_SHORT).show();
						}
					});
					return imageView;
				}

				@Override
				public void destroyItem(ViewGroup container, int position, Object object) {
					container.removeView((View) object);
				}
			});
		}
	}
	//秒杀
	class SeckillViewHolder extends RecyclerView.ViewHolder{
		private Context mContext;
		private TextView tv_time_seckill;
		private TextView tv_more_seckill;
		private RecyclerView rv_seckill;
		private SeckillAdapter adapter;

		/**
		 * 相差多少时间  单位：毫秒
		 */
		private long dt = 0;
		private Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				dt = dt - 1000;
				//将毫秒转换为具体小时的工具
				SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
				String time = format.format(new Date(dt));

				tv_time_seckill.setText(time);

				handler.removeMessages(0);
				handler.sendEmptyMessageDelayed(0,1000);
				if (dt<=0){
					handler.removeCallbacksAndMessages(null);
				}
			}
		};


		public SeckillViewHolder(Context mContext, View itemView) {
			super(itemView);
			tv_time_seckill = (TextView) itemView.findViewById(R.id.tv_time_seckill);
			tv_more_seckill = (TextView) itemView.findViewById(R.id.tv_more_seckill);
			rv_seckill = (RecyclerView) itemView.findViewById(R.id.rv_seckill);
			this.mContext = mContext;
		}

		public void setData(final ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {
			//设置Seckill的设配器
			adapter = new SeckillAdapter(mContext,seckill_info);
			rv_seckill.setAdapter(adapter);
			//设置布局管理器
			rv_seckill.setLayoutManager(new LinearLayoutManager(mContext,
					LinearLayoutManager.HORIZONTAL,false));

			//设置item的点击时间
			adapter.setOnSeckillRecyclerView(new SeckillAdapter.OnSeckillRecyclerView() {
				@Override
				public void onItemClick(int position) {
					Toast.makeText(mContext,"秒杀=="+position,Toast.LENGTH_SHORT).show();
					GoodsBean goodsBean = new GoodsBean();
					ResultBeanData.ResultBean.SeckillInfoBean.ListBean seckillInfoBean = seckill_info.getList().get(position);
					goodsBean.setCover_price(seckillInfoBean.getCover_price());
					goodsBean.setFirgure(seckillInfoBean.getFigure());
					goodsBean.setName(seckillInfoBean.getName());
					goodsBean.setProduct_id(seckillInfoBean.getProduct_id());
					startGoodsInfoActivity(goodsBean);
				}
			});


			dt = Integer.valueOf(seckill_info.getEnd_time())-Integer.valueOf(seckill_info.getStart_time());
			//1s
			handler.sendEmptyMessageDelayed(0,1000);
		}
	}
	/*新品推荐*/
	class RecommendViewHolder extends RecyclerView.ViewHolder{
		private Context mContext;
		private TextView MoreRecommend;
		private GridView gv_recommend;
		private RecommendAdapter adapter;
		public RecommendViewHolder(final Context mContext, View itemView) {
			super(itemView);
			this.mContext = mContext;
			MoreRecommend = (TextView) itemView.findViewById(R.id.tv_more_recommend);
			gv_recommend = (GridView) itemView.findViewById(R.id.gv_recommend);


		}

		public void setData(final List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
			//设置gridview的设配器
			adapter = new RecommendAdapter(mContext,recommend_info);
			gv_recommend.setAdapter(adapter);

			//点击事件
			gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Toast.makeText(mContext,"position=="+position,Toast.LENGTH_SHORT).show();
					GoodsBean goodsBean = new GoodsBean();
					ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get(position);
					goodsBean.setCover_price(recommendInfoBean.getCover_price());
					goodsBean.setFirgure(recommendInfoBean.getFigure());
					goodsBean.setName(recommendInfoBean.getName());
					goodsBean.setProduct_id(recommendInfoBean.getProduct_id());
					startGoodsInfoActivity(goodsBean);
				}
			});

		}
	}

	//热卖
	class HotViewHolder extends RecyclerView.ViewHolder{
		private Context mContext;
		private TextView moreHot;
		private GridView gv_hot;
		private HotAdapter hotAdapter;
		public HotViewHolder(final Context mContext, View itemView) {
			super(itemView);
			this.mContext = mContext;
			moreHot = (TextView) itemView.findViewById(R.id.tv_more_hot);
			gv_hot = (GridView) itemView.findViewById(R.id.gv_hot);
		}


		public void setData(final List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
			hotAdapter = new HotAdapter(mContext,hot_info);
			gv_hot.setAdapter(hotAdapter);

			gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Toast.makeText(mContext,"position=="+position,Toast.LENGTH_SHORT).show();
					GoodsBean goodsBean = new GoodsBean();
					ResultBeanData.ResultBean.HotInfoBean hotInfoBean = hot_info.get(position);
					goodsBean.setCover_price(hotInfoBean.getCover_price());
					goodsBean.setFirgure(hotInfoBean.getFigure());
					goodsBean.setName(hotInfoBean.getName());
					goodsBean.setProduct_id(hotInfoBean.getProduct_id());
					startGoodsInfoActivity(goodsBean);
				}
			});
		}
	}


	/**
	 * 实现页面跳转 -----> 商品详情
	 * @param goodsBean
	 */
	private void startGoodsInfoActivity(GoodsBean goodsBean) {
		Intent intent = new Intent(mContext, GoodsInfoActivity.class);
		//传递数据
		intent.putExtra(GOODS_BEAN,goodsBean);
		mContext.startActivity(intent);
	}


	//得到类型
	@Override
	public int getItemViewType(int position) {
		switch (position){
			case BANNER:
				//横幅广告
				currentType = BANNER;
				break;
			case CHANNEL:
				//频道
				currentType = CHANNEL;
				break;
			case ACT:
				//活动
				currentType = ACT;
				break;
			case SECKILL:
				//秒杀
				currentType = SECKILL;
				break;
			case RECOMMEND:
				//新品推荐
				currentType = RECOMMEND;
				break;
			case HOT:
				//热卖
				currentType = HOT;
				break;
		}
		return currentType;
	}

	//返回的条目数量
	@Override
	public int getItemCount() {
		return 6;
	}
}
