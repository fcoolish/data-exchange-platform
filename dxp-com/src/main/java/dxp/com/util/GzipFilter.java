package dxp.com.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
public class GzipFilter implements Filter {

    /** 参数键值：头信息 */
    public static final String PARAM_KEY_HEADERS = "headers";

    /** 头信息 */
    private Map<String, String> headers;

    /**
     * <B>方法名称：</B>初始化<BR>
     * <B>概要说明：</B>初始化过滤器<BR>
     * 
     * @param fConfig 过滤器配置
     * @throws ServletException Servlet异常
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        String param = fConfig.getInitParameter(PARAM_KEY_HEADERS);
        if (param == null || param.trim().length() < 1) {
            return;
        }
        this.headers = new HashMap<String, String>();
        String[] params = param.split(",");
        String[] kvs = null;
        for (int i = 0; i < params.length; i++) {
            param = params[i];
            if (param != null && param.trim().length() > 0) {
                kvs = param.split("=");
                if (kvs != null && kvs.length == 2) {
                    headers.put(kvs[0], kvs[1]);
                }
            }
        }
        if (this.headers.isEmpty()) {
            this.headers = null;
        }
    }

    /**
     * <B>方法名称：</B>过滤处理<BR>
     * <B>概要说明：</B>设定编码格式<BR>
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
        if (this.headers != null) {
            HttpServletResponse res = (HttpServletResponse) response;
            for (Entry<String, String> header : this.headers.entrySet()) {
                res.addHeader(header.getKey(), header.getValue());
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * <B>方法名称：</B>释放资源<BR>
     * <B>概要说明：</B>释放过滤器资源<BR>
     * 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        this.headers.clear();
        this.headers = null;
    }

}
