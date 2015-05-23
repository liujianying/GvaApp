package com.ydh.gva.location.core;

public class CityEntity {

    /**
     * @类别
     */
    public int type;
    /**
     * @城市名
     */
    private String region_name;

    /**
     * @搞定区域编码
     */
    private int region_cd;
    /**
     * @城市拼音
     */
    public String pinying;

    /**
     * @首字母
     */
    public char fristLetter;


    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }


    public int getRegion_cd() {
        return region_cd;
    }

    public void setRegion_cd(int region_cd) {
        this.region_cd = region_cd;
    }
}
