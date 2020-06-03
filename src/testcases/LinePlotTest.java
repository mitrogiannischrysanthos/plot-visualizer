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
import model.LinePlot;
import model.PlotSuperClass;

class LinePlotTest {

	@Test
	void test() throws SQLException, IOException, URISyntaxException {
		String actualContentsOfLinePlot = "";
		try (Scanner scanner = new Scanner(new File("C:/Users/chris/OneDrive/Υπολογιστής/[ΜΥΕ030ΠΛΕ045] Προχωρημένα Θέματα Τεχνολογίας και Εφαρμογών Βάσεων Δεδομένων/server/linePlotTest.csv"));) {
			while (scanner.hasNextLine()) {
				actualContentsOfLinePlot += scanner.nextLine() + "\r\n";
			}
		}
		
		Connection connect = DriverManager.getConnection( "jdbc:mysql://localhost:3306/database2?useLegacysDatetimeCode=false&serverTimezone=UTC", "root", "155144444455" );
		PlotSuperClass plotSuperClass = new LinePlot();
		ArrayList<String> countries = new ArrayList<String>();
		countries.add("Finland");
		countries.add("United States");
		ArrayList<String> metrics = new ArrayList<String>();
		metrics.add("Textiles and clothing (% of value added in manufacturing)\r");
		ElementsOfGUI elementsOfGUI = new ElementsOfGUI("LinePlot", countries, metrics, "YEAR", "1960", "1985");
		plotSuperClass.setElementsOfGUI(elementsOfGUI);
		plotSuperClass.implementationOfPlot(connect.createStatement(), connect.createStatement());
		
		String expectedContentsOfLinePlot = "";
		try (Scanner scanner = new Scanner(new File("C:/Users/chris/OneDrive/Υπολογιστής/[ΜΥΕ030ΠΛΕ045] Προχωρημένα Θέματα Τεχνολογίας και Εφαρμογών Βάσεων Δεδομένων/server/lineplot.csv"));) {
			while (scanner.hasNextLine()) {
				expectedContentsOfLinePlot += scanner.nextLine() + "\r\n";
			}
		}
		assertEquals(actualContentsOfLinePlot, expectedContentsOfLinePlot);
	}
}
