package dxp.com.dao;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;

@Repository
public class UserComDao extends BaseJdbcDao{

	private static final String SQL_SELECT_USER_INFO = "SELECT USER_ID, USER_NAME, ROLE_CODE, ORG_ID, DISABLE_FLAG FROM SYS_USER WHERE USER_ID = ?";
	
	public JSONObject getUserInfo(String userId){
		return this.queryForJsonObject(SQL_SELECT_USER_INFO, userId);
	}
	
	
}
