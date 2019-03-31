package dxp.mst.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dxp.com.dao.RoleFuncComDao;
import dxp.com.util.RequestUtils;
import dxp.com.util.ResponseUtils;

import com.alibaba.fastjson.JSONObject;

@Controller
public class MstIndexController {

	@Resource
	private RoleFuncComDao roleFuncComDao;
	
	/**
     * <B>方法名称：</B>系统首页<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param request 页面请求
     * @param response 页面响应
     * @param dataYear 年份
     * @return ModelAndView 模型视图
     */
    @RequestMapping("/mstindex.html")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView ret = new ModelAndView();
        String userId = RequestUtils.getCurrentUserId(request) == null ? "admin" : RequestUtils.getCurrentUserId(request);
        String userName = RequestUtils.getCurrentUserName(request) == null ? "admin" : RequestUtils.getCurrentUserName(request);
        String roleCode = RequestUtils.getCurrentRoleCode(request) == null ? "SYS_ADMIN" : RequestUtils.getCurrentRoleCode(request);
        String orgId = RequestUtils.getCurrentOrgId(request) == null ? "00000000000000000000000000000000" : RequestUtils.getCurrentOrgId(request);
        List<JSONObject> funcList = this.roleFuncComDao.getFuncList(roleCode, "02");
        ret.addObject("funcList", funcList.toString());
        return ret;
    }
    
    @RequestMapping("/mstFuncList.json")
    public void sysFuncList(HttpServletRequest request, HttpServletResponse response, String id){
    	String roleCode = RequestUtils.getCurrentRoleCode(request) == null ? "SYS_ADMIN" : RequestUtils.getCurrentRoleCode(request);
    	List<JSONObject> records = new ArrayList<JSONObject>();
    	List<JSONObject> funcList = this.roleFuncComDao.getFuncList(roleCode, id);
    	for (JSONObject jsonObject : funcList) {
			JSONObject record = new JSONObject();
			//'id' ,'text' , 'type' , 'leaf', 'url'
			record.put("id", jsonObject.getString("FUNC_CODE"));
			record.put("text", jsonObject.getString("FUNC_NAME"));
			record.put("type", jsonObject.getString("FUNC_TYPE"));
			record.put("leaf", true);
			record.put("url", jsonObject.getString("FUNC_PATH"));
			records.add(record);
		}
    	ResponseUtils.putJsonResponse(response, records);
    	
    }
    
    

}
