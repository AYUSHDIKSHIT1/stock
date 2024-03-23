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
public class BankNiftyService {
	private static EmailService sender;
	public static HashMap<String, Object> prebankmap = new HashMap<>();
	static HashMap<String, Object> bankMap = new HashMap<>();

	@Autowired
	public BankNiftyService(EmailService sender) {
		this.sender = sender;
	}

	static Long PEstrikePrice;
	static Double PEunderlyingValue;
	static Long underlyingValue;
	static Long bankPEopenInterest;
	static Long bankPEVolume;
	static Long CEstrikePrice;
	static Double CEunderlyingValue;
	static Long bankCEopenInterest;
	static Long bankCEVolume;
	static Long bankCEoI;
	static Long bankPEoI;
	static Long sp;
	static Long ceoi;
	static Long peoi;
	static Long totalCEoI = 0L;
	static Long totalPEoI = 0L;
	static Double totalPcr;
	static Long banktotalCEoI = 0L;
	static Long banktotalPEoI = 0L;
	static Double banktotalPcr;

	@SuppressWarnings("removal")
	public static HashMap<String, Object> importBankNiftyJson()
			throws FileNotFoundException, IOException, org.json.simple.parser.ParseException {
		if (prebankmap.isEmpty()) {
			int n = 200;
			for (int i = 1; i <= 6; i++) {
				prebankmap.put("bank" + n + "CEopenInterest", (Long) new Long(10L));
				prebankmap.put("bank" + n + "PEopenInterest", (Long) new Long(10));
				n = n - 100;
			}
		}
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
			Object Obj = parser.parse(new FileReader("C:\\Users\\ayush\\Downloads\\BankNiftyData.json"));
			JSONObject Json = (JSONObject) Obj;
			JSONObject records = (JSONObject) Json.get("records");
			Record = parser.parse(records.get("data").toString());
			JSONArray recordArray = (JSONArray) Record;
			int count = 1;
			Iterator<String> recordIterator = recordArray.iterator();
			while (recordIterator.hasNext()) {
				int x = 200;
				Object uJson = recordIterator.next();
				JSONObject edobj = new JSONObject();
				if (uJson.toString().contains("27-Mar-2024")) {
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
							System.out.println(e);
						}
						for (int i = 1; i <= 6; i++) {
							if (((CEstrikePrice - underlyingValue) <= 100 + x)
									&& ((CEstrikePrice - underlyingValue) >= x)) {
								try {								
									bankCEopenInterest = (Long) parser.parse(CEValues.get("openInterest").toString());
									bankCEVolume = (Long) parser.parse(CEValues.get("totalTradedVolume").toString());
									/************************ PRE MAP FOR AVOIDING DUPLICITY ************/
									if (bankMap.get("bank" + x + "CEopenInterest") != null) {
										prebankmap.put("bank" + x + "CEopenInterest",
												bankMap.get("bank" + x + "CEopenInterest"));
									} else {
										prebankmap.put("bank" + x + "CEopenInterest", 10L);
									}
									bankMap.put("bank" + x + "underlyingValue", underlyingValue);
									bankMap.put("bank" + x + "strikePrice", CEstrikePrice);
									bankMap.put("Time", format);
									bankMap.put("bank" + x + "CEopenInterest", bankCEopenInterest);
									bankMap.put("bank" + x + "CEVolume", bankCEVolume);
								} catch (Exception e) {
									System.out.println(e);
								}
								System.out.println(count + "*************CE******************");
								System.out.println("bankstrikePrice  " + CEstrikePrice);
								System.out.println("bankunderlyingValue  " + underlyingValue);
								System.out.println("bankCE DATA  ↓ ");
								System.out.println("bankopenInterest  " + bankCEopenInterest);
								System.out.println("bankVolume  " + bankCEVolume);
								System.out.println();
								System.out.println();
								count++;
							}
							x = x - 100;
						}
						x = 200;
					}
					if (OptionData.containsKey("PE")) {
						PEobj = parser.parse(OptionData.get("PE").toString());
						JSONObject PEValues = (JSONObject) PEobj;
						PEstrikePrice = (Long) parser.parse(PEValues.get("strikePrice").toString());
						try {
							PEunderlyingValue = (Double) parser.parse(PEValues.get("underlyingValue").toString());
							Double newDouble = new Double(PEunderlyingValue);
							underlyingValue = newDouble.longValue();

						} catch (Exception e) {
							System.out.println(e);
						}
						for (int i = 1; i <= 6; i++) {
							if (((PEstrikePrice - underlyingValue) <= 100 + x)
									&& ((PEstrikePrice - underlyingValue) >= x)) {
								try {
									System.out.println("**aaaaa*" + x);
									bankPEopenInterest = (Long) parser.parse(PEValues.get("openInterest").toString());
									bankPEVolume = (Long) parser.parse(PEValues.get("totalTradedVolume").toString());

									/************************ PRE MAP FOR AVOIDING DUPLICITY ************/
									if (bankMap.get("bank" + x + "PEopenInterest") != null) {
										prebankmap.put("bank" + x + "PEopenInterest",
												bankMap.get("bank" + x + "PEopenInterest"));
									} else {
										prebankmap.put("bank" + x + "PEopenInterest", 10L);
									}
									bankMap.put("bank" + x + "underlyingValue", underlyingValue);
									bankMap.put("bank" + x + "strikePrice", PEstrikePrice);
									bankMap.put("Time", format);
									bankMap.put("bank" + x + "PEopenInterest", bankPEopenInterest);
									bankMap.put("bank" + x + "PEVolume", bankPEVolume);

								} catch (Exception e) {
									System.out.println(e);
								}
								System.out.println(count + "*************PE******************");
								System.out.println("bankstrikePrice  " + PEstrikePrice);
								System.out.println("bankunderlyingValue  " + underlyingValue);
								System.out.println("bankPE DATA  ↓ ");
								System.out.println("bankopenInterest  " + bankPEopenInterest);
								System.out.println("bankVolume  " + bankPEVolume);
								System.out.println();
								System.out.println();
								count++;
							}
							x = x - 100;
						}
					}
				}
			}
			System.out.println("BANKMAP");
			System.out.println(bankMap);

			System.out.println("BANKPremap");
			System.out.println(prebankmap);

		} catch (Exception e) {
			System.out.println(e);
		}

		int x = 200;
		for (int i = 1; i <= 6; i++) {
			try {
				ceoi = (Long) bankMap.get("bank" + x + "CEopenInterest");
				peoi = (Long) bankMap.get("bank" + x + "PEopenInterest");
				sp = (long) bankMap.get("bank" + x + "strikePrice");
				Long diff = sp - underlyingValue;

				// condition to check the range
				if (ceoi != null && peoi != null && ((diff) <= 100 + x) && ((diff >= x))) {

					double CE = ((Long) bankMap.get("bank" + x + "CEopenInterest")).doubleValue();
					double PE = ((Long) bankMap.get("bank" + x + "PEopenInterest")).doubleValue();
					boolean mapMatch = prebankmap.get("bank" + x + "CEopenInterest").toString()
							.equals(bankMap.get("bank" + x + "CEopenInterest").toString());
					System.out.println();
					System.out.println();
					double pdf = CE / PE;
					double cdf = PE / CE;
					System.out.println("CALL BUY Deciding Factor " + cdf);
					System.out.println("PUT BUY Deciding Factor " + pdf);
					System.out.println(CE);
//					if ((diff <= 300) && (diff >= 200) && !mapMatch) {
//
//						if (cdf > 7) {
//							sender.sendEmail("ayushdikshit1@gmail.com", " strong call 3 up", cdf);
//						} else if (cdf > 6) {
//							sender.sendEmail("ayushdikshit1@gmail.com", " call 3 up", cdf);
//						}
//						if (pdf > 7) {
//							sender.sendEmail("ayushdikshit1@gmail.com", " strong put 3 up", pdf);
//						} else if ((pdf > 6)) {
//							sender.sendEmail("ayushdikshit1@gmail.com", " put 3 up", pdf);
//						}
//					}
//
//					if ((diff <= 200) && (diff >= 100) && !mapMatch) {
//						if (cdf > 6) {
//							sender.sendEmail("ayushdikshit1@gmail.com", " strong call 2 up", cdf);
//						} else if (cdf > 5) {
//							sender.sendEmail("ayushdikshit1@gmail.com", " call 2 up", cdf);
//						}
//						if (pdf > 6) {
//							sender.sendEmail("ayushdikshit1@gmail.com", " strong put 2 up", pdf);
//						} else if ((pdf > 5)) {
//							sender.sendEmail("ayushdikshit1@gmail.com", " put 2 up", pdf);
//						}
//					}
					
					if ((diff <= 10) && (diff >= 0) && !mapMatch) {
						if (cdf > 3.5) {
							sender.sendEmail("ayushdikshit1@gmail.com", " bank impossible strong call up", cdf);
						} else if (cdf > 2.8) {
							sender.sendEmail("ayushdikshit1@gmail.com", " bank very strong call up", cdf);
						} else if (cdf > 1.8) {
							sender.sendEmail("ayushdikshit1@gmail.com", " bank call up", cdf);
						}
						if (pdf > 3.5) {
							sender.sendEmail("ayushdikshit1@gmail.com", " bank impossible strong put up", pdf);
						} else if ((pdf > 2.8)) {
							sender.sendEmail("ayushdikshit1@gmail.com", " bank very strong put up", pdf);
						} else if (pdf > 1.8) {
							sender.sendEmail("ayushdikshit1@gmail.com", " bank put up", pdf);
						}
					}
					
					
					
					if ((diff <= 0) && (diff >= -10) && !mapMatch) {
						if (cdf > 3.5) {
							sender.sendEmail("ayushdikshit1@gmail.com", " bank impossible strong call down", cdf);
						} else if (cdf > 2.8) {
							sender.sendEmail("ayushdikshit1@gmail.com", " bank very strong call down", cdf);
						} else if (cdf > 1.8) {
							sender.sendEmail("ayushdikshit1@gmail.com", " bank call down", cdf);
						}
						if (pdf > 3.5) {
							sender.sendEmail("ayushdikshit1@gmail.com", " bank impossible strong put down", pdf);
						} else if ((pdf > 2.8)) {
							sender.sendEmail("ayushdikshit1@gmail.com", " bank very strong put down", pdf);
						} else if (pdf >1.8 ) {
							sender.sendEmail("ayushdikshit1@gmail.com", " bank put down", pdf);
						}
					}

					if ((diff <= 50) && (diff >= 0) && !mapMatch) {
						if (cdf > 4) {
							sender.sendEmail("ayushdikshit1@gmail.com", "bank impossible strong call up", cdf);
						} else if (cdf > 3) {
							sender.sendEmail("ayushdikshit1@gmail.com", "bank very strong call up", cdf);
						} else if (cdf > 2) {
							sender.sendEmail("ayushdikshit1@gmail.com", "bank call up", cdf);
						}
						if (pdf > 4) {
							sender.sendEmail("ayushdikshit1@gmail.com", "bank impossible strong put up", pdf);
						} else if ((pdf > 3)) {
							sender.sendEmail("ayushdikshit1@gmail.com", "bank very strong put up", pdf);
						} else if (pdf > 2) {
							sender.sendEmail("ayushdikshit1@gmail.com", "bank put up", pdf);
						}
					}
					
					if ((diff <= 0) && (diff >= -50) && !mapMatch) {
						if (cdf > 4) {
							sender.sendEmail("ayushdikshit1@gmail.com", "bank impossible strong call down", cdf);
						} else if (cdf > 3) {
							sender.sendEmail("ayushdikshit1@gmail.com", "bank very strong call down", cdf);
						} else if (cdf > 2) {
							sender.sendEmail("ayushdikshit1@gmail.com", "bank call down", cdf);
						}
						if (pdf > 4) {
							sender.sendEmail("ayushdikshit1@gmail.com", "bank impossible strong put down", pdf);
						} else if ((pdf > 3)) {
							sender.sendEmail("ayushdikshit1@gmail.com", "bank very strong put down", pdf);
						} else if (pdf > 2) {
							sender.sendEmail("ayushdikshit1@gmail.com", "bank put down", pdf);
						}
					}

//					if ((diff <= -200) && (diff >= -300) && !mapMatch) {
//						if (cdf > 7) {
//							sender.sendEmail("ayushdikshit1@gmail.com", " strong call 3 down", cdf);
//						} else if (cdf > 6) {
//							sender.sendEmail("ayushdikshit1@gmail.com", " call 3 down", cdf);
//						}
//						if (pdf > 7) {
//							sender.sendEmail("ayushdikshit1@gmail.com", " strong put 3 down", pdf);
//						} else if ((pdf > 6)) {
//							sender.sendEmail("ayushdikshit1@gmail.com", " put 3 down", pdf);
//						}
//					}
//
//					if ((diff <= -100) && (diff >= -200) && !mapMatch) {
//						if (cdf > 6) {
//							sender.sendEmail("ayushdikshit1@gmail.com", " strong call 2 down", cdf);
//						} else if (cdf >5) {
//							sender.sendEmail("ayushdikshit1@gmail.com", " call 2 down", cdf);
//						}
//						if (pdf > 6) {
//							sender.sendEmail("ayushdikshit1@gmail.com", " strong put 2 down", pdf);
//						} else if ((pdf > 5)) {
//							sender.sendEmail("ayushdikshit1@gmail.com", " put 2 down", pdf);
//						}
//					}

				
					// end of if
				}

			} catch (Exception e) {
				System.out.println(e);
			}
			x = x - 100;
		}
		System.out.println("**********Map BankNifty******");
		System.out.println(bankMap);
		return bankMap;
	}
}
