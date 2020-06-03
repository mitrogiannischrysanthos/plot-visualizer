package testcases;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import model.ElementsOfGUI;
import model.PlotSuperClass;
import model.ScatterPlot;

class ScatterPlotTest {

	@Test
	void test() throws SQLException, IOException, URISyntaxException {
		String actualContentsOfLinePlot = "";
		try (Scanner scanner = new Scanner(new File("C:/Users/chris/OneDrive/Υπολογιστής/[ΜΥΕ030ΠΛΕ045] Προχωρημένα Θέματα Τεχνολογίας και Εφαρμογών Βάσεων Δεδομένων/server/scatterPlotTest.csv"));) {
			while (scanner.hasNextLine()) {
				actualContentsOfLinePlot += scanner.nextLine() + "\r\n";
			}
		}
		
		Connection connect = DriverManager.getConnection( "jdbc:mysql://localhost:3306/database2?useLegacysDatetimeCode=false&serverTimezone=UTC", "root", "155144444455" );
		PlotSuperClass plotSuperClass = new ScatterPlot();
		ArrayList<String> countries = new ArrayList<String>();
		countries.add("Greece");
		ArrayList<String> metrics = new ArrayList<String>();
		metrics.add("Chemicals (% of value added in manufacturing)\r");
		metrics.add("Fuel imports (% of merchandise imports)\r");
		ElementsOfGUI elementsOfGUI = new ElementsOfGUI("ScatterPlot", countries, metrics, "YEAR", "1978", "1994");
		plotSuperClass.setElementsOfGUI(elementsOfGUI);
		plotSuperClass.implementationOfPlot(connect.createStatement(), connect.createStatement());
		
		String expectedContentsOfLinePlot = "";
		try (Scanner scanner = new Scanner(new File("C:/Users/chris/OneDrive/Υπολογιστής/[ΜΥΕ030ΠΛΕ045] Προχωρημένα Θέματα Τεχνολογίας και Εφαρμογών Βάσεων Δεδομένων/server/scatterPlot.csv"));) {
			while (scanner.hasNextLine()) {
				expectedContentsOfLinePlot += scanner.nextLine() + "\r\n";
			}
		}
		assertEquals(actualContentsOfLinePlot, expectedContentsOfLinePlot);
	}

}
