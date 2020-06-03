package controller;

import java.io.FileNotFoundException;

import model.BarPlot;
import model.LinePlot;
import model.Plot;
import model.ScatterPlot;

public class PlotFactory {
	public PlotFactory() {}
	
	public Plot createPlot(String plotKey) throws FileNotFoundException {
		switch(plotKey) {
			case "BarPlot": 
				return new BarPlot();
			case "LinePlot": 
				return new LinePlot();
			case "ScatterPlot":
				return new ScatterPlot();
			default:
				return null;
		}
	}
}