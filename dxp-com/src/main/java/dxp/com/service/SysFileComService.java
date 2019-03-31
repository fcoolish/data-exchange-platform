package dxp.com.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import dxp.com.dao.SysFileComDao;
import dxp.com.entity.SysFile;
import dxp.com.util.FastDFSClientUtils;

@Service
@PropertySource("classpath:fastdfs.properties")
public class SysFileComService {

	@Autowired
	private SysFileComDao sysFileComDao;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigure(){
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	/**
	 * FASTDFS_BASEURL
	 */
	@Value("${fastdfs.baseurl}")
	public String FASTDFS_BASEURL;
	
	
	public String key(){
		return this.sysFileComDao.generateKey();
	}
	
	/**
	 * <B>方法名称：</B>文件上传<BR>
	 * <B>概要说明：</B>文件上传<BR>
	 * @param key 键值
	 * @param type 类型
	 * @param userId 用户标识
	 * @param mf 文件对象	
	 * @param expired 过期时间
	 * @param descInfo 描述
	 * @return String 错误信息
	 * @throws Exception 输入输出异常
	 */
    public JSONObject put(String key, String type, String userId, MultipartFile mf, Date expired, String descInfo) throws Exception {
        
    	String filename = mf.getOriginalFilename();
    	String extName = getExtName(filename);
    	String fileId = FastDFSClientUtils.upload(mf.getBytes(), extName);
    	String dataGroup = fileId.substring(0,fileId.indexOf("/"));
    	long bytes = mf.getSize();
    	
        SysFile file = new SysFile();
        file.setKey(key);
        file.setName(filename);
        file.setType(type);
        file.setExt(extName);
        file.setBytes(bytes);
        file.setDataPath(FASTDFS_BASEURL + fileId);
        file.setDataGroup(dataGroup);
        file.setExpired(expired);
        file.setDescInfo(descInfo);
        file.setUpdateBy(userId);
        sysFileComDao.insert(file);
        
        JSONObject ret = new JSONObject();
        ret.put("success", true);
        ret.put("key", key);
        ret.put("type", type);
        ret.put("name", mf.getOriginalFilename());
        ret.put("bytes", mf.getSize());
        ret.put("dataPath", FASTDFS_BASEURL + fileId);
        ret.put("dataGroup", dataGroup);
        ret.put("expired", expired);
        return ret;
    }
    
    /**
     * <B>方法名称：</B>上传DATA数据文件<BR>
     * <B>概要说明：</B>上传DATA数据文件<BR>
     * 
     * @param key 键值
     * @param type 分类
     * @param userId 用户标识
     * @param fileName 文件名称
     * @param expired 过期时间
     * @param data 文件数据
     * @param descInfo 备注信息
     * @return String 错误信息
     * @throws IOException 输入输出异常
     */
    public String put(String key, String type, String userId, String fileName, Date expired, byte[] data, String descInfo)
            throws IOException {

        if (StringUtils.isBlank(fileName)) {
            return "未指定文件名称";
        }
        if (data == null || data.length < 1) {
            return "未指定文件数据";
        }
        
    	String fileId = FastDFSClientUtils.upload(data, getExtName(fileName));
    	String dataGroup = fileId.substring(0,fileId.indexOf("/"));

        SysFile file = new SysFile();
        file.setKey(key);
        file.setName(fileName);
        file.setType(type);
        file.setExt(getExtName(fileName));
        file.setExpired(expired);
        file.setBytes(data.length);
        file.setDataPath(FASTDFS_BASEURL + fileId);
        file.setDataGroup(dataGroup);
        file.setDescInfo(descInfo);
        file.setUpdateBy(userId);
        sysFileComDao.insert(file);

        return null;
    }    

    /**
     * <B>方法名称：</B>获取系统文件信息<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param key 文件键值
     * @return SysFile 系统文件基本信息
     */
    public SysFile getInfo(String key) {
        return sysFileComDao.get(key);
    }

    /**
     * <B>方法名称：</B>获取系统文件信息列表<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param keys 文件键值集合
     * @return List<SysFile> 系统文件信息列表
     */
    public List<SysFile> getInfoList(String... keys) {
        return sysFileComDao.getList(keys);
    }	
    
    /**
     * <B>方法名称：</B>删除系统文件<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param keys 文件键值
     */
    public void delete(String... keys) {
        if (keys == null || keys.length < 1) {
            return;
        }
        if (keys.length == 1) {
            keys = keys[0].split(",");
        }
        for (String key : keys) {
        	SysFile sysFile = sysFileComDao.get(key);
        	String dataPath = sysFile.getDataPath();
        	String fileId = dataPath.substring(FASTDFS_BASEURL.length());
        	String groupName = sysFile.getDataGroup();
        	FastDFSClientUtils.delete(groupName, fileId);
            sysFileComDao.delete(key);
        }
    }


    /**
     * <B>方法名称：</B>系统文件过期<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param keys 文件键值
     */
    public void expire(String... keys) {
        if (keys == null || keys.length < 1) {
            return;
        }
        if (keys.length == 1) {
            keys = keys[0].split(",");
        }
        for (String key : keys) {
            sysFileComDao.expire(key, null);
        }
    }

    /**
     * <B>方法名称：</B>系统文件过期<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param key 文件键值
     * @param expired 过期时间（null为当前时间）
     */
    public void expire(String key, Date expired) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        String[] keys = key.split(",");
        for (String k : keys) {
            sysFileComDao.expire(k, expired);
        }
    }

    /**
     * <B>方法名称：</B>清除系统文件过期限制<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param keys 文件键值
     */
    public void clearExpired(String... keys) {
        if (keys == null || keys.length < 1) {
            return;
        }
        if (keys.length == 1) {
            keys = keys[0].split(",");
        }
        for (String key : keys) {
            sysFileComDao.clearExpired(key);
        }
    }    

    /**
     * <B>方法名称：</B>获取文件扩展名<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param filename 文件名
     * @return String 扩展名
     */
    public String getExtName(String filename) {
        if (StringUtils.isBlank(filename)) {
            return null;
        }
        int i = filename.indexOf(".");
        if (i >= 0) {
            return filename.substring(i + 1).trim().toLowerCase();
        }
        return null;
    }
	
}
