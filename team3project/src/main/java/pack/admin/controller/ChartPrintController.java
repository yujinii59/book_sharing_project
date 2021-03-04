package pack.admin.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pack.admin.model.AdminInter;
import pack.model.ChartPrintDto;

@Controller
public class ChartPrintController {

	@Autowired
	private AdminInter adminInter;

	@RequestMapping(value = "monthprofitchart", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<JSONObject> abc() {
		// 리스트 형태를 json 형태로 만들어서 리턴
		JSONObject dataList = new JSONObject();

		ResponseEntity<JSONObject> data = null;
		List<ChartPrintDto> profitList = adminInter.getChartData();

		// 컬럼객체
		JSONObject col1 = new JSONObject();
		JSONObject col2 = new JSONObject();
		JSONObject col3 = new JSONObject();
		JSONObject col4 = new JSONObject();
		JSONArray title = new JSONArray();

		col1.put("label", "해당월");
		col1.put("type", "string");
		col2.put("label", "새책판매액");
		col2.put("type", "number");
		col3.put("label", "중고책판매액");
		col3.put("type", "number");
		col4.put("label", "순수익");
		col4.put("type", "number");
		
		title.add(col1);
		title.add(col2);
		title.add(col3);
		title.add(col4);
		
		dataList.put("cols", title);
		
		JSONArray body = new JSONArray();
				
		for (ChartPrintDto cd : profitList) {
			JSONObject month = new JSONObject();
			month.put("v", cd.getCmonth());
			
			JSONObject nbprofit = new JSONObject();
			nbprofit.put("v", cd.getNbprofit());
			
			JSONObject obprofit = new JSONObject();
			obprofit.put("v", cd.getObprofit());
			
			JSONObject rprofit = new JSONObject();
			rprofit.put("v", cd.getRprofit());
			
			JSONArray row = new JSONArray();
			row.add(month);
			row.add(nbprofit);
			row.add(obprofit);
			row.add(rprofit);
			
			JSONObject c = new JSONObject();
			c.put("c", row);
			
			body.add(c);
		}
		
		dataList.put("rows", body);
		try{
			data =new ResponseEntity<JSONObject>(dataList, HttpStatus.OK);
		}catch (Exception e) {
			System.out.println(" 에러            -- ");
			data =new ResponseEntity<JSONObject>(HttpStatus.BAD_REQUEST);
		}
		return data;
	}

	
}
