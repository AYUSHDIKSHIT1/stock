package com.stock.data.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RangeService {

	private static EmailService sender;
	
	@Autowired
public RangeService(EmailService sender) {
		this.sender = sender;
	this.CEopenInterest=-1L;
	this.PEopenInterest=-1L;
	}
	
	static HashMap<String, Object> rangeMap = new HashMap<>();
	static Long PEopenInterest;
	static Long PEVolume;
	static Long PEchangeinOpenInterest;
	static Double PEimpliedVolatility;
	static Long PEtotalBuyQuantity;
	static Long PEtotalSellQuantity;
	static Long PEbidQty;

	static Long CEopenInterest;
	static Long CEVolume;
	static Long CEchangeinOpenInterest;
	static Double CEimpliedVolatility;
	static Long CEtotalBuyQuantity;
	static Long CEtotalSellQuantity;
	static Long CEbidQty;
	
	
	static Double CEunderlyingValue;
	static Double PEunderlyingValue;
	static Long underlyingValue;

	static Long CEstrikePrice;
	static Long PEstrikePrice;

	static Long CEoI;
	static Long PEoI;

	static Long totalCEoI = 0L;
	static Long totalPEoI = 0L;
	static Double totalPcr;

	
	public static void getMarketRange()
			throws FileNotFoundException, IOException, org.json.simple.parser.ParseException {
		
		
		Formatter format = new Formatter();
		Calendar cl = Calendar.getInstance();
		format = new Formatter();
		format.format("%tl:%tM", cl, cl);
		System.out.println(format);

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("sheet");

		int rowno = 0;
		Object Record = null;
		Object CEobj = null;
		Object PEobj = null;
		Object dt = null;
		Object oo = null;

		ArrayList<JSONObject> al = new ArrayList();
		JSONParser parser = new JSONParser();
		try {

			Object Obj = parser.parse(new FileReader("C:\\Users\\ayush\\Downloads\\MarketData.json"));

			JSONObject Json = (JSONObject) Obj;
			// JSONArray item = (JSONArray) Json.get("item");
		JSONObject records = (JSONObject) Json.get("records");
			Record = parser.parse(records.get("data").toString());
			JSONArray recordArray = (JSONArray) Record;
			// System.out.println(recordArray.get(0));

			int count = 1;
			Iterator<String> recordIterator = recordArray.iterator();

			while (recordIterator.hasNext()) {

				Object uJson = recordIterator.next();
				JSONObject edobj = new JSONObject();
				if (uJson.toString().contains("13-Nov-2023")) {
					JSONObject OptionData = (JSONObject) uJson;
					if (OptionData.containsKey("CE")) {
						CEobj = parser.parse(OptionData.get("CE").toString());
						JSONObject CEValues = (JSONObject) CEobj;
						CEstrikePrice = (Long) parser.parse(CEValues.get("strikePrice").toString());
						try {
							CEunderlyingValue = (Double) parser.parse(CEValues.get("underlyingValue").toString());

							Double newDouble = new Double(CEunderlyingValue);
							underlyingValue = newDouble.longValue();

							

							if (((CEstrikePrice - underlyingValue) <= 200) && ((CEstrikePrice - underlyingValue) >=0 )) {
								try {

									if(CEopenInterest<(Long) parser.parse(CEValues.get("openInterest").toString())) {
									CEopenInterest = (Long) parser.parse(CEValues.get("openInterest").toString());
										

									CEVolume = (Long) parser.parse(CEValues.get("totalTradedVolume").toString());
									rangeMap.put("CEunderlyingValue", underlyingValue);
									rangeMap.put("CEstrikePrice", CEstrikePrice);
									rangeMap.put("CETime", format);
									rangeMap.put("CEopenInterest", CEopenInterest);
									rangeMap.put("CEVolume", CEVolume);
						
									count++;

									
									}								
								
								} catch (Exception e) {

								}
							}
						
							
							
						}
						 catch (Exception e) {

							}	}
						
					

					if (OptionData.containsKey("PE")) {
						PEobj = parser.parse(OptionData.get("PE").toString());
						JSONObject PEValues = (JSONObject) PEobj;
						PEstrikePrice = (Long) parser.parse(PEValues.get("strikePrice").toString());

						if (((PEstrikePrice - underlyingValue) <= 0) && ((PEstrikePrice - underlyingValue) >= -200)) {
							try {
								if(PEopenInterest<(Long) parser.parse(PEValues.get("openInterest").toString())) {									
								PEopenInterest = (Long) parser.parse(PEValues.get("openInterest").toString());
								rangeMap.put("PEopenInterest", PEopenInterest);
								rangeMap.put("PEVolume", PEVolume);
								rangeMap.put("PEunderlyingValue", underlyingValue);
								rangeMap.put("PEstrikePrice", PEstrikePrice);
								rangeMap.put("PETime", format);							
								PEVolume = (Long) parser.parse(PEValues.get("totalTradedVolume").toString());
								}
							
							} catch (Exception e) {
							
							}
						}
				}	
	}
		}
			
			System.out.println(rangeMap);
		}
		catch (Exception e) {

		}
	
	
	
	}
}
