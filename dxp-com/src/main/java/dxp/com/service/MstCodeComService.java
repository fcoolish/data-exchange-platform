package dxp.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dxp.com.dao.MstCodeComDao;

import com.alibaba.fastjson.JSONObject;

@Service
public class MstCodeComService {

	private MstCodeComDao mstCodeComDao;

	public MstCodeComDao getMstCodeComDao() {
		return mstCodeComDao;
	}
	@Autowired
	public void setMstCodeComDao(MstCodeComDao mstCodeComDao) {
		this.mstCodeComDao = mstCodeComDao;
	}
	
	public List<JSONObject> getTypeList(String type){
		return this.mstCodeComDao.getTypeList(type);
	}
	
}
