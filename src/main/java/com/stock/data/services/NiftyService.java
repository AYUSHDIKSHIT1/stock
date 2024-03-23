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
public class NiftyService {

	private static EmailService sender;
	static HashMap<String, Object> niftymap = new HashMap<>();

	public static HashMap<String, Object> niftypremap = new HashMap<>();

	@Autowired
	public NiftyService(EmailService sender) {
		this.sender = sender;
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


	static Double CEunderlyingValue;
	static Double PEunderlyingValue;
	static Long underlyingValue;

	static Long CEstrikePrice;
	static Long PEstrikePrice;

	static Long ceoi;
	static Long peoi;

	static Long sp;

	static Long totalCEoI = 0L;
	static Long totalPEoI = 0L;
	static Double totalPcr;

	@SuppressWarnings("removal")
	public static HashMap<String, Object> importNiftyJson()
			throws FileNotFoundException, IOException, org.json.simple.parser.ParseException {

		if (niftypremap.isEmpty()) {
			niftypremap.put("CEopenInterest", 10);
		}
		Formatter format = new Formatter();
		Calendar cl = Calendar.getInstance();
		format = new Formatter();
		format.format("%tl:%tM", cl, cl);
		System.out.println(format);

//		  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy ");  
//		   LocalDateTime now = LocalDateTime.now();  
//		   System.out.println(dtf.format(now));  

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("sheet");

		int rowno = 0;
		// TODO Auto-generated method stub
		Object Record = null;
		Object CEobj = null;
		Object PEobj = null;
		Object dt = null;
		// Date ed = null;
		Object oo = null;

		
//		  SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
//		  SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
//		  Date date = format1.parse("12/10/2012");
//		  System.out.println(format1.format(date));
//
//		
		// *****************************************/
		// Getting details of the JSON Object
		// Getting details of JSONArray
		// *****************************************/

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
				int x = 100;
				Object uJson = recordIterator.next();
				JSONObject edobj = new JSONObject();
				if (uJson.toString().contains("28-Mar-2024")) {
					JSONObject OptionData = (JSONObject) uJson;
					if (OptionData.containsKey("CE")) {
						CEobj = parser.parse(OptionData.get("CE").toString());
						JSONObject CEValues = (JSONObject) CEobj;
						CEstrikePrice = (Long) parser.parse(CEValues.get("strikePrice").toString());
						try {
							CEunderlyingValue = (Double) parser.parse(CEValues.get("underlyingValue").toString());

							Double newDouble = new Double(CEunderlyingValue);
							underlyingValue = newDouble.longValue();

						} catch (Exception e) {

						}

						for (int i = 1; i <= 6; i++) {
							if (((CEstrikePrice - underlyingValue) <= 50 + x)
									&& ((CEstrikePrice - underlyingValue) >= x)) {
								try {
									if (niftymap.get("CEopenInterest"+x) == null) {
										niftypremap.put("CEopenInterest"+x, 10);
									} else {
										niftypremap.put("CEopenInterest"+x, niftymap.get("CEopenInterest"+x));
									}
									CEopenInterest = (Long) parser.parse(CEValues.get("openInterest").toString());
									CEchangeinOpenInterest = (Long) parser
											.parse(CEValues.get("changeinOpenInterest").toString());

									CEVolume = (Long) parser.parse(CEValues.get("totalTradedVolume").toString());
									CEimpliedVolatility = (Double) parser
											.parse(CEValues.get("impliedVolatility").toString());
									CEtotalBuyQuantity = (Long) parser
											.parse(CEValues.get("totalBuyQuantity").toString());
									CEtotalSellQuantity = (Long) parser
											.parse(CEValues.get("totalSellQuantity").toString());
									CEbidQty = (Long) parser.parse(CEValues.get("bidQty").toString());
									
									System.out.println(niftypremap.get("CEopenInterest"+x));
									System.out.println(niftymap.get("CEopenInterest"+x));
									niftymap.put("underlyingValue" + x, underlyingValue);
									niftymap.put("strikePrice" + x, CEstrikePrice);
									niftymap.put("Time" + x, format);
									niftymap.put("CEopenInterest" + x, CEopenInterest);
									niftymap.put("CEVolume" + x, CEVolume);
									niftymap.put("CEimpliedVolatility" + x, CEimpliedVolatility);
									niftymap.put("CEchangeinOpenInterest" + x, CEchangeinOpenInterest);
									System.out.println(niftymap);
								} catch (Exception e) {

								}
								System.out.println(count + "*************CE******************");
								System.out.println("strikePrice" + x + CEstrikePrice);
								System.out.println("underlyingValue  " + x + underlyingValue);
								System.out.println("CE DATA  ↓ ");
								System.out.println("openInterest  " + x + CEopenInterest);
								System.out.println("Volume  " + x + CEVolume);
								System.out.println("IV  " + x + CEimpliedVolatility);
								System.out.println("changeinOpenInterest  " + x + CEchangeinOpenInterest);
								System.out.println();
								System.out.println();

								count++;
							}
							x = x - 50;
						}

					}

					if (OptionData.containsKey("PE")) {
						PEobj = parser.parse(OptionData.get("PE").toString());
						JSONObject PEValues = (JSONObject) PEobj;
						PEstrikePrice = (Long) parser.parse(PEValues.get("strikePrice").toString());
						x = 100;
						try {
							PEunderlyingValue = (Double) parser.parse(PEValues.get("underlyingValue").toString());
							Double newDouble = new Double(PEunderlyingValue);
							underlyingValue = newDouble.longValue();

						} catch (Exception e) {
							System.out.println(e);
						}
						for (int i = 1; i <= 6; i++) {
							if (((PEstrikePrice - underlyingValue) <= 50 + x)
									&& ((PEstrikePrice - underlyingValue) >= x)) {
								try {

									PEchangeinOpenInterest = (Long) parser
											.parse(PEValues.get("changeinOpenInterest").toString());
									PEopenInterest = (Long) parser.parse(PEValues.get("openInterest").toString());
									PEVolume = (Long) parser.parse(PEValues.get("totalTradedVolume").toString());
									PEimpliedVolatility = (Double) parser
											.parse(PEValues.get("impliedVolatility").toString());
									PEtotalBuyQuantity = (Long) parser
											.parse(PEValues.get("totalBuyQuantity").toString());
									PEtotalSellQuantity = (Long) parser
											.parse(PEValues.get("totalSellQuantity").toString());
									PEbidQty = (Long) parser.parse(PEValues.get("bidQty").toString());
									niftymap.put("PEchangeinOpenInterest" + x, PEchangeinOpenInterest);
									niftymap.put("PEopenInterest" + x, PEopenInterest);
									niftymap.put("PEVolume" + x, PEVolume);
									niftymap.put("PEimpliedVolatility" + x, PEimpliedVolatility);
									niftymap.put("PEStrikePrice" + x, PEstrikePrice);
									niftypremap.put("PEopenInterest" + x, niftymap.get("PEopenInterest"));

								} catch (Exception e) {

								}
								System.out.println(count + "*************PE******************");

								System.out.println("strikePrice" + x + PEstrikePrice);
								System.out.println("underlyingValue" + x + underlyingValue);
								System.out.println("PE DATA  ↓ ");
								System.out.println("openInterest" + x + PEopenInterest);
								System.out.println("IV" + x + PEimpliedVolatility);

								System.out.println("changeinOpenInterest" + x + PEchangeinOpenInterest);
								System.out.println();
								System.out.println();
								count++;
							}
							x = x - 50;
						}
					}

				}
			}
		} catch (Exception e) {

		}

