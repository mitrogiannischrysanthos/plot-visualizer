package model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class BarPlot extends WriteContentsToIndexSuperClass {
	
	public BarPlot() {};
	
	public void implementationOfPlot(Statement stmt, Statement stmt2) throws URISyntaxException, IOException, SQLException{
		JSONArray external_array = new  JSONArray();
		int K = elementsOfGUI.getCountries().size()*elementsOfGUI.getMetrics().size();
		String sql = "CREATE VIEW viewWithNulls AS\r\n" + 
				"SELECT countries.COUNTRY_NAME, indicator.INDICATOR_NAME, years." + elementsOfGUI.getPeriod() + " AS YEAR, AVG(m.MEASUREMENT) AS AVG_MEASUREMENT\r\n" + 
				"FROM years, m, countries, indicator\r\n" + 
				"WHERE years.YEAR = m.YEAR AND m.COUNTRY_CODE = countries.COUNTRY_CODE AND m.INDICATOR_CODE = indicator.INDICATOR_CODE AND years.YEAR>=" + elementsOfGUI.getFromYear() + " AND years.YEAR<=" + elementsOfGUI.getToYear() + " AND (" + getMetricsConditionString() + ") AND (" + getCountriesConditionString() + ")\r\n" + 
				"GROUP BY years." + elementsOfGUI.getPeriod() + " , countries.COUNTRY_NAME, indicator.INDICATOR_NAME\r\n" + 
				"ORDER BY years." + elementsOfGUI.getPeriod();
		stmt.executeUpdate(sql);
		sql = "CREATE VIEW viewWithoutNulls AS\r\n" + 
				"SELECT countries.COUNTRY_NAME, indicator.INDICATOR_NAME, years." + elementsOfGUI.getPeriod() + " AS YEAR, AVG(m.MEASUREMENT) AS AVG_MEASUREMENT\r\n" + 
				"FROM years, m, countries, indicator\r\n" + 
				"WHERE years.YEAR = m.YEAR AND m.COUNTRY_CODE = countries.COUNTRY_CODE AND m.INDICATOR_CODE = indicator.INDICATOR_CODE AND years.YEAR>=" + elementsOfGUI.getFromYear() + " AND years.YEAR<=" + elementsOfGUI.getToYear() + " AND (" + getMetricsConditionString() + ") AND (" + getCountriesConditionString() + ") AND m.MEASUREMENT != '\r'\r\n" + 
				"GROUP BY years." + elementsOfGUI.getPeriod() + " , countries.COUNTRY_NAME, indicator.INDICATOR_NAME\r\n" + 
				"ORDER BY years." + elementsOfGUI.getPeriod();
		stmt.executeUpdate(sql);
		sql = "SELECT viewWithNulls.COUNTRY_NAME, viewWithNulls.INDICATOR_NAME, viewWithNulls.YEAR, viewWithNulls.AVG_MEASUREMENT as AVG_MEASUREMENT_NULL, COALESCE(viewWithoutNulls.AVG_MEASUREMENT, 0) as AVG_MEASUREMENT_NOT_NULL, GREATEST(viewWithNulls.AVG_MEASUREMENT, COALESCE(viewWithoutNulls.AVG_MEASUREMENT, 0)) as AVG_MEASUREMENT\r\n" + 
				"FROM viewWithNulls \r\n" + 
				"LEFT JOIN viewWithoutNulls\r\n" + 
				"ON viewWithoutNulls.COUNTRY_NAME = viewWithNulls.COUNTRY_NAME AND viewWithoutNulls.INDICATOR_NAME = viewWithNulls.INDICATOR_NAME AND viewWithoutNulls.YEAR = viewWithNulls.YEAR";
		initializeQuery(stmt, sql);
		sql = "DROP VIEW viewWithNulls";
		stmt2.executeUpdate(sql);
		sql = "DROP VIEW viewWithoutNulls";
		stmt2.executeUpdate(sql);
        
        addElementsToResultOfQuery(2); 
        displayResultsOfQuery();
		
		for(int i=0; i< resultOfQuery.size(); i = i+K) { 
			JSONArray arr = new  JSONArray();
			JSONObject categories = new JSONObject();
			categories.put("categorie", resultOfQuery.get(i)[0]);
			for(int j=i; j<K+i; j++) {
				JSONObject values = new JSONObject();
				values.put("value", Float.parseFloat(resultOfQuery.get(j)[2]));
				values.put("rate", resultOfQuery.get(j)[1]);
				arr.add(values);
			}
			categories.put("values", arr);
			external_array.add(categories);
		} 
        writeContentsToFile("barPlot.json", external_array.toJSONString());
        createIndexFile("indexCorrectBarPlot.html");
	}
}
