package view;

import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import controller.AccessInMySQLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class PlotView {
	public CheckBox linePlot;
	public CheckBox barPlot;
	public CheckBox scatterPlot;
	public CheckBox austria;
	public CheckBox australia;
	public CheckBox china;
	public CheckBox finland;
	public CheckBox greece;
	public CheckBox japan;
	public CheckBox mexico;
	public CheckBox portugal;
	public CheckBox spain;
	public CheckBox unitedStates;
	
	public CheckBox chemicals;
	public CheckBox fuelImports;
	public CheckBox grossNationalExpenditure;
	public CheckBox agriculturalRawMaterialsImports;
	public CheckBox exportOfGoodsAndServices;
	public CheckBox populationMale;
	public CheckBox textilesAndClothing;
	public CheckBox generalGovernmentFinal;
	public CheckBox co2Emissions;
	public CheckBox electricityProduction;
	
	private ArrayList<CheckBox> plots = new ArrayList<CheckBox>();
	private ArrayList<CheckBox> countriesObjects = new ArrayList<CheckBox>();
	private ArrayList<CheckBox> metricsObjects = new ArrayList<CheckBox>();
	private ArrayList<String> countries = new ArrayList<String>();
	private ArrayList<String> metrics = new ArrayList<String>();
	private HashMap<String, String> metricsOriginalNames = new HashMap<String,String>();
	
	public ChoiceBox period;
	public ChoiceBox fromYear;
	public ChoiceBox toYear;
	public TextArea displayTextArea;
	
	private String typeOfPlot;
	private AccessInMySQLController accessInMySQLController;
	
	@FXML
	public void initialize() throws SQLException, FileNotFoundException {
		accessInMySQLController = new AccessInMySQLController();
		
		metricsOriginalNames.put("Chemicals","Chemicals (% of value added in manufacturing)\r");
		metricsOriginalNames.put("Fuel imports","Fuel imports (% of merchandise imports)\r");
		metricsOriginalNames.put("Gross national expenditure","Gross national expenditure (% of GDP)\r");
		metricsOriginalNames.put("Agricultural raw materials imports","Agricultural raw materials imports (% of merchandise imports)\r");
		metricsOriginalNames.put("Exports of goods and services","Exports of goods and services (annual % growth)\r");
		metricsOriginalNames.put("Population male","Population male (% of total population)\r");
		metricsOriginalNames.put("Textiles and clothing","Textiles and clothing (% of value added in manufacturing)\r");
		metricsOriginalNames.put("General government final consumption expenditure","General government final consumption expenditure (annual % growth)\r");
		metricsOriginalNames.put("CO2 emissions from electricity and heat production total","CO2 emissions from electricity and heat production total (% of total fuel combustion)\r");
		metricsOriginalNames.put("Electricity production from hydroelectric sources","Electricity production from hydroelectric sources (% of total)\r");

		plots.add(linePlot);
		plots.add(barPlot);
		plots.add(scatterPlot);
		
		countriesObjects.add(austria);
		countriesObjects.add(australia);
		countriesObjects.add(china);
		countriesObjects.add(finland);
		countriesObjects.add(greece);
		countriesObjects.add(japan);
		countriesObjects.add(mexico);
		countriesObjects.add(portugal);
		countriesObjects.add(spain);
		countriesObjects.add(unitedStates);
		
		metricsObjects.add(chemicals);
		metricsObjects.add(fuelImports);
		metricsObjects.add(grossNationalExpenditure);
		metricsObjects.add(agriculturalRawMaterialsImports);
		metricsObjects.add(exportOfGoodsAndServices);
		metricsObjects.add(populationMale);
		metricsObjects.add(textilesAndClothing);
		metricsObjects.add(generalGovernmentFinal);
		metricsObjects.add(co2Emissions);
		metricsObjects.add(electricityProduction);
		
		period.getItems().addAll("YEAR", "FIVEYRPERIOD", "TENYRPERIOD", "TWENTYYRPERIOD");
		period.setValue("YEAR");
		
		for (int i=1960; i<2020; i++) {
			fromYear.getItems().add(i);
			toYear.getItems().add(i);
		}
		fromYear.setValue(1960);
		toYear.setValue(2019);
	}
	
	public void handlePlotAction(ActionEvent actionEvent) {
		this.typeOfPlot = getSelectedNameCheckBox(actionEvent); //TO-DO If plot not selected display error message in Display button.
		enableOnlyOneOption(plots, actionEvent);
		if (actionEvent.getSource() == scatterPlot) {
			countries.clear();
			for (CheckBox checkBox: countriesObjects) {
				checkBox.setSelected(false);
			}
			metrics.clear();
			for (CheckBox checkBox: metricsObjects) {
				checkBox.setSelected(false);
			}
		}
		enableMetricsCheckBox();
	}
	
	public void handleCountriesAction(ActionEvent actionEvent) {
		if (scatterPlot.isSelected()) {
			countries.clear();
			enableOnlyOneOption(countriesObjects, actionEvent);
		}
		if (countries.contains(getSelectedNameCheckBox(actionEvent))) {
			countries.remove(getSelectedNameCheckBox(actionEvent));
		} else {
			countries.add(getSelectedNameCheckBox(actionEvent));
		}
	}
	
	public void enableOnlyOneOption(ArrayList<CheckBox> checkBoxes, ActionEvent actionEvent) {
		for (CheckBox checkBox: checkBoxes) {
			if (!(actionEvent.getSource() == checkBox)) {
				checkBox.setSelected(false);
			}
		}	
	}
	
	public void handleMetricsAction(ActionEvent actionEvent) {
		if (metrics.contains(metricsOriginalNames.get(getSelectedNameCheckBox(actionEvent)))) {
			metrics.remove(metricsOriginalNames.get(getSelectedNameCheckBox(actionEvent)));
		} else {
			metrics.add(metricsOriginalNames.get(getSelectedNameCheckBox(actionEvent)));
		}
		
		if (scatterPlot.isSelected()) {
			if (metrics.size() == 2) {
				for (CheckBox checkBox: metricsObjects) {
					if (!checkBox.isSelected()) {
						checkBox.setDisable(true);
					}
				}	
			} else if (metrics.size() < 2) {
				enableMetricsCheckBox();	
			}		
		}
	}
	
	private String getSelectedNameCheckBox(ActionEvent actionEvent) {
		Pattern p = Pattern.compile("'(.*?)'");
		Matcher m = p.matcher(actionEvent.getSource().toString());
		System.out.println(actionEvent.getSource().toString());
		m.find();
		return m.group(1);
	}
	
	public void enableMetricsCheckBox() {
		for (CheckBox checkBox: metricsObjects) {
			if (checkBox.isDisable()) {
				checkBox.setDisable(false);
			}
		}
	}
	
	public void handleDisplayButton() throws Exception {
		displayTextArea.clear();
		int count = 0;
		for (CheckBox checkBox: plots) {
			if (checkBox.isSelected()) {
				count ++;	
				break;
			}
		}
		if (count == 0) {
			typeOfPlot = linePlot.getText();
			linePlot.setSelected(true);
		}
		if (countries.size() == 0) {
			displayTextArea.setText("You should choose at least one country. Please choose one.");
			return ;
		}
		if (metrics.size() == 0) {
			displayTextArea.setText("You should choose at least one metric. Please choose one.");
			return ;
		}
		if (scatterPlot.isSelected() && metrics.size() == 1) {
			displayTextArea.setText("You have choose ScatterPlot and you gave only one metric. Please give one more.");
			return ;
		}
		accessInMySQLController.setValuesOfElementsOfGUI(typeOfPlot, countries, metrics, String.valueOf(period.getValue()), String.valueOf(fromYear.getValue()), String.valueOf(toYear.getValue()));
		accessInMySQLController.enact();
		displayTextArea.setText(accessInMySQLController.getDisplayResultsOfSQLQuery());
		Desktop.getDesktop().browse(URI.create("http://127.0.0.1:8887"));
	}
}
