package com.example.administrator.shoppingmall.shoppingcar.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.example.administrator.shoppingmall.app.MyApplication;
import com.example.administrator.shoppingmall.home.bean.GoodsBean;
import com.example.administrator.shoppingmall.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 *   购物车存储类
 */

public class CartStorage {
	public static final String JSON_CART = "json_cart";
	private static CartStorage instance;
	private Context mContext;
	//SparseArray的性能优于HashMap
	private SparseArray<GoodsBean> sparseArray;
	public CartStorage(Context mContext){
		this.mContext = mContext;

		//把之前存储的数据读取出来
		sparseArray = new SparseArray<>(100);

		listToSparseArray();

	}


	/**
	 * 从本地读取的数据加入到SparseArray中
	 */
	private void listToSparseArray() {
		List<GoodsBean> goodsBeanList = getAllData();
		//把list数据转换成SparseArray
		for (int i=0;i<goodsBeanList.size();i++){
			GoodsBean goodsBean = goodsBeanList.get(i);
			sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
		}
	}

	/**
	 * 获取本地所有的数据
	 * @return
	 */
	public List<GoodsBean> getAllData() {
		List<GoodsBean> goodsBeanList = new ArrayList<>();
		//1.从本地获取
		String json = CacheUtils.getString(mContext, JSON_CART);

		//2.使用Gson转换成列表list
		//当json数据不为空时执行
		if (!TextUtils.isEmpty(json)){
			//把String转换成list
			goodsBeanList = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>(){}.getType());
		}
		return goodsBeanList;
	}

	public static CartStorage getInstance(){
		if (instance == null){
			instance = new CartStorage(MyApplication.getContext());
		}
		return instance;
	}


	/*增删改查*/
	/**
	 * 添加数据
	 * @param goodsBean
	 */
	public void addData(GoodsBean goodsBean){
		//1.添加到内存中，实际上就是添加到SparseArray中
		//如果当前有数据存在，就修改成number递增
		GoodsBean tempData = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
		if (tempData != null){
			//内存中有了这条数据
			tempData.setNumber(tempData.getNumber()+1);
			tempData.setNumber(1);
		}else {
			tempData = goodsBean;
			tempData.setNumber(1);
		}
		//同步到内存中
		sparseArray.put(Integer.parseInt(tempData.getProduct_id()),tempData);
		//2.同步到本地
		savaLocal();
	}

	/**
	 * 删除数据
	 * @param goodsBean
	 */
	public void deleteData(GoodsBean goodsBean){
		//1.从内存中删除
		sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));
		//2.同步到本地
		savaLocal();
	}

	/**
	 * 更新数据
	 * @param goodsBean
	 */
	public void updateData(GoodsBean goodsBean){
		//1.从内存中更新
		sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
		//2.同步到本地
		savaLocal();
	}

	/**
	 * 保存数据到本地
	 */
	public void savaLocal() {
		//1.把SparseArray转换成list
		List<GoodsBean> goodsBeanList = sparseArrayToList();
		//2.使用Gson把数据转换成String类型
		String json = new Gson().toJson(goodsBeanList);
		//3.把String数据保存
		CacheUtils.saveString(mContext,JSON_CART,json);
	}

	private List<GoodsBean> sparseArrayToList() {
		List<GoodsBean> goodsBeanList = new ArrayList<>();
		for (int i=0;i<sparseArray.size();i++){
			GoodsBean goodsBean = sparseArray.valueAt(i);
			goodsBeanList.add(goodsBean);
		}
		return goodsBeanList;
	}

}
