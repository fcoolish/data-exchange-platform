package dxp.com.dao;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;

@Repository
public class RoleFuncComDao extends BaseJdbcDao{

	public List<JSONObject> getFuncList(String roleCode, String funcCode){
		
		if(StringUtils.isBlank(roleCode) || StringUtils.isBlank(funcCode)){
			return Collections.emptyList();
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT F.* FROM SYS_ROLE R JOIN SYS_ROLE_FUNC RF   ");
		sql.append("            ON(R.ROLE_CODE = RF.ROLE_CODE)          ");
		sql.append("            JOIN SYS_FUNC F                         ");
		sql.append("            ON(RF.FUNC_CODE = F.FUNC_CODE)          ");
		sql.append("            WHERE R.ROLE_CODE = ?         			");
		sql.append("            AND F.FUNC_CODE LIKE ?             		");
		sql.append("            AND R.DISABLE_FLAG = '0'                ");
		sql.append("            AND F.DISABLE_FLAH = '0'                ");	

		funcCode = funcCode + "__";
		
		return this.queryForJsonList(sql.toString(), roleCode, funcCode);
	}
	
}
