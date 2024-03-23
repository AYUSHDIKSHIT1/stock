package com.stock.data.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
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
public class bankNifty {

	private static EmailService sender;
	static HashMap<String, Object> map = new HashMap<>();
	static HashMap<String, Object> premap = new HashMap<>();

	@Autowired
	public bankNifty(EmailService sender) {
		this.sender = sender;
		if (premap.isEmpty()) {
			premap.put("CEopenInterest", 1);
		}
		

	}
//	static	Double  bidprice;
	// static Double bidprice;

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


	static Long CEstrikePrice;
	static Long PEstrikePrice;

	static Long CEoI;
	static Long PEoI;
	static Long totalCEoI = 0L;
	static Long totalPEoI = 0L;
	static Double totalPcr;
	
	
	
	public static boolean isHammer(double open, double high, double low, double close) {
        double bodyHeight = Math.abs(open - close);
        double lowerShadow = Math.min(open, close) - low;
        double upperShadow = high - Math.max(open, close);

        // Define the criteria for a hammer
        boolean smallBody = bodyHeight < 0.1 * (high - low);
        boolean longLowerShadow = lowerShadow > 2 * bodyHeight;
        boolean littleUpperShadow = upperShadow < 0.1 * (high - low);
System.out.println(smallBody);

System.out.println(longLowerShadow);
System.out.println(littleUpperShadow);

        // Check if the candle meets the criteria for a hammer
        return smallBody && longLowerShadow && littleUpperShadow;
    }
	
	
	
	public static void hammer()
			throws FileNotFoundException, IOException, org.json.simple.parser.ParseException {
		Formatter format = new Formatter();
		Calendar cl = Calendar.getInstance();
		format = new Formatter();
		format.format("%tl:%tM", cl, cl);
		System.out.println(format);
				Object oo = null;
				Object Record = null;
				
				
				
				JSONParser parser = new JSONParser();
				Object Obj = parser.parse(new FileReader("C:\\Users\\ayush\\Downloads\\BankNifty.json"));
				JSONObject Json = (JSONObject) Obj;
			//	System.out.println(Json);
				// JSONArray item = (JSONArray) Json.get("item");
				JSONObject oobbjj = (JSONObject) Json.get("data");
					oo = parser.parse(oobbjj.get("candles").toString());
				
				 JSONArray records = (JSONArray) oo;
				 JSONArray candle = (JSONArray) records.get(0);
			

				Object o= candle.get(1);	
				Object l= candle.get(3);
				Object h= candle.get(2);
				Object c=candle.get(4);
				
				
				
				double open=19437.05;
				
						double low =19300.15;
				
						double high =19447.05;
				
						double close= 19447.55;
				
						
						boolean isHammer = isHammer(open, high, low, close);
System.out.println(isHammer);
				
				System.out.println(l);
				System.out.println(o);
				System.out.println(h);
				System.out.println(c);
				 
				 
				 
				 
//				 					Iterator<Array> recordIt = records.iterator();
//
//					while (recordIt.hasNext()) {
		

//		JSONArray CandlesArray = (JSONArray) Record;
//
//		// System.out.println(recordArray.get(0));
//
//		int count = 1;
	
//
//	while (recordIterator.hasNext()) {
//System.out.println(recordIt.next());
////
//	}
	}}
