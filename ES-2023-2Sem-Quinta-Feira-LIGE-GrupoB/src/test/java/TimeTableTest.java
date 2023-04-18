import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import utilities.FileConverter;

/**
 * 
 */

/**
 * @author Asus
 *
 */
public class TimeTableTest
{

    private static final String HORARIO_CSV = "src/test/resources/horario_exemplo1.csv";
    private static final String HORARIO_JSON = "src/test/resources/horario_exemplo2.json";
    private static final String HORARIO_CSV_CONVERTIDO_JSON = "src/test/resources/";		///horario_exemplo_convertido.json
    private static final String HORARIO_JSON_CONVERTIDO_CSV = "src/test/resources/";		///horario_exemplo_convertido.csv

    /**
     * Test method for {@link TimeTable#TimeTable(java.lang.String)}.
     */
    @Test
    public void testTimeTable()
    {
	try
	{
	    new TimeTable(HORARIO_JSON);
	    new TimeTable(HORARIO_CSV);
	    new TimeTable("https://github.com/filipafranco/projeto/blob/main/horario_exemplo.json",
	    		HORARIO_CSV_CONVERTIDO_JSON);
	    new TimeTable("https://github.com/filipafranco/projeto/blob/main/horario_exemplo.csv",
	    		HORARIO_JSON_CONVERTIDO_CSV);
	} catch (Exception e)
	{
	    fail(e.getMessage());
	}
    }

    /**
     * Test method for {@link TimeTable#saveAsJSON(java.lang.String)}.
     * 
     * @throws IOException
     */
    @Test
    public void testSaveAsJSON() throws IOException
    {
	TimeTable timetable = new TimeTable(HORARIO_CSV);
	timetable.saveAsJSON(HORARIO_CSV_CONVERTIDO_JSON);
	Path jsonpath = Paths.get(HORARIO_JSON);
	Path convertedJsonPath = timetable.getFile().toPath();
	//Path convertedJsonPath = Paths.get(HORARIO_CSV_CONVERTIDO_JSON);
	assert (Files.mismatch(convertedJsonPath, jsonpath) == -1);

	timetable = new TimeTable("https://github.com/filipafranco/projeto/blob/main/horario_exemplo.csv",
			HORARIO_CSV_CONVERTIDO_JSON);
	timetable.saveAsJSON(HORARIO_CSV_CONVERTIDO_JSON);
	jsonpath = Paths.get(HORARIO_JSON);
	convertedJsonPath = timetable.getFile().toPath();
	//convertedJsonPath = Paths.get(HORARIO_CSV_CONVERTIDO_JSON);
	assert (Files.mismatch(convertedJsonPath, jsonpath) == -1);
    }

    /**
     * Test method for {@link TimeTable#saveAsCSV(java.lang.String)}.
     * 
     * @throws IOException
     */
    @Test
    public void testSaveAsCSV() throws IOException
    {
	TimeTable timetable = new TimeTable(HORARIO_JSON);
	timetable.saveAsCSV(HORARIO_JSON_CONVERTIDO_CSV);
	Path csvpath = Paths.get(HORARIO_CSV);
	//Path convertedJsonPath = Paths.get(HORARIO_JSON_CONVERTIDO_CSV);
	Path convertedJsonPath = timetable.getFile().toPath();
	
	assert (Files.mismatch(convertedJsonPath, csvpath) == -1);
    }
}
