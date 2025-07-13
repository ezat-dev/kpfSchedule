package com.kpf.async;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.kpf.domain.HtDelay;
import com.kpf.domain.Rms;
import com.kpf.domain.RmsData;
import com.kpf.domain.RmsYn;
import com.kpf.service.RmsService;

public class RmsYnProcessor {
	
	@Autowired
	private RmsService rmsService;
	
	int val = 0;
	
	//10초 주기로 동작
	@Scheduled(fixedRate = 10000)
	public void handle(){
		List<RmsYn> rmsYnList = rmsService.getRmsYnList();
		//EQUIP_CODE, EQUIP_DESC, EQUIP_CMF_25
//		System.out.println("rms SIZE"+rmsYnList.size());
		if(rmsYnList.size() != 0) {
			for(int i=0; i<rmsYnList.size(); i++) {
				Map<String, Object> rmsObj = new HashMap<String, Object>();
				
				rmsObj.put("equip_code", rmsYnList.get(i).getEquip_code());
				rmsObj.put("equip_desc", rmsYnList.get(i).getEquip_desc());
				rmsObj.put("equip_cmf_25", rmsYnList.get(i).getEquip_cmf_25());

				rmsService.setRmsYnInsert(rmsObj);
			}
		}
		
	}
}
