package com.example.administrator.shoppingmall.home.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/6.
 *    意图传递需要实现序列化
 */

public class GoodsBean implements Serializable {
	private String cover_price;
	private String firgure;
	private String name;
	private String product_id;

	private int number = 1;

	private Boolean isChecked = true;

	public Boolean getChecked() {
		return isChecked;
	}

	public void setChecked(Boolean checked) {
		isChecked = checked;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCover_price() {
		return cover_price;
	}

	public void setCover_price(String cover_price) {
		this.cover_price = cover_price;
	}

	public String getFirgure() {
		return firgure;
	}

	public void setFirgure(String firgure) {
		this.firgure = firgure;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	@Override
	public String toString() {
		return "GoodsBean{" +
				"cover_price='" + cover_price + '\'' +
				", firgure='" + firgure + '\'' +
				", name='" + name + '\'' +
				", product_id='" + product_id + '\'' +
				", number=" + number +
				", isChecked=" + isChecked +
				'}';
	}
}
