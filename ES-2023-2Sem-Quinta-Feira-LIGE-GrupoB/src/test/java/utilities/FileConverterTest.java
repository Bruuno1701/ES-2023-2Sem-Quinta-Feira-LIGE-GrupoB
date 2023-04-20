package utilities;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class FileConverterTest {

	@Test
	public void testcsvTojson() throws IOException {
		Path convertedJsonPath = Paths.get(FileConverter.csvTojson("src/test/resources/horario_exemplo.csv",
				"src/test/resources/horario_exemplo_convertido.json").getAbsolutePath());
		Path jsonPath = Paths.get("src/test/resources/horario_exemplo.json");
		BufferedReader bf1 = new BufferedReader(new FileReader(convertedJsonPath.toFile()));
		BufferedReader bf2 = new BufferedReader(new FileReader(jsonPath.toFile()));
		
		assertTrue(IOUtils.contentEqualsIgnoreEOL(bf1, bf2));
		convertedJsonPath = Paths.get(FileConverter.csvTojson("src/test/resources/horario_exemplo.json",
				"src/test/resources/horario_exemplo_convertido.json").getPath());
		assertTrue(convertedJsonPath.equals(jsonPath));
	}

	@Test
	public void testjsonTocsv() throws IOException {
		Path convertedCsvPath = Paths.get(FileConverter.jsonTocsv("src/test/resources/horario_exemplo.json",
				"src/test/resources/horario_exemplo_convertido.csv").getAbsolutePath());
		Path csvPath = Paths.get("src/test/resources/horario_exemplo.csv");

		BufferedReader bf1 = new BufferedReader(new FileReader(convertedCsvPath.toFile()));
		BufferedReader bf2 = new BufferedReader(new FileReader(csvPath.toFile()));

		assertTrue(IOUtils.contentEqualsIgnoreEOL(bf1, bf2));
		
		convertedCsvPath = Paths.get(FileConverter.jsonTocsv("src/test/resources/horario_exemplo.csv",
				"src/test/resources/horario_exemplo_convertido.csv").getPath());
		assertTrue(convertedCsvPath.equals(csvPath));
		
	}
}
