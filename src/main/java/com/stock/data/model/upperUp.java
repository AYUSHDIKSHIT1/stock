package com.stock.data.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Two_Strikeup")
public class upperUp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "upPcr")
	private double upPcr;
	
	@Column(name = "dnPcr")
	private double dnPcr;
	
	public double getUpPcr() {
		return upPcr;
	}

	public void setUpPcr(double upPcr) {
		this.upPcr = upPcr;
	}

	public double getDnPcr() {
		return dnPcr;
	}

	public void setDnPcr(double dnPcr) {
		this.dnPcr = dnPcr;
	}

	@Column(name = "strikePrice")
	private int strikePrice;

	@Column(name = "Time")
	private String time;	
	
	@Column(name = "CurrentPrice")
	private double CurrentPrice;
	

	public double getCurrentPrice() {
		return CurrentPrice;
	}

	public void setCurrentPrice(long currentPrice) {
		CurrentPrice = currentPrice;
	}

	@Column(name = "CEoi")
	private long CEoi;

	@Column(name = "CEvol")
	private long CEvol;
	
	@Column(name = "CEiv")
	private double CEiv;

	@Column(name = "CEcio")
	private long CEcio;


	@Column(name = "PEoi")
	private long PEoi;

	@Column(name = "PEvol")
	private long PEvol;
	
	@Column(name = "PEiv")
	private double PEiv;



	public upperUp(int id, int strikePrice, String time, double currentPrice, long cEoi,
			long cEvol, double cEiv, long cEcio, long pEoi, long pEvol, double pEiv, long pEcio, double upPcr, double dnPcr) {
		super();
		this.id = id;
		this.upPcr = upPcr;
		this.dnPcr = dnPcr;
		this.strikePrice = strikePrice;
		this.time = time;
		CurrentPrice = currentPrice;
		CEoi = cEoi;
		CEvol = cEvol;
		CEiv = cEiv;
		CEcio = cEcio;
		PEoi = pEoi;
		PEvol = pEvol;
		PEiv = pEiv;
		PEcio = pEcio;
	}

	@Column(name = "PEcio")
	private long PEcio;

	
	
	

	
	
	
	

	public long getId() {
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

	public long getCEoi() {
		return CEoi;
	}

	public void setCEoi(long cEoi) {
		CEoi = cEoi;
	}

	public long getCEvol() {
		return CEvol;
	}

	public void setCEvol(long cEvol) {
		CEvol = cEvol;
	}

	public double getCEiv() {
		return CEiv;
	}

	public void setCEiv(long cEiv) {
		CEiv = cEiv;
	}

	public long getCEcio() {
		return CEcio;
	}

	public void setCEcio(long cEcio) {
		CEcio = cEcio;
	}

	public long getPEoi() {
		return PEoi;
	}

	public void setPEoi(long pEoi) {
		PEoi = pEoi;
	}

	public long getPEvol() {
		return PEvol;
	}

	public void setPEvol(long pEvol) {
		PEvol = pEvol;
	}

	public double getPEiv() {
		return PEiv;
	}

	@Override
	public String toString() {
		return "upperUp [id=" + id + ", upPcr=" + upPcr + ", dnPcr=" + dnPcr + ", strikePrice=" + strikePrice
				+ ", time=" + time + ", CurrentPrice=" + CurrentPrice + ", CEoi=" + CEoi + ", CEvol=" + CEvol
				+ ", CEiv=" + CEiv + ", CEcio=" + CEcio + ", PEoi=" + PEoi + ", PEvol=" + PEvol + ", PEiv=" + PEiv
				+ ", PEcio=" + PEcio + "]";
	}

	public void setPEiv(long pEiv) {
		PEiv = pEiv;
	}

	public long getPEcio() {
		return PEcio;
	}

	public void setPEcio(long pEcio) {
		PEcio = pEcio;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
	
