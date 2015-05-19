package gva.ydh.com.core;





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
	 * @城市id
	 */
	private int region_id;
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
	public int getRegion_id() {
		return region_id;
	}
	public void setRegion_id(int region_id) {
		this.region_id = region_id;
	}
	
	
	

}
