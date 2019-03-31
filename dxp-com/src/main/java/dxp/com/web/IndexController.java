package dxp.com.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dxp.com.constant.Const;
import dxp.com.dao.UserComDao;
import dxp.com.util.ResponseUtils;

import com.alibaba.fastjson.JSONObject;

@Controller
public class IndexController {

	private UserComDao userComDao;
	
	public UserComDao getUserComDao() {
		return userComDao;
	}
	@Autowired
	public void setUserComDao(UserComDao userComDao) {
		this.userComDao = userComDao;
	}

	/**
     * <B>方法名称：</B>系统首页<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param request 页面请求
     * @param response 页面响应
     * @param dataYear 年份
     * @return ModelAndView 模型视图
     */
    @RequestMapping("/index.html")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView ret = new ModelAndView();
        String existsCookie = (String) request.getAttribute("existsCookie");
        String basePath = (String) request.getAttribute("basePath");
        String myTag = basePath.substring(basePath.indexOf("-")+1, basePath.length());
        if(existsCookie.equals(Const.FALSE)){
        	//设置cookie
        	String remoteUser = request.getRemoteUser();
        	JSONObject userInfo = this.userComDao.getUserInfo(remoteUser);
        	String userId = userInfo.getString("USER_ID");
        	String userName = userInfo.getString("USER_NAME");
        	String roleCode = userInfo.getString("ROLE_CODE");
        	String orgId = userInfo.getString("ORG_ID");
        	
        	StringBuffer validateKey = new StringBuffer();
        	validateKey.append(userId);
        	validateKey.append(Const.COOKIE_VALIDATE_KEY_SPLIT);
        	validateKey.append(userName);
        	if(!StringUtils.isBlank(roleCode)){
            	validateKey.append(Const.COOKIE_VALIDATE_KEY_SPLIT);
            	validateKey.append(roleCode);        		
        	}
        	if(!StringUtils.isBlank(orgId)){
            	validateKey.append(Const.COOKIE_VALIDATE_KEY_SPLIT);
            	validateKey.append(orgId);        		
        	}
        	ResponseUtils.setValidateKey(response, validateKey.toString());
        } 
        for(String tag : Const.TAGS){
        	if(myTag.equals(tag)){
        		String redirect = "/" + tag + "index.html";
        		System.out.println(redirect);
        		ret.setViewName("redirect:" + redirect);
        	}
        }
        
        return ret;
    }
    

}
