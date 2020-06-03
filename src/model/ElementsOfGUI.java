package model;

import java.util.ArrayList;

public class ElementsOfGUI {
	private ArrayList<String> countries = new ArrayList<String>();
	private ArrayList<String> metrics = new ArrayList<String>();
	private String typeOfPlot;
	private String period;
	private String fromYear;
	private String toYear;
	
	public ElementsOfGUI(String typeOfPlot, ArrayList<String> countries, ArrayList<String> metrics, String period, String fromYear, String toYear) {
		this.typeOfPlot = typeOfPlot;
		this.countries = countries;
		this.metrics = metrics;
		this.period = period;
		this.fromYear = fromYear;
		this.toYear = toYear;
	}
	
	public String getTypeOfPlot() {
		return typeOfPlot;
	}
	
	public ArrayList<String> getCountries(){
		return countries;
	}
	
	public ArrayList<String> getMetrics(){
		return metrics;
	}
	
	public String getPeriod(){
		return period;
	}
	
	public String getFromYear(){
		return fromYear;
	}
	
	public String getToYear(){
		return toYear;
	}
}
