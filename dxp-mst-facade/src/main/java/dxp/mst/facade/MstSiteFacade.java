package dxp.mst.facade;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface MstSiteFacade {
	
	public List<JSONObject> getList(JSONObject jsonParam, Integer start , Integer limit) throws Exception;

	public int getTotal(JSONObject jsonParam);
	
	public void insert(JSONObject jsonObject) throws Exception;
	
	public int update(JSONObject json) throws Exception;
	
	public JSONObject get(String siteId) throws Exception;
	
}
