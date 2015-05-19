package gva.ydh.com.localtion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import gva.ydh.com.core.AddressEntity;
import gva.ydh.com.core.CharacterParser;
import gva.ydh.com.core.CityEntity;
import gva.ydh.com.util.PingYinUtil;
import gva.ydh.com.util.SafetyUitl;


/**
 * 省，市，区表
 * @author Administrator
 *
 */
public class AddressHelper {

    private AddressDBManager dbOpenHelper ;

    private SQLiteDatabase sqlDatabase;
    public AddressHelper(Context context){
        dbOpenHelper = new AddressDBManager(context);
    }

    /**
     * 获取列表
     * @return
     */
    public List<AddressEntity> getAddressInfo(String province_id , String city_id){

        Cursor cursor = null;
        List<AddressEntity> regionList = new ArrayList<AddressEntity>();
        try {
            sqlDatabase = dbOpenHelper.openDatabase();

            if("".equals(province_id) && "".equals(city_id)){

                cursor = getProviceCursor(sqlDatabase);

            }else if(!"".equals(province_id)){

                cursor = getCityInfo(province_id,sqlDatabase);

            }else if(!"".equals(city_id)){

                cursor = getCityInfo(city_id,sqlDatabase);
            }


            while(cursor.moveToNext()){

                int region_id = cursor.getInt(cursor.getColumnIndex("region_id"));
                int parent_id = cursor.getInt(cursor.getColumnIndex("parent_id"));
                String region_name = cursor.getString(cursor.getColumnIndex("region_name"));
                AddressEntity addressEntity = new AddressEntity();
                addressEntity.setParent_id(parent_id);
                addressEntity.setAddress_name(region_name);
                addressEntity.setAddress_id(region_id);
                regionList.add(addressEntity);
           }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                sqlDatabase.close();
            }catch(Exception e) {
            }

        }

        return regionList;
    }


