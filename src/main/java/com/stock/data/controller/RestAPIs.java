package com.stock.data.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.stock.data.model.Banknifty;
import com.stock.data.model.MarketData;
import com.stock.data.model.downerDown;
import com.stock.data.model.up;
import com.stock.data.model.upperUp;
import com.stock.data.repository.UpRepository;
import com.stock.data.repository.UpperUpRepository;
import com.stock.data.repository.DownerDownRepository;
import com.stock.data.repository.MarketRepository;
import com.stock.data.services.BankNiftyService;
import com.stock.data.services.EmailService;
import com.stock.data.services.FinniftyService;
import com.stock.data.services.NiftyService;
import com.stock.data.services.RangeService;
import com.stock.data.services.StockOIService;
import com.stock.data.services.bankNifty;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.simple.parser.ParseException;
import java.io.FileWriter;
import java.util.concurrent.Future;
import org.asynchttpclient.Dsl;

import java.io.File;
import java.io.FileInputStream;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class RestAPIs {

	static HashMap<String, Object> niftyMap = new HashMap<>();
	static HashMap<String, Object> niftyPremap = new HashMap<>();

	@Autowired
	private EmailService emailsender;

	@Autowired
	private NiftyService niftyservice;

	@Autowired
	private FinniftyService fnservice;

	@Autowired
	private BankNiftyService bankservice;

	@Autowired
	private StockOIService stockservice;

	@Autowired
	private RangeService range;

	@Autowired
	private bankNifty bnService;
//
//	@Autowired
//	AuthenticationManager authenticationManager;

	@Autowired
	MarketRepository marketRepository;

	@Autowired
	UpRepository upRepository;

	@Autowired
	UpperUpRepository upperUpRepository;

	@Autowired
	DownerDownRepository downerDownRepository;

//	@Autowired
//	PasswordEncoder encoder;


	@GetMapping("/home")
	public String home() {
		 return "Done it!";
	}
	
	
	
	
	@PostMapping("/options")
	public void optionData(@RequestBody markt mk) {
		try {
			HttpClient httpClient = HttpClients.createDefault();
			String apiUrl = "https://www.nseindia.com/api/option-chain-indices?symbol=NIFTY";
			HttpGet httpGet = new HttpGet(apiUrl);
			httpGet.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36");
			httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
			httpGet.setHeader("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8");
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			String result = EntityUtils.toString(httpEntity);
			System.out.println(result);
			System.out.println(response);
			Path path = Paths.get("C:\\Users\\ayush\\Downloads\\MarketData.json");
			Files.writeString(path, result, StandardCharsets.UTF_8);
			niftyservice.importNiftyJson();
			Thread.sleep(1000);

			try {
				HttpClient http = HttpClients.createDefault();
				String Url = "https://www.nseindia.com/api/option-chain-indices?symbol=FINNIFTY";
				HttpGet htp = new HttpGet(Url);
				htp.setHeader("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36");
				htp.setHeader("Accept-Encoding", "gzip, deflate, br");
				htp.setHeader("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8");
				HttpResponse resp = http.execute(htp);
				HttpEntity htpEntity = resp.getEntity();
				String ress = EntityUtils.toString(htpEntity);
				System.out.println(ress);
				System.out.println(resp);
				Path pathh = Paths.get("C:\\Users\\ayush\\Downloads\\FinNiftyData.json");
				Files.writeString(pathh, ress, StandardCharsets.UTF_8);
				fnservice.importFinNiftyJson();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e);
			}
			Thread.sleep(1000);

			try {
				HttpClient http = HttpClients.createDefault();
				String bankUrl = "https://www.nseindia.com/api/option-chain-indices?symbol=BANKNIFTY";
				HttpGet bankhttp = new HttpGet(bankUrl);
				bankhttp.setHeader("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36");
				bankhttp.setHeader("Accept-Encoding", "gzip, deflate, br");
				bankhttp.setHeader("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8");
				HttpResponse bankresponse = http.execute(bankhttp);
				HttpEntity bankhttpEntity = bankresponse.getEntity();
				String bankresult = EntityUtils.toString(bankhttpEntity);
				System.out.println(bankresult);
				System.out.println(bankresponse);
				Path pathh = Paths.get("C:\\Users\\ayush\\Downloads\\BankNiftyData.json");
				Files.writeString(pathh, bankresult, StandardCharsets.UTF_8);
				bankservice.importBankNiftyJson();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e);
			}

			niftyMap.putAll(niftyservice.importNiftyJson());
//			niftyPremap.putAll(market.niftypremap);
////	Thread.sleep(5000);
//			System.out.println("**nifmap***"+niftyMap);
//			System.out.println("**nifpremap***"+niftyPremap);
//			
//			System.out.println(niftyPremap.get("CEopenInterest").toString());
//			System.out.println(niftyMap.get("CEopenInterest").toString());

//		if(!(niftyPremap.get("CEopenInterest").toString().equals(niftyMap.get("CEopenInterest").toString())))  {
//			double CE =10.00;
//			double PE =8.00;
//			double dnPcr=PE/CE;
//			double upPcr=CE/PE;
//
//			MarketData _md = marketRepository.save(new MarketData(0,Integer.valueOf(niftyMap.get("strikePrice0").toString()),
//					niftyMap.get("Time0").toString(),Double.valueOf(niftyMap.get("underlyingValue0").toString()),
//					Long.valueOf(niftyMap.get("CEopenInterest0").toString()),Long.valueOf(niftyMap.get("CEVolume0").toString()),
//					Double.valueOf(niftyMap.get("CEimpliedVolatility0").toString()),Long.valueOf(niftyMap.get("CEchangeinOpenInterest0").toString()),
//					Long.valueOf(niftyMap.get("PEopenInterest0").toString()),Long.valueOf(niftyMap.get("PEVolume0").toString()),
//					Double.valueOf(niftyMap.get("PEimpliedVolatility0").toString()),
//					Long.valueOf(niftyMap.get("PEchangeinOpenInterest0").toString()),upPcr,dnPcr));
//			
//			up _up= upRepository.save(new up(0,Integer.valueOf(niftyMap.get("up1strikePrice").toString()),
//					niftyMap.get("Time").toString(),Double.valueOf(niftyMap.get("underlyingValue").toString()),
//					Long.valueOf(niftyMap.get("up1CEopenInterest").toString()),Long.valueOf(niftyMap.get("up1CEVolume").toString()),
//					Double.valueOf(niftyMap.get("up1CEimpliedVolatility").toString()),Long.valueOf(niftyMap.get("up1CEchangeinOpenInterest").toString()),
//					Long.valueOf(niftyMap.get("up1PEopenInterest").toString()),Long.valueOf(niftyMap.get("up1PEVolume").toString()),
//					Double.valueOf(niftyMap.get("up1PEimpliedVolatility").toString()),
//					Long.valueOf(niftyMap.get("up1PEchangeinOpenInterest").toString()),upPcr,dnPcr));
//		
//			upperUp _upperUp= upperUpRepository.save(new upperUp(0,Integer.valueOf(niftyMap.get("up2strikePrice").toString()),
//					niftyMap.get("Time").toString(),Double.valueOf(niftyMap.get("underlyingValue").toString()),
//					Long.valueOf(niftyMap.get("up2CEopenInterest").toString()),Long.valueOf(niftyMap.get("up2CEVolume").toString()),
//					Double.valueOf(niftyMap.get("up2CEimpliedVolatility").toString()),Long.valueOf(niftyMap.get("up2CEchangeinOpenInterest").toString()),
//					Long.valueOf(niftyMap.get("up2PEopenInterest").toString()),Long.valueOf(niftyMap.get("up2PEVolume").toString()),
//					Double.valueOf(niftyMap.get("up2PEimpliedVolatility").toString()),
//					Long.valueOf(niftyMap.get("up2PEchangeinOpenInterest").toString()),upPcr,dnPcr));
//			
//
//			downerDown _downerDown= downerDownRepository.save(new downerDown(0,Integer.valueOf(niftyMap.get("dn2strikePrice").toString()),
//					niftyMap.get("Time").toString(),Double.valueOf(niftyMap.get("underlyingValue").toString()),
//					Long.valueOf(niftyMap.get("dn2CEopenInterest").toString()),Long.valueOf(niftyMap.get("dn2CEVolume").toString()),
//					Double.valueOf(niftyMap.get("dn2CEimpliedVolatility").toString()),Long.valueOf(niftyMap.get("dn2CEchangeinOpenInterest").toString()),
//					Long.valueOf(niftyMap.get("dn2PEopenInterest").toString()),Long.valueOf(niftyMap.get("dn2PEVolume").toString()),
//					Double.valueOf(niftyMap.get("dn2PEimpliedVolatility").toString()),
//					Long.valueOf(niftyMap.get("dn2PEchangeinOpenInterest").toString()),upPcr,dnPcr));
				
			System.out.println("DATA SAVED TO DATABASE");
		
		

		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}

	}

	@PostMapping("/bankNifty")
	public void bankNifty() {
		try {

			HttpClient httpClient = HttpClients.createDefault();
			String apiUrl = "https://service.upstox.com/charts/v2/open/intraday/IN/NSE_INDEX%7CNifty%20Bank/30minute/2023-11-03";
			HttpGet httpGet = new HttpGet(apiUrl);

			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			String result = EntityUtils.toString(httpEntity);
			System.out.println(result);
			System.out.println(response);

			Path path = Paths.get("C:\\Users\\ayush\\Downloads\\BankNifty.json");
			Files.writeString(path, result, StandardCharsets.UTF_8);

			bnService.hammer();

//			System.out.println(premap);
//		
//			map=market.importJson();			
//			System.out.println("**map***"+map);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
//		try {
//		if(!(premap.get("CEopenInterest").toString().equals(map.get("CEopenInterest").toString())))  {
//		
//				
//				premap.put("CEopenInterest", map.get("CEopenInterest"));
//		
//				double dnPcr=(Long)map.get("CEopenInterest")/(Long)map.get("PEopenInterest");
//				double upPcr=(Long)map.get("up1CEopenInterest")/(Long)map.get("up1PEopenInterest");
//				MarketData _md = marketRepository.save(new MarketData(0,Integer.valueOf(map.get("strikePrice").toString()),map.get("Time").toString(),Double.valueOf(map.get("underlyingValue").toString()),Long.valueOf(map.get("CEopenInterest").toString()),Long.valueOf(map.get("CEVolume").toString()),Double.valueOf(map.get("CEimpliedVolatility").toString()),Long.valueOf(map.get("CEchangeinOpenInterest").toString()),Long.valueOf(map.get("PEopenInterest").toString()),Long.valueOf(map.get("PEVolume").toString()),Double.valueOf(map.get("PEimpliedVolatility").toString()),Long.valueOf(map.get("PEchangeinOpenInterest").toString()),upPcr,dnPcr));
//			System.out.println("DATA SAVED TO DATABASE");
//				
//			
//			}
//		else {
//						
//					}
//		
//		}
//		catch (Exception e) {
//			
//			e.printStackTrace();
//			
//		}
//	
//		

	}

	@PostMapping("/stocks")
	public void Stocks() throws FileNotFoundException {
		File file = new File("C:\\Users\\ayush\\OneDrive\\Desktop\\stockslist.txt");
		Scanner fc = new Scanner(file);
		int i = 0;
		String StockCode;
		while (fc.hasNextLine()) {
			if (i % 2 == 0) {
				StockCode = fc.nextLine();
				try {
					HttpClient httpClient = HttpClients.createDefault();
					String apiUrl = "https://www.nseindia.com/api/option-chain-equities?symbol=".concat(StockCode);
					HttpGet httpGet = new HttpGet(apiUrl);
					httpGet.setHeader("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36");
					httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
					httpGet.setHeader("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8");
					HttpResponse response = httpClient.execute(httpGet);
					HttpEntity httpEntity = response.getEntity();
					int code = response.getStatusLine().getStatusCode();
					String result = EntityUtils.toString(httpEntity);
					System.out.println(result);
					System.out.println(response);
					if (code == 200) {
						String p = "C:\\Users\\ayush\\Downloads\\Stocksdata\\";
						p = p.concat(StockCode).concat(".json");
						Path path = Paths.get(p);
						Files.writeString(path, result, StandardCharsets.UTF_8);
					}
					// stockservice.stockData();
				}

				catch (Exception e) {

					e.printStackTrace();

				}

			}
			i++;

		}

	}

	@PostMapping("/range")
	public void Range() {

		try {
			HttpClient httpClient = HttpClients.createDefault();
			String apiUrl = "https://www.nseindia.com/api/option-chain-indices?symbol=FINNIFTY";
			HttpGet httpGet = new HttpGet(apiUrl);
			httpGet.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36");
			httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
			httpGet.setHeader("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8");
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			String result = EntityUtils.toString(httpEntity);
			System.out.println(result);
			System.out.println(response);
			Path path = Paths.get("C:\\Users\\ayush\\Downloads\\MarketData.json");
			Files.writeString(path, result, StandardCharsets.UTF_8);
			range.getMarketRange();
		}

		catch (Exception e) {

			e.printStackTrace();

		}

	}

	

}