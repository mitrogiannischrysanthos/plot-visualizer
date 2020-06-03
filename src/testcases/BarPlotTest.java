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
import model.BarPlot;
import model.ElementsOfGUI;
import model.PlotSuperClass;

class BarPlotTest {

	@Test
	void test() throws SQLException, IOException, URISyntaxException {
		String actualContentsOfLinePlot = "";
		try (Scanner scanner = new Scanner(new File("C:/Users/chris/OneDrive/Υπολογιστής/[ΜΥΕ030ΠΛΕ045] Προχωρημένα Θέματα Τεχνολογίας και Εφαρμογών Βάσεων Δεδομένων/server/barPlotTest.csv"));) {
			while (scanner.hasNextLine()) {
				actualContentsOfLinePlot += scanner.nextLine();
			}
		}
		
		Connection connect = DriverManager.getConnection( "jdbc:mysql://localhost:3306/database2?useLegacysDatetimeCode=false&serverTimezone=UTC", "root", "155144444455" );
		PlotSuperClass plotSuperClass = new BarPlot();
		ArrayList<String> countries = new ArrayList<String>();
		countries.add("Greece");
		ArrayList<String> metrics = new ArrayList<String>();
		metrics.add("Chemicals (% of value added in manufacturing)\r");
		metrics.add("Fuel imports (% of merchandise imports)\r");
		ElementsOfGUI elementsOfGUI = new ElementsOfGUI("BarPlot", countries, metrics, "YEAR", "1978", "1994");
		plotSuperClass.setElementsOfGUI(elementsOfGUI);
		plotSuperClass.implementationOfPlot(connect.createStatement(), connect.createStatement());
		
		String expectedContentsOfLinePlot = plotSuperClass.getDisplayResultsOfSQLQuery();
		expectedContentsOfLinePlot = expectedContentsOfLinePlot.replace("\r", "");
		expectedContentsOfLinePlot = expectedContentsOfLinePlot.replace("\n", " ");
		
		assertEquals(actualContentsOfLinePlot, expectedContentsOfLinePlot);
	}

}
