package com.kpf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kpf.dao.RmsDao;
import com.kpf.domain.HT01Rms;
import com.kpf.domain.Rms;

@Service
public class RmsServiceImpl implements RmsService{

	@Autowired
	private RmsDao rmsDao;

	@Override
	public List<Rms> getRmsData() {
		return rmsDao.getRmsData();
	}

	@Override
	public void setHT01RmsInsert(HT01Rms ht01Rms) {
		rmsDao.setHT01RmsInsert(ht01Rms);
	}
}
