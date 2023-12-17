package com.kpf.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kpf.domain.HT01Rms;
import com.kpf.domain.Rms;

@Repository
public class RmsDaoImpl implements RmsDao{

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Rms> getRmsData() {
		return sqlSession.selectList("rms.getRmsData");
	}

	@Override
	public void setHT01RmsInsert(HT01Rms ht01Rms) {
		sqlSession.insert("rms.setHT01RmsInsert",ht01Rms);
	}
}
