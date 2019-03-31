
package dxp.com.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;



/**
 * <B>系统名称：</B>治超信息系统<BR>
 * <B>模块名称：</B>数据实体<BR>
 * <B>中文类名：</B>系统文件<BR>
 * <B>概要说明：</B><BR>
 */
public class SysFile implements java.io.Serializable {

    /** 默认序列版本标识 */
    private static final long serialVersionUID = 1L;

    /** 文件键值 */
    private String key;

    /** 文件分类 */
    private String type;

    /** 文件名称 */
    private String name;

    /** 文件扩展名 */
    private String ext;

    /** 文件大小 */
    private long bytes;

    /** 文件数据路径 */
    private String dataPath;
    
    /** 文件数据组 */
    private String dataGroup;
    
    /** 过期时间 */
    private Date expired;
    
    /** 备注 */
    private String descInfo;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;
    
    /**
     * <B>取得：</B>文件键值<BR>
     * 
     * @return String 文件键值
     */
    public String getKey() {
        return key;
    }

    /**
     * <B>设定：</B>文件键值<BR>
     * 
     * @param key 文件键值
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * <B>取得：</B>文件分类【GROUP】<BR>
     * 
     * @return String 文件分类【GROUP】
     */
    public String getType() {
        return type;
    }

    /**
     * <B>设定：</B>文件分类<BR>
     * 
     * @param type 文件分类
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * <B>取得：</B>文件名称<BR>
     * 
     * @return String 文件名称
     */
    public String getName() {
        return name;
    }

    /**
     * <B>设定：</B>文件名称<BR>
     * 
     * @param name 文件名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <B>取得：</B>文件扩展名<BR>
     * 
     * @return String 文件扩展名
     */
    public String getExt() {
        return ext;
    }

    /**
     * <B>设定：</B>文件扩展名<BR>
     * 
     * @param ext 文件扩展名
     */
    public void setExt(String ext) {
        this.ext = ext;
    }


	/**
     * <B>取得：</B>文件大小<BR>
     * 
     * @return long 文件大小
     */
    public long getBytes() {
        return bytes;
    }

    /**
     * <B>设定：</B>文件大小<BR>
     * 
     * @param bytes 文件大小
     */
    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

	/**
     * <B>取得：</B>文件路径<BR>
     * 
     * @return dataPath 文件路径
     */
    public String getDataPath() {
		return dataPath;
	}

    /**
     * <B>设定：</B>文件路径<BR>
     * 
     * @param dataPath 文件路径
     */
	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}
	
	/**
     * <B>取得：</B>文件组名<BR>
     * 
     * @return dataGroup 文件组名
     */
	public String getDataGroup() {
		return dataGroup;
	}
	
    /**
     * <B>设定：</B>文件组名<BR>
     * 
     * @param dataGroup 文件组名
     */
	public void setDataGroup(String dataGroup) {
		this.dataGroup = dataGroup;
	}

	/**
     * <B>取得：</B>过期时间<BR>
     * 
     * @return Date 过期时间
     */
    public Date getExpired() {
        return expired;
    }

    /**
     * <B>设定：</B>过期时间<BR>
     * 
     * @param expired 过期时间
     */
    public void setExpired(Date expired) {
        this.expired = expired;
    }

    /**
     * <B>取得：</B>更新人<BR>
     * 
     * @return String 更新人
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * <B>设定：</B>更新人<BR>
     * 
     * @param updateBy 更新人
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * <B>取得：</B>更新时间<BR>
     * 
     * @return Date 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * <B>设定：</B>更新时间<BR>
     * 
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    /**
     * <B>取得：</B>descInfo<BR>
     * @return String
     */
    public String getDescInfo() {
        return descInfo;
    }

    /**
     * <B>设定：</B>descInfo<BR>
     * @param descInfo 备注
     */
    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo;
    }


}
