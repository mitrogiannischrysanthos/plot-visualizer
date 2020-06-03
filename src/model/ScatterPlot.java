package model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Statement;

public class ScatterPlot extends PlotSuperClass {
	private ContentsOfScatterPlot contentsOfScatterPlot;
	
	public ScatterPlot() {
		contentsOfScatterPlot = new ContentsOfScatterPlot();
	};
	
	public void implementationOfPlot(Statement stmt, Statement stmt2) throws URISyntaxException, IOException, SQLException{
		String contentsOfCSVForScatterPlot= "MetricXAxis,MetricYAxis,Year\r\n";
		String sql = "CREATE VIEW scatterPlotView AS\r\n" + 
				"SELECT countries.COUNTRY_NAME, indicator.INDICATOR_NAME, years." + elementsOfGUI.getPeriod() + " AS YEAR, AVG(m.MEASUREMENT) AS AVG_MEASUREMENT\r\n" + 
				"FROM years, m, countries, indicator\r\n" + 
				"WHERE years.YEAR = m.YEAR AND m.COUNTRY_CODE = countries.COUNTRY_CODE AND m.INDICATOR_CODE = indicator.INDICATOR_CODE AND years.YEAR>=" + elementsOfGUI.getFromYear() + " AND years.YEAR<=" + elementsOfGUI.getToYear() +" AND (" + getMetricsConditionString() + ") AND (" + getCountriesConditionString() + ") AND m.MEASUREMENT != '\r'\r\n" + 
				"GROUP BY years." + elementsOfGUI.getPeriod() + " , indicator.INDICATOR_NAME\r\n" + 
				"ORDER BY years." + elementsOfGUI.getPeriod();
		stmt.executeUpdate(sql);
		sql = "SELECT  view1.YEAR as YEAR, view1.AVG_MEASUREMENT as AVG_MEASUREMENT1, view2.AVG_MEASUREMENT as AVG_MEASUREMENT2\r\n" + 
				"FROM scatterPlotView as view1, scatterPlotView as view2\r\n" + 
				"WHERE view1.YEAR = view2.YEAR AND view1.INDICATOR_NAME != view2.INDICATOR_NAME AND view1.INDICATOR_NAME = \"" + elementsOfGUI.getMetrics().get(0) + "\"";
		initializeQuery(stmt, sql);
		sql = "DROP VIEW scatterPlotView";
		stmt2.executeUpdate(sql);
		
		addElementsToResultOfQuery(2);
        displayResultsOfQuery();
        
        for (String[] x : resultOfQuery) {
        	contentsOfCSVForScatterPlot += x[1] + "," + x[2] + "," + x[0] +"\r\n";
        }
        writeContentsToFile("scatterPlot.csv", contentsOfCSVForScatterPlot);
        String contents = contentsOfScatterPlot.setScatterPlotAxis(elementsOfGUI.getMetrics().get(0).replace("\r", ""), elementsOfGUI.getMetrics().get(1).replace("\r", ""), "Corellation of the two metrics in " + elementsOfGUI.getCountries().get(0));
        writeContentsToFile("index.html", contents);
	}
	
	public void addElementsToResultOfQueryHelper() throws SQLException{
		lineOfQuery[1] = resultSet.getString("AVG_MEASUREMENT1");
    	lineOfQuery[1] = lineOfQuery[1].replace("\r", "");
    	lineOfQuery[2] = resultSet.getString("AVG_MEASUREMENT2");
	}
}
