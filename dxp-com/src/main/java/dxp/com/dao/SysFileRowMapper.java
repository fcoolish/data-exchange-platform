
package dxp.com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dxp.com.entity.SysFile;

/**
 * <B>系统名称：</B>通用系统功能<BR>
 * <B>模块名称：</B>数据访问通用功能<BR>
 * <B>中文类名：</B>系统文件行映射器<BR>
 * <B>概要说明：</B><BR>
 * @author dxp（Alienware）
 * @since 2019年5月23日
 */
public class SysFileRowMapper implements RowMapper<SysFile> {

    /**
     * <B>方法名称：</B>映射行数据<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param rs 结果集
     * @param row 行号
     * @return JSONObject 数据
     * @throws SQLException SQL异常错误
     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
     *      int)
     */
    public SysFile mapRow(ResultSet rs, int row) throws SQLException {
        SysFile ret = new SysFile();
        ret.setKey(rs.getString("KEY"));
        ret.setType(rs.getString("TYPE"));
        ret.setName(rs.getString("NAME"));
        ret.setExt(rs.getString("EXT"));
        ret.setBytes(rs.getLong("BYTES"));
        ret.setDataPath(rs.getString("DATA_PATH"));
        ret.setDataGroup(rs.getString("DATA_GROUP"));
        ret.setExpired(rs.getDate("EXPIRED"));
        ret.setDescInfo(rs.getString("DESC_INFO"));
        ret.setUpdateBy(rs.getString("UPDATE_BY"));
        ret.setUpdateTime(rs.getDate("UPDATE_TIME"));
        return ret;
    }
}