package dxp.mst.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Repository;

import dxp.com.constant.Const;
import dxp.com.dao.BaseJdbcDao;

import com.alibaba.fastjson.JSONObject;

@Repository
public class MstSiteDao extends BaseJdbcDao {

	public List<JSONObject> getList(JSONObject jsonParam, Integer start , Integer limit){
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT MS.*,                                                                                   					");
		sql.append("        (SELECT MC.NAME FROM MST_CODE MC WHERE MC.TYPE ='SITE_TYPE' AND MC.CODE= MS.SITE_TYPE) AS SITE_TYPE_NAME,   ");
		sql.append("        (SELECT MD.DIST_NAME FROM MST_DIST MD WHERE MD.DIST_CODE = MS.DIST_CODE) AS DIST_NAME,  					");
		sql.append("        (SELECT ORG_NAME FROM MST_ORG MO WHERE MO.ORG_ID = MS.ORG_ID) AS ORG_NAME               					");
		sql.append("        FROM MST_SITE MS WHERE 1=1                                                                       			");		
		List<Object> sqlArgs = new ArrayList<Object>();
		addWhereCondition(sql, sqlArgs, jsonParam);
		super.appendPageSql(sql, start, limit);
		return this.queryForJsonList(sql.toString(), sqlArgs.toArray());
	}
	
	private void addWhereCondition(StringBuffer sql, List<Object> sqlArgs, JSONObject jsonParam) {
		System.out.println(jsonParam);
        String siteType = jsonParam.getString("siteType");
        String siteName = jsonParam.getString("siteName");
        String expired = jsonParam.getString("expired");
        if (!StringUtils.isBlank(siteName)) {
            sql.append(" AND MS.SITE_NAME LIKE ? ");
            sqlArgs.add("%" + siteName + "%");
        } 
        if (!StringUtils.isBlank(siteType)) {
            sql.append(" AND MS.SITE_TYPE = ? ");
            sqlArgs.add(siteType);
        }  
        if (!StringUtils.isBlank(expired)) {
            if (Const.TRUE.equals(expired)) {
                sql.append(" AND MS.EXPIRED IS NOT NULL ");
            }            
        } 
        else {
            sql.append(" AND MS.EXPIRED IS NULL ");
        } 
	}

	public int getTotal(JSONObject jsonParam){
        StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT(*) FROM MST_SITE MS WHERE 1=1 ");
        List<Object> sqlArgs = new ArrayList<Object>();
        addWhereCondition(sql, sqlArgs, jsonParam);
        return super.getJdbcTemplate().queryForObject(sql.toString(), Integer.class, sqlArgs.toArray());		
	}
	
	public int insert(JSONObject json) throws Exception{
        Date buildDate = null;
        if (!StringUtils.isBlank(json.getString("BUILD_DATE"))) {
        	buildDate = DateUtils.parseDate(json.getString("BUILD_DATE"), Const.FORMAT_DATE);
        }        
        String sql = " INSERT INTO MST_SITE (SITE_ID, SITE_NAME, SITE_TYPE, DIST_CODE, ORG_ID, TEL, BUILD_DATE, DESC_INFO, MASTER, LINE1, LINE2, PARKS, TOTAL_AREA, SITE_IMG, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSTIMESTAMP, ?, SYSTIMESTAMP)";
        Object[] args = { super.generateKey(), json.getString("SITE_NAME"), json.getString("SITE_TYPE"),
    			json.getString("DIST_CODE"), json.getString("ORG_ID"), json.getString("TEL"),
                buildDate, json.getString("DESC_INFO"), json.getString("MASTER"), json.getString("LINE1"),
                json.getString("LINE2"), json.getString("PARKS"), json.getString("TOTAL_AREA"),
                json.getString("SITE_IMG"), json.getString("DETAIL_BY"), json.getString("DETAIL_BY")};
        return super.getJdbcTemplate().update(sql, args);
	}
	
    public int update(JSONObject json) throws Exception {
        Date buildDate = null;
        if (!StringUtils.isBlank(json.getString("BUILD_DATE"))) {
        	buildDate = DateUtils.parseDate(json.getString("BUILD_DATE"), Const.FORMAT_DATE);
        }        
        String sql = " UPDATE MST_SITE SET SITE_NAME = ?, SITE_TYPE = ?, DIST_CODE = ?, ORG_ID = ?, TEL = ?, BUILD_DATE = ?, DESC_INFO = ?, MASTER = ?, LINE1 = ?, LINE2 = ?, PARKS = ?, TOTAL_AREA = ?, SITE_IMG = ?, UPDATE_BY = ?, UPDATE_TIME = SYSTIMESTAMP WHERE SITE_ID = ? ";
        Object[] args = { json.getString("SITE_NAME"), json.getString("SITE_TYPE"), json.getString("DIST_CODE"),
                json.getString("ORG_ID"), json.getString("TEL"), buildDate, json.getString("DESC_INFO"), json.getString("MASTER"), 
                json.getString("LINE1"), json.getString("LINE2"), json.getString("PARKS"), json.getString("TOTAL_AREA"),
                json.getString("SITE_IMG"), json.getString("DETAIL_BY"), json.getString("SITE_ID")};         
        return super.getJdbcTemplate().update(sql.toString(), args);
    }

	public JSONObject get(String siteId) {
		String sql = " SELECT * FROM MST_SITE WHERE SITE_ID = ? ";
		return this.queryForJsonObject(sql, siteId);
	}
	
	
}
