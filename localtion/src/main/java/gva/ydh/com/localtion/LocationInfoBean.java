package gva.ydh.com.localtion;


public class LocationInfoBean {

	/**
	 * 定位到的用户的纬度
	 */
	private double latitude;
	/**
	 * 定位到的用户的经度
	 */
	private double longitude;
	/**  
	 * 定位到的用户的位置全称
	 */
	private String user_current_location;
	/**
	 * 获取的省份的信息
	 */
	public String provinceName;
	/**o
	 * 获取城市信息
	 */
	public String cityName;
	/**
	 * 获取区县信息
	 */
	public String district;
	/**
	 * 街道
	 */
	public String townsLoaction;
	/**
	 * 最后加载时间
	 */
	private long lastLoadingTime;


	private String adCode;

    /**
     * 区域ID
     */
    private String reginId;

    private int regionIdForFlee = -1;
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getUser_current_location() {
		return user_current_location;
	}

	public void setUser_current_location(String user_current_location) {
		this.user_current_location = user_current_location;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getTownsLoaction() {
		return townsLoaction;
	}

	public void setTownsLoaction(String townsLoaction) {
		this.townsLoaction = townsLoaction;
	}

	public long getLastLoadingTime() {
		return lastLoadingTime;
	}

	public void setLastLoadingTime(long lastLoadingTime) {
		this.lastLoadingTime = lastLoadingTime;
	}

	public String getAdCode() {
		return adCode;
	}

	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}

    /**
     * 是否有位置
     * @return
     */
    public boolean hasLocation() {
        if (getLatitude()>0 && getLongitude()>0) {
            return true;
        }
        return false;
    }

    public String getReginId() {
        return reginId;
    }

    public void setReginId(String reginId) {
        this.reginId = reginId;
    }

    public int getRegionIdForFlee() {
        return regionIdForFlee;
    }

    public void setRegionIdForFlee(int regionIdForFlee) {
        this.regionIdForFlee = regionIdForFlee;
    }
}
