package com.ydh.gva.location.localtion;

public class LocationControlBean {
	/**
	 * 间隔时间
	 */
	private long spaceTime;
	/**
	 * 操作类型
	 */
	private LocationUpdateType updatelType;
	/**
	 *  定位获取 距离变化   单位为米
	 */
	private float spaceDistance  ; 
	/**
	 * 间隔实行次数
	 */
	private int  SpaceRunTime;

    private boolean isUpdateServerLocation;

	public long getSpaceTime() {
		return spaceTime;
	}

	public void setSpaceTime(long spaceTime) {
		this.spaceTime = spaceTime;
	}

	public LocationUpdateType getUpdatelType() {
		return updatelType;
	}

	public void setUpdatelType(LocationUpdateType updatelType) {
		this.updatelType = updatelType;
	}

	public float getSpaceDistance() {
		return spaceDistance;
	}

	public void setSpaceDistance(float spaceDistance) {
		this.spaceDistance = spaceDistance;
	}

	public int getSpaceRunTime() {
		return SpaceRunTime;
	}

	public void setSpaceRunTime(int spaceRunTime) {
		SpaceRunTime = spaceRunTime;
	}

    public boolean isUpdateServerLocation() {
        return isUpdateServerLocation;
    }

    public void setUpdateServerLocation(boolean isUpdateServerLocation) {
        this.isUpdateServerLocation = isUpdateServerLocation;
    }
}
