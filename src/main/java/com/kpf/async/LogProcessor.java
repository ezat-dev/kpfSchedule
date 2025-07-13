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

public class LogProcessor {
	
	@Autowired
	private RmsService rmsService;
	
	int val = 0;
	
	//10초 주기로 동작
	@Scheduled(fixedRate = 10000)
	public void handle(){
//		System.out.println(val++);
		
		List<Rms> rmsArray = rmsService.getRmsData();
		
//		System.out.println("size : "+rmsArray.size());
		if(rmsArray.size() != 0) {
			
			for(int i=0; i<rmsArray.size(); i++) {
				Map<String, Object> rmsMap = new HashMap<String, Object>();
				RmsData rmsData = new RmsData();
//				System.out.println(rmsArray.get(i).getApy_uid());
				
				
				String factory_code = rmsArray.get(i).getFactory_code();
				String apy_uid = rmsArray.get(i).getApy_uid();
				int apy_his_seq = rmsArray.get(i).getApy_his_seq();
				String apy_type = rmsArray.get(i).getApy_type();
				String mst_lot_id = rmsArray.get(i).getMst_lot_id();
				int lot_htt_seq = rmsArray.get(i).getLot_htt_seq();
				String recipe_id = rmsArray.get(i).getRecipe_id();
				int recipe_ver = rmsArray.get(i).getRecipe_ver();
				String equip_code = rmsArray.get(i).getEquip_code();
				String create_user_id = rmsArray.get(i).getCreate_user_id();
				String create_time = rmsArray.get(i).getCreate_time();
				String change_type = rmsArray.get(i).getChange_type();
			
				rmsMap.put("factory_code", factory_code);
				rmsMap.put("apy_uid", apy_uid);
				rmsMap.put("apy_his_seq", apy_his_seq);
				rmsMap.put("apy_type", apy_type);
				rmsMap.put("mst_lot_id", mst_lot_id);
				rmsMap.put("lot_htt_seq", lot_htt_seq);
				rmsMap.put("recipe_id", recipe_id);
				rmsMap.put("recipe_ver", recipe_ver);
				rmsMap.put("equip_code", equip_code);
				rmsMap.put("create_user_id", create_user_id);
				rmsMap.put("create_time", create_time);
				rmsMap.put("change_type", change_type);
				
//				System.out.println("apy_uid : "+apy_uid+"// apy_his_seq : "+apy_his_seq);
				
				//apy_uid, apy_his_seq 기준 0일때만 insert
				int chk_value = rmsService.getRmsDataChkValue(rmsMap);
//				System.out.println("chk_value : "+chk_value);
				if(chk_value == 0) {
	/*				
					rmsData.setFactory_code(factory_code);
					rmsData.setApy_uid(apy_uid);
					rmsData.setApy_his_seq(apy_his_seq);
					rmsData.setApy_type(apy_type);
					rmsData.setMst_lot_id(mst_lot_id);
					rmsData.setLot_htt_seq(lot_htt_seq);
					rmsData.setRecipe_id(recipe_id);
					rmsData.setRecipe_ver(recipe_ver);
					rmsData.setEquip_code(equip_code);
					rmsData.setCreate_user_id(create_user_id);
					rmsData.setCreate_time(create_time);
					rmsData.setChange_type(change_type);
	*/
					String recipe_apy_data = rmsArray.get(i).getRecipe_apy_data();
					
//					System.out.println("equip_code = "+equip_code+"// recipe_apy_data = "+recipe_apy_data);
					
//					System.out.println("JSON get 시작");
					JSONParser parser = new JSONParser();
					JSONObject recipe_apy_dataObj;
					try {
//						System.out.println("JSON get 진행중");
						recipe_apy_dataObj = (JSONObject)parser.parse(recipe_apy_data);
						
	//					System.out.println(recipe_apy_dataObj.get("paramList"));
						String add_time = recipe_apy_dataObj.get("addTime").toString();
						JSONArray paramListArray = (JSONArray)recipe_apy_dataObj.get("paramList");
						
						StringBuffer str = new StringBuffer();
						str.append("{");
						for(int j=0; j<paramListArray.size(); j++) {
							
	//						System.out.println(paramListArray.get(0));
							JSONObject rowObj = (JSONObject)paramListArray.get(j);
//							System.out.println("i : "+j+"//itemCode : "+rowObj.get("itemCode").toString()+"//value : "+rowObj.get("value").toString());
//							System.out.println("j : "+j+"//itemCode : "+rowObj.get("itemCode")+"//value : "+rowObj.get("value"));
							
							String itemCode = rowObj.get("itemCode").toString();
							String value = rowObj.get("value").toString().replace("\"", "");
							
							if(j == paramListArray.size()-1) {
								str.append("\""+itemCode+"\":"+"\""+value+"\"");
							}else {
								str.append("\""+itemCode+"\":"+"\""+value+"\",");
							}
						}
						str.append("}");
						
//						System.out.println("=============================");
//						System.out.println(str.toString());
						
						JSONObject tempObj = (JSONObject)parser.parse(str.toString());
//						System.out.println("*****************************");
//						System.out.println(tempObj);
	/*					
						rmsData.setQf_mesh_speed_sp_set(tempObj.get("QF_MESH_SPEED_SP_SET").toString());
						rmsData.setQf_mesh_speed_time_set(tempObj.get("QF_MESH_SPEED_TIME_SET").toString());
						rmsData.setQf1_next_sp(tempObj.get("QF1_NEXT_TIME_SET").toString());
						rmsData.setQf1_next_time_set(tempObj.get("QF1_NEXT_TIME_SET").toString());
						rmsData.setQf2_next_sp(tempObj.get("QF2_NEXT_TIME_SET").toString());
						rmsData.setQf2_next_time_set(tempObj.get("QF2_NEXT_TIME_SET").toString());
						rmsData.setQf3_next_sp(tempObj.get("QF3_NEXT_TIME_SET").toString());
						rmsData.setQf3_next_time_set(tempObj.get("QF3_NEXT_TIME_SET").toString());
						rmsData.setQf4_next_sp(tempObj.get("QF4_NEXT_TIME_SET").toString());
						rmsData.setQf4_next_time_set(tempObj.get("QF4_NEXT_TIME_SET").toString());
						rmsData.setQf5_next_sp(tempObj.get("QF5_NEXT_TIME_SET").toString());
						rmsData.setQf5_next_time_set(tempObj.get("QF5_NEXT_TIME_SET").toString());
						rmsData.setAgitator_sp_set(tempObj.get("AGITATOR_SP_SET").toString());
						rmsData.setAgitator_time_set(tempObj.get("AGITATOR_TIME_SET").toString());
						rmsData.setQt_mesh_speed_sp_set(tempObj.get("QT_MESH_SPEED_SP_SET").toString());
						rmsData.setQt_mesh_speed_time_set(tempObj.get("QT_MESH_SPEED_TIME_SET").toString());
						rmsData.setTf_mesh_speed_sp_set(tempObj.get("TF_MESH_SPEED_SP_SET").toString());
						rmsData.setTf_mesh_speed_time_set(tempObj.get("TF_MESH_SPEED_TIME_SET").toString());
						rmsData.setTf1_next_sp(tempObj.get("TF1_NEXT_TIME_SET").toString());
						rmsData.setTf1_next_time_set(tempObj.get("TF1_NEXT_TIME_SET").toString());
						rmsData.setTf2_next_sp(tempObj.get("TF2_NEXT_TIME_SET").toString());
						rmsData.setTf2_next_time_set(tempObj.get("TF2_NEXT_TIME_SET").toString());
						rmsData.setTf3_next_sp(tempObj.get("TF3_NEXT_TIME_SET").toString());
						rmsData.setTf3_next_time_set(tempObj.get("TF3_NEXT_TIME_SET").toString());
						rmsData.setTf4_next_sp(tempObj.get("TF4_NEXT_TIME_SET").toString());
						rmsData.setTf4_next_time_set(tempObj.get("TF4_NEXT_TIME_SET").toString());
						rmsData.setCp1_next_sp(tempObj.get("CP1_NEXT_TIME_SET").toString());
						rmsData.setCp1_next_time_set(tempObj.get("CP1_NEXT_TIME_SET").toString());
	*/
						rmsMap.put("qf_mesh_speed_sp_set", tempObj.get("QF_MESH_SPEED_SP_SET"));
						rmsMap.put("qf_mesh_speed_time_set", tempObj.get("QF_MESH_SPEED_TIME_SET"));
						rmsMap.put("qf1_next_sp", tempObj.get("QF1_NEXT_SP"));
						rmsMap.put("qf1_next_time_set", tempObj.get("QF1_NEXT_TIME_SET"));
						rmsMap.put("qf2_next_sp", tempObj.get("QF2_NEXT_SP"));
						rmsMap.put("qf2_next_time_set", tempObj.get("QF2_NEXT_TIME_SET"));
						rmsMap.put("qf3_next_sp", tempObj.get("QF3_NEXT_SP"));
						rmsMap.put("qf3_next_time_set", tempObj.get("QF3_NEXT_TIME_SET"));
						rmsMap.put("qf4_next_sp", tempObj.get("QF4_NEXT_SP"));
						rmsMap.put("qf4_next_time_set", tempObj.get("QF4_NEXT_TIME_SET"));
						rmsMap.put("qf5_next_sp", tempObj.get("QF5_NEXT_SP"));
						rmsMap.put("qf5_next_time_set", tempObj.get("QF5_NEXT_TIME_SET"));
						rmsMap.put("agitator_sp_set", tempObj.get("AGITATOR_SP_SET"));
						rmsMap.put("agitator_time_set", tempObj.get("AGITATOR_TIME_SET"));
						rmsMap.put("qt_mesh_speed_sp_set", tempObj.get("QT_MESH_SPEED_SP_SET"));
						rmsMap.put("qt_mesh_speed_time_set", tempObj.get("QT_MESH_SPEED_TIME_SET"));
						rmsMap.put("tf_mesh_speed_sp_set", tempObj.get("TF_MESH_SPEED_SP_SET"));
						rmsMap.put("tf_mesh_speed_time_set", tempObj.get("TF_MESH_SPEED_TIME_SET"));
						rmsMap.put("tf1_next_sp", tempObj.get("TF1_NEXT_SP"));
						rmsMap.put("tf1_next_time_set", tempObj.get("TF1_NEXT_TIME_SET"));
						rmsMap.put("tf2_next_sp", tempObj.get("TF2_NEXT_SP"));
						rmsMap.put("tf2_next_time_set", tempObj.get("TF2_NEXT_TIME_SET"));
						rmsMap.put("tf3_next_sp", tempObj.get("TF3_NEXT_SP"));
						rmsMap.put("tf3_next_time_set", tempObj.get("TF3_NEXT_TIME_SET"));
						rmsMap.put("tf4_next_sp", tempObj.get("TF4_NEXT_SP"));
						rmsMap.put("tf4_next_time_set", tempObj.get("TF4_NEXT_TIME_SET"));
						rmsMap.put("tf5_next_sp", tempObj.get("TF5_NEXT_SP"));
						rmsMap.put("tf5_next_time_set", tempObj.get("TF5_NEXT_TIME_SET"));
						rmsMap.put("cp1_next_sp", tempObj.get("CP1_NEXT_SP"));
						rmsMap.put("cp1_next_time_set", tempObj.get("CP1_NEXT_TIME_SET"));
						rmsMap.put("cp2_next_sp", tempObj.get("CP2_NEXT_SP"));
						rmsMap.put("cp2_next_time_set", tempObj.get("CP2_NEXT_TIME_SET"));
						rmsMap.put("mat_std",tempObj.get("MAT_STD"));
						rmsMap.put("mat_code",tempObj.get("MAT_CODE"));
						rmsMap.put("mat_desc",tempObj.get("MAT_DESC"));
						rmsMap.put("inv_mat_code",tempObj.get("INV_MAT_CODE"));
						rmsMap.put("inv_mat_desc",tempObj.get("INV_MAT_DESC"));
						rmsMap.put("add_time",add_time);
						
/*						
						System.out.println("--------------------------------");
						System.out.println(URLEncoder.encode(tempObj.get("MAT_DESC").toString(),"utf-8"));
						System.out.println(URLEncoder.encode(tempObj.get("INV_MAT_CODE").toString(),"utf-8"));
						System.out.println(URLEncoder.encode(tempObj.get("EQUIP_DESC").toString(),"utf-8"));
						System.out.println(tempObj.get("EQUIP_DESC"));
						System.out.println("--------------------------------");
						String encode_mat_desc = URLEncoder.encode(tempObj.get("MAT_DESC").toString(),"utf-8");
						String encode_inv_mat_desc = URLEncoder.encode(tempObj.get("INV_MAT_DESC").toString(),"utf-8");
						String encode_equip_desc = URLEncoder.encode(tempObj.get("EQUIP_DESC").toString(),"utf-8");
						System.out.println(URLDecoder.decode(encode_mat_desc,"utf-8"));
						System.out.println(URLDecoder.decode(encode_inv_mat_desc,"utf-8"));
						System.out.println(URLDecoder.decode(encode_equip_desc,"euc-kr"));
						System.out.println("--------------------------------");
						String brokenKorean = tempObj.get("EQUIP_DESC").toString();
						
						byte[] brokenBytes = brokenKorean.getBytes("ISO-8859-1");
						byte[] brokenBytes2 = brokenKorean.getBytes("");
						
						String correctKorean = new String(brokenBytes, "UTF-8");
						String correctKorean2 = new String(brokenBytes2, "UTF-8");
						String correctKorean3 = new String(brokenBytes, "EUC-KR");
						String correctKorean2 = new String(brokenBytes2, "UTF-8");
						
						System.out.println("변환 설비명 : "+correctKorean);
						System.out.println("변환 설비명2 : "+correctKorean2);
*/						
//						System.out.println(rmsMap.get("qf_mesh_speed_sp_set")+", "+rmsMap.get("qf_mesh_speed_time_set"));
//						System.out.println(rmsMap.get("qf1_sp_set")+", "+rmsMap.get("qf1_time_set"));
//						System.out.println(rmsMap.toString());
						rmsService.setRmsDataInsert(rmsMap);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}
				
		//설비의 템퍼링 온도차이에 따른 지연시간 저장
		List<HtDelay> htDelayList = rmsService.getHtDelayList();
		
		if(htDelayList.size() != 0) {
			for(int i=0; i<htDelayList.size(); i++) {
				rmsService.setHtDelayInsert(htDelayList.get(i));
			}
		}
		
	}
}
