import java.io.File;

import utilities.FileConverter;

public class TimeTable {
	private File file;
	
	public TimeTable(String path) {
		this.file = findFile(path);
	}

	private File findFile(String path) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void saveAsJSON(String path) {
		FileConverter.CSVtoJSON(file, path);
	}
	
	public void saveAsCSV(String path) {
		FileConverter.CSVtoJSON(file, path);
	}
}
