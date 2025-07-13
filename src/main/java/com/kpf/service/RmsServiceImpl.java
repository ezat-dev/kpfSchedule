package com.kpf.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kpf.dao.RmsDao;
import com.kpf.domain.HtDelay;
import com.kpf.domain.Rms;
import com.kpf.domain.RmsYn;

@Service
public class RmsServiceImpl implements RmsService{

	@Autowired
	private RmsDao rmsDao;

	@Override
	public List<Rms> getRmsData() {
		return rmsDao.getRmsData();
	}

	@Override
	public void setRmsDataInsert(Map<String, Object> rmsMap) {
		rmsDao.setRmsDataInsert(rmsMap);
	}

	@Override
	public int getRmsDataChkValue(Map<String, Object> rmsMap) {
		return rmsDao.getRmsDataChkValue(rmsMap);
	}

	@Override
	public List<RmsYn> getRmsYnList() {
		return rmsDao.getRmsYnList();
	}

	@Override
	public void setRmsYnInsert(Map<String, Object> rmsObj) {
		rmsDao.setRmsYnInsert(rmsObj);
	}

	@Override
	public List<HtDelay> getHtDelayList() {
		return rmsDao.getHtDelayList();
	}

	@Override
	public void setHtDelayInsert(HtDelay htDelay) {
		rmsDao.setHtDelayInsert(htDelay);
	}
}
