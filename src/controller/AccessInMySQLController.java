package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import model.ElementsOfGUI;
import model.Plot;

public class AccessInMySQLController {
	private Connection connect = null;
	private HashMap<String, Plot> plots = new HashMap<String,Plot>();
	private PlotFactory plotFactory;
	private ElementsOfGUI elementsOfGUI;
	
	public AccessInMySQLController() throws FileNotFoundException, SQLException{
		this.connect = DriverManager.getConnection( "jdbc:mysql://localhost:3306/database2?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "XXXX" );
		this.plotFactory = new PlotFactory();
		InitializePLot("./nameOfPlots.txt");
	}

	public void InitializePLot(String filePath) throws FileNotFoundException {
		FileInputStream inputStream = new FileInputStream(filePath);
		Scanner inputReader = new Scanner(inputStream);
		String plotNamePerLine = "";
		while (inputReader.hasNextLine()) {
			plotNamePerLine = inputReader.nextLine();
			plots.put(plotNamePerLine, plotFactory.createPlot(plotNamePerLine));
		}
		inputReader.close();
	}
	
	public void setValuesOfElementsOfGUI(String typeOfPlot, ArrayList<String> countries, ArrayList<String> metrics, String period, String fromYear, String toYear) {
		elementsOfGUI = new ElementsOfGUI(typeOfPlot, countries, metrics, period, fromYear, toYear);
		plots.get(elementsOfGUI.getTypeOfPlot()).setElementsOfGUI(elementsOfGUI);
	}
	
	public void enact() throws Exception {
	    plots.get(elementsOfGUI.getTypeOfPlot()).implementationOfPlot(connect.createStatement(), connect.createStatement());
	}
	
	public String getDisplayResultsOfSQLQuery() {
		return plots.get(elementsOfGUI.getTypeOfPlot()).getDisplayResultsOfSQLQuery();
	}
}