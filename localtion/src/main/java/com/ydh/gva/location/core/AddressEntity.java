package com.ydh.gva.location.core;

/**
 * 地区bean，用户数据库对象
 *
 * @author Administrator
 */
public class AddressEntity {

    /**
     * 表示 该 地区的ID
     */
    private int address_id;

    /**
     * 该 地区上一个节点的ID
     */
    private int parent_id;
    /**
     * 该地区下一个节点的ID
     */
    private int child_id;

    private String address_name;  //表示该地区的名字


    public int getAddress_id() {
        return address_id;
    }


    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }


    public int getParent_id() {
        return parent_id;
    }


    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }


    public int getChild_id() {
        return child_id;
    }


    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }


    public String getAddress_name() {
        return address_name;
    }


    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }


    @Override
    public String toString() {
        return address_name;
    }


}
