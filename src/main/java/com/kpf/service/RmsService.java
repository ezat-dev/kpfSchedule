package com.kpf.service;

import java.util.List;
import java.util.Map;

import com.kpf.domain.HtDelay;
import com.kpf.domain.Rms;
import com.kpf.domain.RmsYn;

public interface RmsService {

	List<Rms> getRmsData();

	void setRmsDataInsert(Map<String, Object> rmsMap);

	int getRmsDataChkValue(Map<String, Object> rmsMap);

	List<RmsYn> getRmsYnList();

	void setRmsYnInsert(Map<String, Object> rmsObj);

	List<HtDelay> getHtDelayList();

	void setHtDelayInsert(HtDelay htDelay);

}
