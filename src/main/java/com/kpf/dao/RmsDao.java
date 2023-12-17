package com.kpf.dao;

import java.util.List;

import com.kpf.domain.HT01Rms;
import com.kpf.domain.Rms;

public interface RmsDao {

	List<Rms> getRmsData();

	void setHT01RmsInsert(HT01Rms ht01Rms);

}
