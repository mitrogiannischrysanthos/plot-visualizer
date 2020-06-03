package model;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public abstract class PlotSuperClass implements Plot{
	public FileWriter myWriter;
	public ArrayList<String[]> resultOfQuery;
	public ResultSet resultSet;
	public String[] lineOfQuery;
	public ElementsOfGUI elementsOfGUI;
	private String displayResultsOfSQLQuery;
	
	public PlotSuperClass() {}
	
	public abstract void implementationOfPlot(Statement stmt, Statement stmt2) throws SQLException, IOException, URISyntaxException;
	
	public void initializeQuery(Statement stmt, String sql) throws SQLException {
		resultOfQuery = new ArrayList<String[]>();
		resultSet = stmt.executeQuery(sql);
	}
	
	public void setElementsOfGUI(ElementsOfGUI elementsOfGUI) {
		this.elementsOfGUI = elementsOfGUI;
	}
	
	public void displayResultsOfQuery() {
		displayResultsOfSQLQuery = "";
		for (String[] rowOfQuery : resultOfQuery) {
        	displayResultsOfSQLQuery +=rowOfQuery[0] + "  " + rowOfQuery[1] + "  " + rowOfQuery[2] + "\r\n";
        }
	}
	
	public void addElementsToResultOfQuery(int replaceNumber) throws SQLException {
		while (resultSet.next()) {
        	lineOfQuery = new String[3];
        	lineOfQuery[0] = resultSet.getString("YEAR").substring(0, 4);
        	addElementsToResultOfQueryHelper();
        	lineOfQuery[replaceNumber] = lineOfQuery[replaceNumber].replace("\r", "");
        	resultOfQuery.add(lineOfQuery);
        }
	}
	
	public String getCountriesConditionString() {
		String countriesConditionString = "";
		for (int i=0; i<elementsOfGUI.getCountries().size(); i++) {
			countriesConditionString += "countries.COUNTRY_NAME = " + "'" + elementsOfGUI.getCountries().get(i) + "'";
			if (i != elementsOfGUI.getCountries().size() - 1) {
				countriesConditionString += " OR ";
			}
		}
		return countriesConditionString;
	}
	
	public String getMetricsConditionString() {
		String metricsConditionString = "";
		for (int i=0; i<elementsOfGUI.getMetrics().size(); i++) {
			metricsConditionString += "indicator.INDICATOR_NAME = " + "'" + elementsOfGUI.getMetrics().get(i) + "'";
			if (i != elementsOfGUI.getMetrics().size() - 1) {
				metricsConditionString += " OR ";
			}
		}
		return metricsConditionString;
	}
	
	public abstract void addElementsToResultOfQueryHelper() throws SQLException;
	
	public void writeContentsToFile(String nameOfFile, String contents) throws IOException {
		myWriter = new FileWriter("C:/Users/chris/OneDrive/Υπολογιστής/[ΜΥΕ030ΠΛΕ045] Προχωρημένα Θέματα Τεχνολογίας και Εφαρμογών Βάσεων Δεδομένων/server/" + nameOfFile);
        myWriter.write(contents);
        myWriter.close();
	}
	
	public String getDisplayResultsOfSQLQuery() {
		return displayResultsOfSQLQuery;
	}
}