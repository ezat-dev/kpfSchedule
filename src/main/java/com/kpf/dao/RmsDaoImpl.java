package com.kpf.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kpf.domain.HtDelay;
import com.kpf.domain.Rms;
import com.kpf.domain.RmsYn;

@Repository
public class RmsDaoImpl implements RmsDao{

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Rms> getRmsData() {
		return sqlSession.selectList("rms.getRmsData");
	}

	@Override
	public void setRmsDataInsert(Map<String, Object> rmsMap) {
//		System.out.println("DAO : "+rmsMap.get("apy_uid")+"// >> "+rmsMap.get("apy_his_seq"));
		sqlSession.insert("rms.setRmsDataInsert",rmsMap);
	}

	@Override
	public int getRmsDataChkValue(Map<String, Object> rmsMap) {
		return sqlSession.selectOne("rms.getRmsDataChkValue",rmsMap);
	}

	@Override
	public List<RmsYn> getRmsYnList() {
		return sqlSession.selectList("rms.getRmsYnList");
	}

	@Override
	public void setRmsYnInsert(Map<String, Object> rmsObj) {
		sqlSession.update("rms.setRmsYnInsert", rmsObj);
	}

	@Override
	public List<HtDelay> getHtDelayList() {
		return sqlSession.selectList("rms.getHtDelayList");
	}

	@Override
	public void setHtDelayInsert(HtDelay htDelay) {
		sqlSession.insert("rms.setHtDelayInsert", htDelay);
	}
}
