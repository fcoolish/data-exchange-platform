package dxp.com.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import dxp.com.constant.Const;
@Component
public class AuthValidateFilter implements Filter {


	private String basePath = null;
	
	private String indexPath = "/index.html";
	
	/**
     * <B>方法名称：</B>初始化<BR>
     * <B>概要说明：</B>初始化过滤器。<BR>
     * 
     * @param fConfig 过滤器配置
     * @throws ServletException Servlet异常
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        this.basePath = "/" + fConfig.getServletContext().getServletContextName();
    }

    /**
     * <B>方法名称：</B>过滤处理<BR>
     * <B>概要说明：</B>验证访问的合法有效性。<BR>
     * 
     * @param request 请求
     * @param response 响应
     * @param chain 过滤器链
     * @throws IOException IO异常
     * @throws ServletException Servlet异常
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        System.out.println("--------basepath----------- : " + this.basePath);
        String uri = req.getRequestURI();
        System.out.println("--------uri----------- : " + uri);
        String remoteUser = req.getRemoteUser();
        System.out.println("--------remoteUser----------- : " + req.getRemoteUser());
        String validateKey = RequestUtils.getValidateKey(req);
        System.out.println("--------validateKey----------- :" + validateKey);
        
        //如果地址为初始登陆地址，并且当前cookie里没有验证信息,则进入index.html进行设值
        if(StringUtils.isBlank(validateKey)){
        	//url == "dxp-sys/index.html"
        	if(uri.equals(this.basePath + this.indexPath)){
            	//设置是否存在cookie验证信息和basePath
            	req.setAttribute("existsCookie", Const.FALSE);
            	req.setAttribute("basePath", this.basePath);
            	chain.doFilter(request, response);
            	return;        		
        	} else {
        		res.sendRedirect(this.basePath + this.indexPath);
        		return;
        	}
        } 
        
        //如果验证信息不为空,则从cookie中获取信息，进行设置
        if(!StringUtils.isBlank(validateKey)){
        	//设置是否存在cookie验证信息和basePath
        	req.setAttribute("existsCookie", Const.TRUE);
        	req.setAttribute("basePath", this.basePath);
        	String values[] = validateKey.split("\\" + Const.COOKIE_VALIDATE_KEY_SPLIT);
        	req.setAttribute(Const.REQ_CUR_USER_ID, values[0]);
        	req.setAttribute(Const.REQ_CUR_USER_NAME, values[1]);
        	if(values.length > 2){
        		req.setAttribute(Const.REQ_CUR_ROLE_CODE, values[2]);
        		if(values.length > 3){
        			req.setAttribute(Const.REQ_CUR_ORG_ID, values[3]);
        		}
        	}
        }
        chain.doFilter(request, response);
    }

    /**
     * <B>方法名称：</B>释放资源<BR>
     * <B>概要说明：</B>释放过滤器资源。<BR>
     * 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }
    
    
    
}