//    getCounty(SQLiteDatabase sqlDatabase, String parent_id)



    /**
     * 获取市级列表
     * @return
     */
    public List<CityEntity> getCountyMethods(String parent_id){
        List<CityEntity> regionList = new ArrayList<CityEntity>();

        Cursor cursor = null;
        try {
            sqlDatabase = dbOpenHelper.openDatabase();

            cursor = getCounty(sqlDatabase, parent_id);
            CityEntity firstCity = new CityEntity();
            firstCity.setRegion_name("全城");
            firstCity.setRegion_id(SafetyUitl.tryInt(parent_id));
            regionList.add(firstCity);
            while(cursor.moveToNext()){
                String region_name = cursor.getString(cursor.getColumnIndex("region_name"));
                CityEntity addressEntity = new CityEntity();
                addressEntity.setRegion_name(region_name);
                addressEntity.setRegion_id(cursor.getInt(cursor.getColumnIndex("region_id")));

                String py = CharacterParser.getInstance().getSelling(region_name);

                String str = py.substring(0, 1);
                String pinying = py.replaceFirst(str, str.toUpperCase());
                addressEntity.pinying = pinying;
                addressEntity.fristLetter = pinying.charAt(0);
                regionList.add(addressEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                sqlDatabase.close();
            }catch(Exception e) {
            }
        }

        return regionList;
    }

    /**
     * 获取市级列表
     * @return
     */
    public List<CityEntity> getCityInfo(){
        List<CityEntity> regionList = new ArrayList<CityEntity>();
        Cursor cursor = null;

        try {
            sqlDatabase = dbOpenHelper.openDatabase();

            cursor = getCityCursor(sqlDatabase);

            while(cursor.moveToNext()){


                String region_name = cursor.getString(cursor.getColumnIndex("region_name"));
                CityEntity addressEntity = new CityEntity();
                addressEntity.setRegion_name(region_name);
                addressEntity.setRegion_id(cursor.getInt(cursor.getColumnIndex("region_id")));

                String py = PingYinUtil.getPingYin(region_name);
//            String py = CharacterParser.getInstance().getSelling(region_name);
                if("重庆市".equals(region_name)){
                    py = "Chongqingshi";
                } else if("长沙市".equals(region_name)){
                    py = "Changshashi";
                } else if("长春市".equals(region_name)){
                    py = "Changchunshi";
                } else if("厦门市".equals(region_name)){
                    py = "Xiamenshi";
                }
                String str = py.substring(0, 1);
                String pinying = py.replaceFirst(str, str.toUpperCase());
                addressEntity.pinying = pinying;
                addressEntity.fristLetter = pinying.charAt(0);
                regionList.add(addressEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                sqlDatabase.close();
            }catch(Exception e) {
            }
        }
        return regionList;
    }

    /**
     * 获取省列表
     * @return
     */
    private Cursor getProviceCursor(SQLiteDatabase sqlDatabase){

        String sql = "select region_id , parent_id , region_name from ecs_region where parent_id = 1";
        Cursor cursor = sqlDatabase.rawQuery(sql, null);
        return cursor;
    }


    /**
     * @获取市级列表
     * @param sqlDatabase
     * @return
     */
    private Cursor getCityCursor(SQLiteDatabase sqlDatabase){
        String sql = "select region_id , parent_id , region_name from ecs_region where region_type = 2";
        Cursor cursor = sqlDatabase.rawQuery(sql, null);
        return cursor;
    }

    /**
     * 获取区级别列表
     * @param sqlDatabase
     * @param parent_id
     * @return
     */
    private Cursor getCounty(SQLiteDatabase sqlDatabase, String parent_id) {
        String sql = "select  region_id , parent_id , region_name from ecs_region where parent_id=? AND region_type=3";
        Cursor cursor = sqlDatabase.rawQuery(sql, new String[]{parent_id});
        return cursor;
    }



    /**
     * 获取省,区列表
     * @return
     */
    public Cursor getCityInfo(String provice_id,SQLiteDatabase sqlDatabase){

        String sql = "select region_id , parent_id , region_name from ecs_region where parent_id = ?";
        Cursor cursor = sqlDatabase.rawQuery(sql, new String[]{provice_id});
        return cursor;
    }

    /**
     * 通过ID得到区域数据
     * @param region_id
     * @return
     */
    public AddressEntity getRegionInfoById(String region_id){
        AddressEntity addressEntity = new AddressEntity();
        Cursor cursor = null;
        try {
            sqlDatabase = dbOpenHelper.openDatabase();
            String sql = "select region_name from ecs_region where region_id = ?";
            cursor = sqlDatabase.rawQuery(sql, new String[]{region_id});
            if(cursor.moveToNext()){
                addressEntity.setAddress_name(cursor.getString(cursor.getColumnIndex("region_name")));
                addressEntity.setAddress_id(Integer.valueOf(region_id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                sqlDatabase.close();
            }catch(Exception e) {
            }
        }

        return addressEntity;
    }

    /**
     *
     *@category 通过ID得到省或市或区的名字
     *@Title: getNameById
     *@Description:
     *@return String
     *@throws
     *@param region_id
     *@return
     */
    public String getNameById(String region_id){
        Cursor cursor =null;
        String name = "";
        try {
            sqlDatabase = dbOpenHelper.openDatabase();
            String sql = "select region_name from ecs_region where region_id = ?";
            cursor = sqlDatabase.rawQuery(sql, new String[]{region_id});
            if(cursor.moveToNext()){
                name = cursor.getString(cursor.getColumnIndex("region_name"));
//					addressEntity.setAddress_name(cursor.getString(cursor.getColumnIndex("region_name")));
//					addressEntity.setAddress_id(Integer.valueOf(region_id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                sqlDatabase.close();
            }catch(Exception e) {
            }
        }
        return name;
    }

    /**
     *
     *@category 通过省或市或区的名字  得到ID
     *@Title: getNameById
     *@Description:
     *@return String
     *@throws
     *@return
     */
    public String getIdByName(String name){
        String id = "";
        Cursor cursor = null;

        try {
            sqlDatabase = dbOpenHelper.openDatabase();
            String sql = "select region_id from ecs_region where region_name = ?";
            cursor = sqlDatabase.rawQuery(sql, new String[]{name});
            if(cursor.moveToNext()){
                id = cursor.getString(cursor.getColumnIndex("region_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                sqlDatabase.close();
            }catch(Exception e) {
            }
        }

        return id;
    }

    public String getIdByName(String name,String type){
        String id = "";
        Cursor cursor = null;
        try {
            sqlDatabase = dbOpenHelper.openDatabase();
            String sql = "select region_id from ecs_region where region_name = ? and region_type = ?";
            cursor = sqlDatabase.rawQuery(sql, new String[]{name,type});

            if(cursor.moveToFirst()){
                id = cursor.getString(cursor.getColumnIndex("region_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                sqlDatabase.close();
            }catch(Exception e) {
            }
        }

        return id;
    }


    /**
     *
     * @param parent_id
     */
    public List<AddressEntity> getRegionInfoById2(String parent_id){
        List<AddressEntity> regionList = new ArrayList<AddressEntity>();
        Cursor cursor = null;
        try {
            sqlDatabase = dbOpenHelper.openDatabase();
            String sql = "select region_id,region_name from ecs_region where parent_id = ?";
            cursor = sqlDatabase.rawQuery(sql, new String[]{parent_id});

            while(cursor.moveToNext()){
                int region_id = cursor.getInt(cursor.getColumnIndex("region_id"));
                String region_name = cursor.getString(cursor.getColumnIndex("region_name"));
                AddressEntity addressEntity = new AddressEntity();
                addressEntity.setAddress_id(region_id);
                addressEntity.setAddress_name(region_name);
                regionList.add(addressEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                sqlDatabase.close();
            }catch(Exception e) {
            }
        }

        return regionList;
    }


    public String getIdByAdCode(String adCode){
        String id = "";
        Cursor cursor = null;
        try {
            sqlDatabase = dbOpenHelper.openDatabase();
            String sql = "select region_id from ecs_region where region_cd = ?";
            cursor = sqlDatabase.rawQuery(sql, new String[]{adCode});
            if(cursor.moveToFirst()){
                id = cursor.getString(cursor.getColumnIndex("region_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                sqlDatabase.close();
            }catch(Exception e) {
            }
        }

        return id;
    }

    public String getIdByAdCodeAndTownShip(String adCode,String townShip){
        String id = "";
        Cursor cursor = null;
        try {
            sqlDatabase = dbOpenHelper.openDatabase();
            String sql = "select region_id from ecs_region where region_cd like ? and region_name = ? ";
            cursor = sqlDatabase.rawQuery(sql, new String[]{adCode+"%",townShip});
            if(cursor.moveToFirst()){
                id = cursor.getString(cursor.getColumnIndex("region_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                sqlDatabase.close();
            }catch(Exception e) {
            }
        }
        return id;
    }






}
