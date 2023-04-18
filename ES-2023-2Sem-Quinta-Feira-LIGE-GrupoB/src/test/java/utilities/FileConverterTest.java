package utilities;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class FileConverterTest {

	@Test
	public void test() throws IOException {
	    	Path convertedJsonPath = Paths.get(FileConverter.csvTojson("src/test/resources/horario_exemplo.csv", "src/test/resources/horario_exemplo_convertido.json").getAbsolutePath());
	    	Path jsonPath = Paths.get("src/test/resources/horario_exemplo.json");
	    	System.out.println(Files.mismatch(convertedJsonPath, jsonPath));
		assert(Files.mismatch(convertedJsonPath, jsonPath)==-1);
		convertedJsonPath = Paths.get(FileConverter.csvTojson("src/test/resources/horario_exemplo.json", "src/test/resources/horario_exemplo_convertido.json").getAbsolutePath());
		assert(Files.mismatch(convertedJsonPath, jsonPath)==-1);
		
		Path convertedCsvPath = Paths.get(FileConverter.jsonTocsv("scr/test/resources/horario.exemplo.json", "src/test/resources/horario_exemplo_convertido.csv").getAbsolutePath());
		Path csvPath = Paths.get("src/test/resources/horario_exemplo.csv");
		System.out.println(Files.mismatch(convertedCsvPath, csvPath));
		assert(Files.mismatch(convertedCsvPath, csvParg)==-1);
		convertedCsvPath = Paths.get(FileConverter.jsonTocsv("scr/test/resources/horario.exemplo.json", "src/test/resources/horario_exemplo_convertido.csv").getAbsolutePath());
		assert(Files.mismatch(convertedCsvPath, csvParg)==-1);
}
