package com.kpf.async;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.kpf.domain.HT01Rms;
import com.kpf.domain.Rms;
import com.kpf.service.RmsService;

public class LogProcessor {
	
	@Autowired
	private RmsService rmsService;
	
	int val = 0;
	
	//주기별 실행 60000 = 1분
	@Scheduled(fixedRate = 10000)
	public void handle(){
//		System.out.println(val++);
		
		List<Rms> rmsArray = rmsService.getRmsData();
		
		for(int i=0; i<rmsArray.size(); i++) {
			String equip_code = rmsArray.get(i).getEquip_code();
			String value = rmsArray.get(i).getValue();
			
			System.out.println("equip_code = "+equip_code+"// value = "+value);
			
			System.out.println("JSON get 시작");
			JSONParser parser = new JSONParser();
			JSONObject valueObj;
			try {
				System.out.println("JSON get 진행중");
				valueObj = (JSONObject)parser.parse(value);
				
				System.out.println(valueObj.get("LOT"));
				System.out.println(valueObj.get("ZONE"));
			
				JSONObject zoneObj = (JSONObject)parser.parse(valueObj.get("ZONE").toString());
				System.out.println(zoneObj.get("QF1"));
				JSONObject qf1Obj = (JSONObject)parser.parse(zoneObj.get("QF1").toString());
				System.out.println(qf1Obj.get("TIME"));				
				System.out.println(qf1Obj.get("TEMP"));

				System.out.println(zoneObj.get("QF2"));
				JSONObject qf2Obj = (JSONObject)parser.parse(zoneObj.get("QF2").toString());
				System.out.println(qf2Obj.get("TIME"));				
				System.out.println(qf2Obj.get("TEMP"));

				HT01Rms ht01Rms = new HT01Rms();
				
				ht01Rms.setEquip_code(equip_code);
				ht01Rms.setLotno(valueObj.get("LOT").toString());
				ht01Rms.setQf1_temp(qf1Obj.get("TIME").toString());
				ht01Rms.setQf1_time(qf1Obj.get("TEMP").toString());
				ht01Rms.setQf2_temp(qf2Obj.get("TIME").toString());
				ht01Rms.setQf2_time(qf2Obj.get("TEMP").toString());
				
				
				
				rmsService.setHT01RmsInsert(ht01Rms);
				
				
/*				
				JSONObject timeObj = (JSONObject)parser.parse(qfObj.get("TIME").toString());
				JSONObject tempObj = (JSONObject)parser.parse(qfObj.get("TEMP").toString());
				
				System.out.println(timeObj.get("TIME"));
				System.out.println(tempObj.get("TEMP"));
*/				
			
			} catch (ParseException e) {
				e.printStackTrace();
			}

			
			
		}
		
		
	}
}
