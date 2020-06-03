package model;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class LinePlot extends WriteContentsToIndexSuperClass {
	
	public LinePlot() {};
	
	public void implementationOfPlot(Statement stmt, Statement stmt2) throws SQLException, IOException {
		String contentsOfCSVForLinePlot= "symbol,date,price\r\n";
		String sql = "SELECT countries.COUNTRY_NAME, indicator.INDICATOR_NAME, years." + elementsOfGUI.getPeriod() + " AS YEAR, AVG(m.MEASUREMENT) AS AVG_MEASUREMENT\r\n" + 
				"FROM years, m, countries, indicator\r\n" + 
				"WHERE years.YEAR = m.YEAR AND m.COUNTRY_CODE = countries.COUNTRY_CODE AND m.INDICATOR_CODE = indicator.INDICATOR_CODE AND years.YEAR>=" + elementsOfGUI.getFromYear() + " AND years.YEAR<=" + elementsOfGUI.getToYear() + " AND (" + getMetricsConditionString() + ") AND (" + getCountriesConditionString() + ") AND m.MEASUREMENT != '\r'\r\n" + 
				"GROUP BY years." + elementsOfGUI.getPeriod() + " , countries.COUNTRY_NAME, indicator.INDICATOR_NAME";	
		initializeQuery(stmt, sql);
		
		addElementsToResultOfQuery(1);
		displayResultsOfQuery();
		
        for (String[] x : resultOfQuery) {
        	contentsOfCSVForLinePlot += x[1] + "," + x[0] + "," + x[2] + "\r";
        }  
        writeContentsToFile("lineplot.csv", contentsOfCSVForLinePlot);
        createIndexFile("indexLinePlot.html");
	}
}