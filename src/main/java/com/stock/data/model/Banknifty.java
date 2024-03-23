package com.stock.data.model;



import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BankNifty")
public class Banknifty {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "strikePrice")
	private int strikePrice;


	

	@Column(name = "Time")
	private String time;
	
	
	@Column(name = "CurrentPrice")
	private double CurrentPrice;


	public Banknifty(int id, int strikePrice, String time, double currentPrice, double open, double close, double low,
			double high) {
		super();
		this.id = id;
		this.strikePrice = strikePrice;
		this.time = time;
		CurrentPrice = currentPrice;
		this.open = open;
		this.close = close;
		this.low = low;
		this.high = high;
	}

	@Override
	public String toString() {
		return "Banknifty [id=" + id + ", strikePrice=" + strikePrice + ", time=" + time + ", CurrentPrice="
				+ CurrentPrice + ", open=" + open + ", close=" + close + ", low=" + low + ", high=" + high + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStrikePrice() {
		return strikePrice;
	}

	public void setStrikePrice(int strikePrice) {
		this.strikePrice = strikePrice;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getCurrentPrice() {
		return CurrentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		CurrentPrice = currentPrice;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	@Column(name = "open")
	private double open;

	
	@Column(name = "close")
	private double close;	
	
	@Column(name = "low")
	private double low;
	
	@Column(name = "high")
	private double high;
	
}
	
