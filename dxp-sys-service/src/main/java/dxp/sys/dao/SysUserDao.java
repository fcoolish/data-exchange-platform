package dxp.sys.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import dxp.com.dao.BaseJdbcDao;

import com.alibaba.fastjson.JSONObject;

@Repository("sysUserDao")
public class SysUserDao extends BaseJdbcDao {
	
	private static final String SQL_TABLE_NAME = "SYS_USER";
	private static final String SQL_SELECT_SYS_USER = "SELECT * FROM SYS_USER";
	private static final String SQL_SELECT_SYS_USER_BYID = "SELECT * FROM SYS_USER WHERE USER_ID = ?";
	
	public List<JSONObject> getList() throws Exception {
		return super.queryForJsonList(SQL_SELECT_SYS_USER);
	}
	
	public JSONObject getById(String id){
		return super.queryForJsonObject(SQL_SELECT_SYS_USER_BYID, id);
	}
	
	public int insert(JSONObject jsonObject) throws Exception {
		return super.insert(SQL_TABLE_NAME, jsonObject);
	}
	
}
