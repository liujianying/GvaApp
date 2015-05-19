package gva.ydh.com.core;

/**
 * Created by Administrator on 2014/9/28.
 */
public class SysRegion {
    private int regionId=-1;
    private int regnType;
    private String regionName;
    private int superId;


    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getRegnType() {
        return regnType;
    }

    public void setRegnType(int regnType) {
        this.regnType = regnType;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getSuperId() {
        return superId;
    }

    public void setSuperId(int superId) {
        this.superId = superId;
    }
}
