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

    /**
     * Test method for {@link TimeTable#TimeTable(java.lang.String)}.
     */
    @Test
    public void testTimeTable()
    {
	fail("Not yet implemented");
    }

    /**
     * Test method for {@link TimeTable#saveAsJSON(java.lang.String)}.
     * 
     * @throws IOException
     */
    @Test
    public void testSaveAsJSON() throws IOException
    {
	String covertedcsvpath = "src/test/resources/horario_exemplo.json";
	TimeTable timetable = new TimeTable("src/test/resources/horario_exemplo.csv");
	timetable.saveAsJSON(covertedcsvpath);
	Path jsonpath = Paths.get("src/test/resources/horario_exemplo.json");
	Path convertedJsonPath = Paths.get(covertedcsvpath);
	System.out.println(Files.mismatch(convertedJsonPath, jsonpath));
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
	String covertedjsonpath = "src/test/resources/horario_exemplo.csv";
	TimeTable timetable = new TimeTable("src/test/resources/horario_exemplo.json");
	timetable.saveAsJSON(covertedjsonpath);
	Path csvpath = Paths.get("src/test/resources/horario_exemplo.csv");
	Path convertedJsonPath = Paths.get(covertedjsonpath);
	System.out.println(Files.mismatch(convertedJsonPath, csvpath));
	assert (Files.mismatch(convertedJsonPath, csvpath) == -1);
    }
}
