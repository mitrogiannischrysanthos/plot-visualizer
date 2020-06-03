package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public abstract class WriteContentsToIndexSuperClass extends PlotSuperClass{
	
	public WriteContentsToIndexSuperClass() {}
	
	public void addElementsToResultOfQueryHelper() throws SQLException {
		lineOfQuery[1] = resultSet.getString("COUNTRY_NAME") + " - " + resultSet.getString("INDICATOR_NAME");
    	lineOfQuery[2] = resultSet.getString("AVG_MEASUREMENT");
	}
	
	public void createIndexFile(String nameOfHTMLFile) throws IOException {
		File file = new File("C:/Users/chris/OneDrive/Υπολογιστής/[ΜΥΕ030ΠΛΕ045] Προχωρημένα Θέματα Τεχνολογίας και Εφαρμογών Βάσεων Δεδομένων/server/" + nameOfHTMLFile);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        String contents = new String(data, "UTF-8");
        writeContentsToFile("index.html", contents);
	}
}