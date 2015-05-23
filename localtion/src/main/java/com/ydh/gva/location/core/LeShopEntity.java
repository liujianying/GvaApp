package com.ydh.gva.location.core;

import java.io.Serializable;
import java.util.List;

/**
 * 乐商列表的乐商Entity
* @ClassName: LeShopEntity  
* @Description:  
* @author xiezw  
* @date 2013-11-11 下午5:28:02  
*
 */
public class LeShopEntity implements Serializable{
	
	private String merchantId;
	private String desc;
	private String icon;
	private String distance;
	private String name;
	/**
	 * 商家的星级
	 */
	private String star;
	/**
	 * 乐商地址
	 */
	private String address;
	/**
	 * 活动，促销
	 */
	private String sales;
	private String lat;
	private String lng;
	private int coupon;//是否有优惠券
	private int cashCoupon;//是否有代金券
	private int mcard;//是否有会员卡
	private int vcard;//是否有储值卡
	private String smallPicture;//小图
	
	private int safeguard ;//是否有保障
	
	
	/**
	 * 乐商属于的分类
	 */
	private List<String> categoryList;
	
	public LeShopEntity(){
		
	}
	
	public LeShopEntity(List<String> categoryList){
		this.categoryList = categoryList;
	}
	
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public List<String> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}

	public int getCoupon() {
		return coupon;
	}

	public void setCoupon(int coupon) {
		this.coupon = coupon;
	}

	public int getCashCoupon() {
		return cashCoupon;
	}

	public void setCashCoupon(int cashCoupon) {
		this.cashCoupon = cashCoupon;
	}

	public int getMcard() {
		return mcard;
	}

	public void setMcard(int mcard) {
		this.mcard = mcard;
	}

	public int getVcard() {
		return vcard;
	}

	public void setVcard(int vcard) {
		this.vcard = vcard;
	}
	/**
	 * 是否有保
	 * @return
	 */
	public int getSafeguard() {
		return safeguard;
	}

	public void setSafeguard(int safeguard) {
		this.safeguard = safeguard;
	}

	public String getSmallPicture() {
		return smallPicture;
	}

	public void setSmallPicture(String smallPicture) {
		this.smallPicture = smallPicture;
	}
}
