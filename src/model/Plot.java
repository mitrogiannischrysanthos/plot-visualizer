package model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Statement;

public interface Plot {
	public void implementationOfPlot(Statement stmt, Statement stmt2) throws SQLException, IOException, URISyntaxException;
	public void setElementsOfGUI(ElementsOfGUI elementsOfGUI);
	public String getDisplayResultsOfSQLQuery();
}