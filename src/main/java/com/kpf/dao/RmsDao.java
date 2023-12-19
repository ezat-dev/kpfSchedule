package com.kpf.dao;

import java.util.List;
import java.util.Map;

import com.kpf.domain.Rms;

public interface RmsDao {

	List<Rms> getRmsData();
	
	void setRmsDataInsert(Map<String, Object> rmsMap);

	int getRmsDataChkValue(Map<String, Object> rmsMap);

}
