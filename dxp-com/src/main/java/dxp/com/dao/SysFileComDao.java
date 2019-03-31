package dxp.com.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import dxp.com.entity.SysFile;

import com.alibaba.fastjson.JSONObject;

@Repository
public class SysFileComDao extends BaseJdbcDao {

    /** 系统文件行映射器 */
    private static final SysFileRowMapper SYS_FILE_ROW_MAPPER = new SysFileRowMapper();
    
    /**
     * <B>方法名称：</B>插入系统文件<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param data 系统文件
     * @return int 插入数量
     */
    public int insert(SysFile data) {
        if (data == null) {
            return 0;
        }
        String sql = "INSERT INTO SYS_FILE(KEY, TYPE, NAME, EXT, BYTES, DATA_PATH, DATA_GROUP, EXPIRED, DESC_INFO, UPDATE_BY, UPDATE_TIME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSTIMESTAMP)";
        Object[] args = new Object[10];
        args[0] = data.getKey();
        args[1] = data.getType();
        args[2] = data.getName();
        args[3] = data.getExt();
        args[4] = data.getBytes();
        args[5] = data.getDataPath();
        args[6] = data.getDataGroup();
        args[7] = data.getExpired();
        args[8] = data.getDescInfo();
        args[9] = data.getUpdateBy();
        return super.getJdbcTemplate().update(sql.toString(), args);
    }

    /**
     * <B>方法名称：</B>清除系统文件过期限制<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param key 文件键值
     * @return int 更新数量
     */
    public int clearExpired(String key) {
        if (StringUtils.isBlank(key)) {
            return 0;
        }
        return super.getJdbcTemplate().update("UPDATE SYS_FILE SET EXPIRED = NULL WHERE KEY = ?", key);
    }
    
    /**
     * <B>方法名称：</B>更新系统文件过期时间<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param key 文件键值
     * @param expired 过期时间（null为当前时间）
     * @return int 更新数量
     */
    public int expire(String key, Date expired) {
        if (StringUtils.isBlank(key)) {
            return 0;
        }
        if (expired == null) {
            return super.getJdbcTemplate().update("UPDATE SYS_FILE SET EXPIRED = SYSTIMESTAMP WHERE KEY = ?", key);
        }
        return super.getJdbcTemplate().update("UPDATE SYS_FILE SET EXPIRED = ? WHERE KEY = ?", expired, key);
    }   
   
    /**
     * <B>方法名称：</B>删除系统文件<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param key 文件键值
     * @return int 删除数量
     */
    public int delete(String key) {
        if (StringUtils.isBlank(key)) {
            return 0;
        }
        return super.getJdbcTemplate().update("DELETE FROM SYS_FILE WHERE KEY = ?", key);
    }
    
    /**
     * <B>方法名称：</B>获取系统文件<BR>
     * <B>概要说明：</B><BR>
     * @param key 文件键值
     * @return SysFile 系统文件
     */
    public SysFile get(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        List<SysFile> dataList = getList(key.split(","));
        if (dataList == null || dataList.size() < 1) {
            return null;
        }
        return dataList.get(0);
    }

    /**
     * <B>方法名称：</B>获取系统文件列表<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param keys 文件键值集合
     * @return List<SysFile> 系统文件列表
     */
    public List<SysFile> getList(String[] keys) {
        List<Object> args = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT KEY, TYPE, NAME, EXT, BYTES, DATA_PATH, DATA_GROUP, EXPIRED, DESC_INFO, UPDATE_BY, UPDATE_TIME ");
        sql.append(" FROM SYS_FILE WHERE KEY IN ");
        super.appendSqlIn(sql, args, keys);
        sql.append(" ORDER BY TYPE, NAME");
        return super.getJdbcTemplate().query(sql.toString(), SYS_FILE_ROW_MAPPER, args.toArray());
    }
    
    
    
    
}
