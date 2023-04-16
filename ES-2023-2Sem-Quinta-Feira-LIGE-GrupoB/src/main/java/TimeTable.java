import java.io.File;
import java.io.IOException;

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
		try
		{
		    FileConverter.csvTojson(file, path);
		} catch (IOException e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	}
	
	public void saveAsCSV(String path) {
		FileConverter.JSONtoCSV(file, path);
	}
}
