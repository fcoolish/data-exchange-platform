package dxp.mst.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dxp.com.constant.Const;
import dxp.com.service.SysFileComService;
import dxp.com.util.FastJsonConvert;
import dxp.com.util.RequestUtils;
import dxp.com.util.ResponseUtils;
import dxp.mst.facade.MstSiteFacade;

import com.alibaba.fastjson.JSONObject;

@Controller
public class MstSiteController {
	
	@Resource
	private MstSiteFacade mstSiteFacade;

	@Autowired
	private SysFileComService sysFileComService;
	
	@RequestMapping("/site/index.html")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
		
		ModelAndView ret = new ModelAndView();
		return ret;
	}
	
	@RequestMapping("/site/query.json")
	public void query(HttpServletRequest request, HttpServletResponse response, String jsonData, Integer start , Integer limit){
		try {
	        JSONObject json = null;
	        if (!StringUtils.isBlank(jsonData)) {
	        	json = FastJsonConvert.convertJSONToObject(jsonData, JSONObject.class);
	        } 
	        else {
	            json = new JSONObject();
	        }		
			List<JSONObject> records = this.mstSiteFacade.getList(json, start, limit);
			List<JSONObject> rows = new ArrayList<JSONObject>(20);
			for (JSONObject jsonObject : records) {
	            if (jsonObject.getTimestamp("BUILD_DATE") != null) {
	                jsonObject.put("BUILD_DATE", DateFormatUtils.format(jsonObject.getTimestamp("BUILD_DATE"), Const.FORMAT_DATE));
	            }
	            if (jsonObject.getTimestamp("UPDATE_TIME") != null) {
	                jsonObject.put("UPDATE_TIME", DateFormatUtils.format(jsonObject.getTimestamp("UPDATE_TIME"), Const.FORMAT_TIMESTAMP));
	            }            
	            rows.add(jsonObject);			
			}
			int total = this.mstSiteFacade.getTotal(json);
			JSONObject datagrid = new JSONObject();
			datagrid.put(Const.JSON_ROWS, rows);
			datagrid.put(Const.JSON_TOTAL, total);
			ResponseUtils.putJsonResponse(response, datagrid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/site/insert.html")
	public ModelAndView insert(HttpServletRequest request, HttpServletResponse response){
		ModelAndView ret = new ModelAndView();
		ret.setViewName("/site/detail");
		ret.addObject("flag", "insert");
		return ret;
	}

	@RequestMapping("/site/update.html")
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, String jsonData){
		ModelAndView ret = new ModelAndView();
		ret.setViewName("/site/detail");
		ret.addObject("flag", "update");
		ret.addObject("jsonData", jsonData);
		return ret;
	}
	
	@RequestMapping("/site/detail.json")
	@Transactional
	public void detail(HttpServletRequest request, HttpServletResponse response, String jsonData, String flag) throws Exception{
        JSONObject	json = FastJsonConvert.convertJSONToObject(jsonData, JSONObject.class);
        String currentUserId = RequestUtils.getCurrentUserId(request) == null ? Const.SYS_INIT : RequestUtils.getCurrentUserId(request);
        json.put("DETAIL_BY", currentUserId);
        JSONObject messageData = new JSONObject();
        
        JSONObject oldSite = this.mstSiteFacade.get(json.getString("SITE_ID"));
        
        String oldSiteImgId = oldSite.getString("SITE_IMG");
        String newSiteImgId = json.getString("SITE_IMG");
        
        if("insert".equals(flag)){
        	this.mstSiteFacade.insert(json);
            if (!StringUtils.isBlank(newSiteImgId)) {
                this.sysFileComService.clearExpired(newSiteImgId);
            }
        	messageData.put(Const.JSON_SUCCESS, true);
        	messageData.put(Const.JSON_MESSAGE, "添加成功!");
        } else {
        	if((!StringUtils.isBlank(oldSiteImgId)) && (!oldSiteImgId.equals(newSiteImgId))){
        		//set expire
        		this.sysFileComService.expire(oldSiteImgId);
        	}
        	
    		//update 
    		this.mstSiteFacade.update(json);
    		
    		//clear expire
    		if (!StringUtils.isBlank(newSiteImgId)) {
                this.sysFileComService.clearExpired(json.getString("SITE_IMG"));
            }
    		
    		
        	messageData.put(Const.JSON_SUCCESS, true);
        	messageData.put(Const.JSON_MESSAGE, "编辑成功!");
        }
        ResponseUtils.putJsonResponse(response, messageData);
	}	
	
}
