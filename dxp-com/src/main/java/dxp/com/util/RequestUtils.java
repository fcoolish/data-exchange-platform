package dxp.com.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import dxp.com.constant.Const;

public final class RequestUtils {

    /**
     * <B>构造方法</B><BR>
     */
    private RequestUtils() {
    }
    
    /**
     * <B>方法名称：</B>获取当前系统标识<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param request 请求
     * @return String 当前用户标识
     */
    public static String getCurrentTag(HttpServletRequest request) {
        return (String) request.getAttribute(Const.REQ_CUR_TAG);
    }
    
    /**
     * <B>方法名称：</B>获取当前用户标识<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param request 请求
     * @return String 当前用户标识
     */
    public static String getCurrentUserId(HttpServletRequest request) {
        return (String) request.getAttribute(Const.REQ_CUR_USER_ID);
    }

    /**
     * <B>方法名称：</B>获取当前用户名称<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param request 请求
     * @return String 当前用户名称
     */
    public static String getCurrentUserName(HttpServletRequest request) {
        return (String) request.getAttribute(Const.REQ_CUR_USER_NAME);
    }

    /**
     * <B>方法名称：</B>获取当前机构标识<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param request 请求
     * @return String 当前机构标识
     */
    public static String getCurrentOrgId(HttpServletRequest request) {
        return (String) request.getAttribute(Const.REQ_CUR_ORG_ID);
    }

    /**
     * <B>方法名称：</B>获取当前角色代码<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param request 请求
     * @return String 当前角色代码
     */
    public static String getCurrentRoleCode(HttpServletRequest request) {
        return (String) request.getAttribute(Const.REQ_CUR_ROLE_CODE);
    }    
    
    /**
     * <B>方法名称：</B>获取客户端Cookie信息<BR>
     * <B>概要说明：</B>根据指定的名称，获取客户端Cookie信息。<BR>
     * 
     * @param request 请求
     * @param name Cookie名
     * @return Cookie Cookie信息
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }
    
    /**
     * <B>方法名称：</B>获取客户端Cookie值<BR>
     * <B>概要说明：</B>根据指定的名称，获取客户端Cookie值。<BR>
     * 
     * @param request 请求
     * @param name Cookie名
     * @return String Cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookieByName(request, name);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }
    
    /**
     * <B>方法名称：</B>获取Cookie验证值<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param request 请求
     * @return String Cookie验证值
     */
    public static String getValidateKey(HttpServletRequest request) {
        return getCookieValue(request, Const.COOKIE_VALIDATE_KEY);
    }
    
    /**
     * <B>方法名称：</B>获取会话属性值<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param request 请求
     * @param name 属性名
     * @return String 属性值
     */
    public static String getSessionValue(HttpServletRequest request, String name) {
        Object attr = request.getSession().getAttribute(name);
        if (attr == null) {
            return null;
        }
        return (String) attr;
    }

    /**
     * <B>方法名称：</B>获取客户端信息<BR>
     * <B>概要说明：</B>根据请求获取客户端信息。<BR>
     * 
     * @param request 请求
     * @return String 客户端信息
     */
    public static String getClientInfo(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * <B>方法名称：</B>判断客户端是否为Firefox<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param request 请求
     * @return boolean 是否为Firefox
     */
    public static boolean isFirefox(HttpServletRequest request) {
        String agent = request.getHeader("USER-AGENT").toUpperCase();
        return (!StringUtils.isBlank(agent) && agent.indexOf("FIREFOX") > 0);
    }

    /**
     * <B>方法名称：</B>获取上传文件<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param request 请求
     * @return MultipartFile 上传文件
     */
    public static MultipartFile getUploadFile(HttpServletRequest request) {
        return getUploadFile(request, "file");
    }

    /**
     * <B>方法名称：</B>获取上传文件<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param request 请求
     * @param name 文件名
     * @return MultipartFile 上传文件
     */
    public static MultipartFile getUploadFile(HttpServletRequest request, String name) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        return multipartRequest.getFile(name);
    }

    /**
     * <B>方法名称：</B>保存上传文件<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param request 请求
     * @param path 保存路径
     * @throws IOException 预想外异常错误
     */
    public static void saveUploadFile(HttpServletRequest request, String path) throws IOException {
        MultipartFile file = getUploadFile(request);
        String sep = System.getProperty("file.separator");
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        String fullPath = path + sep + file.getOriginalFilename();
        File uploadedFile = new File(fullPath);
        file.transferTo(uploadedFile);
    }

}