		int x = 100;
		for (int i = 1; i <= 6; i++) {
			try {
				ceoi = (Long) niftymap.get("CEopenInterest" + x);
				peoi = (Long) niftymap.get("PEopenInterest" + x);
				sp = (long) niftymap.get("strikePrice" + x);
				Long diff = sp - underlyingValue;

				// condition to check the range
				if (ceoi != null && peoi != null && ((diff) <= 50 + x) && ((diff >= x))) {

					double CE = ((Long) niftymap.get("CEopenInterest" + x)).doubleValue();
					double PE = ((Long) niftymap.get("PEopenInterest" + x)).doubleValue();
					boolean mapMatch = niftypremap.get("CEopenInterest" + x).toString()
							.equals(niftymap.get("CEopenInterest"+x).toString());
					System.out.println();
					System.out.println();
					double pdf = CE / PE;
					double cdf = PE / CE;
					System.out.println("CALL BUY Deciding Factor " + cdf);
					System.out.println("PUT BUY Deciding Factor " + pdf);
					System.out.println(CE);

					
					
					if ((diff <= 13) && (diff >= 0) && !mapMatch) {
						if (cdf > 3.5) {
							sender.sendEmail("ayushdikshit1@gmail.com", "nifty impossible strong call up", cdf);
						} else if (cdf > 2.8) {
							sender.sendEmail("ayushdikshit1@gmail.com", "nifty very strong call up", cdf);
						} else if (cdf > 1.8) {
							sender.sendEmail("ayushdikshit1@gmail.com", "nifty call up", cdf);
						}
						if (pdf > 3.5) {
							sender.sendEmail("ayushdikshit1@gmail.com", "nifty impossible strong put up", pdf);
						} else if ((pdf > 2.8)) {
							sender.sendEmail("ayushdikshit1@gmail.com", "nifty very strong put up", pdf);
						} else if (pdf > 1.8) {
							sender.sendEmail("ayushdikshit1@gmail.com", "nifty put up", pdf);
						}
					}
					

					
					if ((diff <= 0) && (diff >= -13) && !mapMatch) {
						if (cdf > 3.5) {
							sender.sendEmail("ayushdikshit1@gmail.com", " nifty impossible strong call down", cdf);
						} else if (cdf > 2.8) {
							sender.sendEmail("ayushdikshit1@gmail.com", " nifty very strong call down", cdf);
						} else if (cdf > 1.8) {
							sender.sendEmail("ayushdikshit1@gmail.com", " nifty call down", cdf);
						}
						if (pdf > 3.5) {
							sender.sendEmail("ayushdikshit1@gmail.com", " nifty impossible strong put down", pdf);
						} else if ((pdf > 2.8)) {
							sender.sendEmail("ayushdikshit1@gmail.com", " nifty very strong put down", pdf);
						} else if (pdf >1.8 ) {
							sender.sendEmail("ayushdikshit1@gmail.com", " nifty put down", pdf);
						}
					}

			
					
					
						if ((diff <= 30) && (diff >= 0) && !mapMatch) {
							if (cdf > 4.5) {
								sender.sendEmail("ayushdikshit1@gmail.com", " nifty impossible strong call up", cdf);
							} else if (cdf > 3.5) {
								sender.sendEmail("ayushdikshit1@gmail.com", " nifty very strong call up", cdf);
							} else if (cdf > 2.5) {
								sender.sendEmail("ayushdikshit1@gmail.com", " nifty call up", cdf);
							}
							if (pdf > 4.5) {
								sender.sendEmail("ayushdikshit1@gmail.com", " nifty impossible strong put up", pdf);
							} else if ((pdf > 3.5)) {
								sender.sendEmail("ayushdikshit1@gmail.com", " nifty very strong put up", pdf);
							} else if (pdf > 2.5) {
								sender.sendEmail("ayushdikshit1@gmail.com", " nifty put up", pdf);
							}
						}
						
											
						
						if ((diff <= 0) && (diff >= -30) && !mapMatch) {
							if (cdf > 4.5) {
								sender.sendEmail("ayushdikshit1@gmail.com", " nifty impossible strong call down", cdf);
							} else if (cdf > 3.5) {
								sender.sendEmail("ayushdikshit1@gmail.com", " nifty very strong call down", cdf);
							} else if (cdf > 2.5) {
								sender.sendEmail("ayushdikshit1@gmail.com", " nifty call down", cdf);
							}
							if (pdf > 4.5) {
								sender.sendEmail("ayushdikshit1@gmail.com", " nifty impossible strong put down", pdf);
							} else if ((pdf > 3.5)) {
								sender.sendEmail("ayushdikshit1@gmail.com", " nifty very strong put down", pdf);
							} else if (pdf >2.5 ) {
								sender.sendEmail("ayushdikshit1@gmail.com", " nifty put down", pdf);
							}
						}
						
						
							}
				
			} catch (Exception e) {
				System.out.println(e);
			}
			x=x-50;
		}


		System.out.println("**********Map nifty******");

		System.out.println(niftymap);
		return niftymap;

	}

}

//Path pathh = Paths.get("C:\\Users\\ayush\\Downloads\\print.txt");
//Files.writeString(pathh, "openInterest", StandardCharsets.UTF_8);
//FileOutputStream file = new FileOutputStream("C:\\Users\\ayush\\Downloads\\ApiReport.xlsx");
//workbook.write(file);
//file.close();
//System.out.println("Data Copied to Excel");
