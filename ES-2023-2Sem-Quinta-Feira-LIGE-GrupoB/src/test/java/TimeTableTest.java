import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.junit.Test;

/**
 * 
 */

/**
 * @author Asus
 *
 */
public class TimeTableTest
{

    private static final String HORARIO_CSV = "src/test/resources/horario_exemplo.csv";
    private static final String HORARIO_JSON = "src/test/resources/horario_exemplo.json";
    private static final String HORARIO_CSV2 = "src/test/resources/horario_exemplo2.csv";
    private static final String HORARIO_JSON2 = "src/test/resources/horario_exemplo2.json";
    private static final String HORARIO_CSV_CONVERTIDO_JSON = "src/test/resources/horario_exemplo_convertido.json";
    private static final String HORARIO_JSON_CONVERTIDO_CSV = "src/test/resources/horario_exemplo_convertido.csv";

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
	FileReader bf1 = new FileReader(HORARIO_CSV2);
	FileReader bf2 = new FileReader(HORARIO_JSON_CONVERTIDO_CSV);

	assertTrue(IOUtils.contentEqualsIgnoreEOL(bf1, bf2));

	timetable = new TimeTable(
		"https://raw.githubusercontent.com/Bruuno1701/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/FileConverter/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/src/test/resources/horario_exemplo.json",
		DESTINO);

	timetable.saveAsCSV(HORARIO_JSON_CONVERTIDO_CSV);
	bf1 = new FileReader(HORARIO_JSON_CONVERTIDO_CSV);
	bf2 = new FileReader(HORARIO_CSV2);
	assertTrue(IOUtils.contentEqualsIgnoreEOL(bf1, bf2));
	bf1.close();
	bf2.close();
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
	FileReader bf1 = new FileReader(HORARIO_CSV_CONVERTIDO_JSON);
	FileReader bf2 = new FileReader(HORARIO_JSON2);

	assertTrue(IOUtils.contentEqualsIgnoreEOL(bf1, bf2));

	timetable = new TimeTable(
		"https://raw.githubusercontent.com/Bruuno1701/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/FileConverter/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/src/test/resources/horario_exemplo.csv",
		DESTINO);

	timetable.saveAsJSON(HORARIO_CSV_CONVERTIDO_JSON);
	bf1 = new FileReader(HORARIO_CSV_CONVERTIDO_JSON);
	bf2 = new FileReader(HORARIO_JSON2);

	assertTrue(IOUtils.contentEqualsIgnoreEOL(bf1, bf2));
	bf1.close();
	bf2.close();

    }

    private static final String CSV = "src/test/resources/horario_exemplo.csv";
    private static final String JSON = "src/test/resources/horario_exemplo.json";
    private static final String DESTINO = "src/test/resources/";
    private static final String GUARDAR1 = "C:\\Users\\Pedro Ferraz\\OneDrive - ISCTE-IUL\\Ambiente de Trabalho\\Iscte\\3º Ano\\2º Semestre\\ES\\Projeto\\outrosTestes";
    private static final String GUARDAR2 = "https://github.com/Bruuno1701/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/tree/FileConverter/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/src/test/resources";

    @Test
    public void testTimeTable2()
    {
	try
	{
	    new TimeTable(CSV);
	    new TimeTable(JSON);
	    new TimeTable(
		    "https://raw.githubusercontent.com/Bruuno1701/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/FileConverter/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/src/test/resources/horario_exemplo.json",
		    DESTINO);
	    new TimeTable(
		    "https://raw.githubusercontent.com/Bruuno1701/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/FileConverter/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/src/test/resources/horario_exemplo.csv",
		    DESTINO);
	} catch (Exception e)
	{
	    fail(e.getMessage());
	}
    }

//
    @Test
    public void testSaveFile()
    {
	try
	{
	    TimeTable t1 = new TimeTable(CSV);
	    TimeTable t2 = new TimeTable(
		    "https://raw.githubusercontent.com/Bruuno1701/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/FileConverter/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/src/test/resources/horario_exemplo.json",
		    DESTINO);
	    t1.saveFile(GUARDAR2);
	    t2.saveFile(GUARDAR1);
	} catch (Exception e)
	{
	    fail(e.getMessage());
	}

    }

    @Test
    public void testGetLessonsList()
    {
	TimeTable t = new TimeTable(HORARIO_JSON);
	String jsonText = "";
	try
	{
	    jsonText = FileUtils.readFileToString(new File(HORARIO_JSON), "UTF-8");
	} catch (IOException e)
	{
	}
	JSONArray jsonArray = new JSONArray(jsonText);
	assertEquals(t.getLessonsList().size(), jsonArray.length());
	
	t = new TimeTable(HORARIO_CSV);
	try
	{
	    CSVParser csvParser = CSVParser.parse(new File(HORARIO_CSV), Charset.forName("UTF-8"),
	    	CSVFormat.DEFAULT.withFirstRecordAsHeader().builder().setDelimiter(';').build());
	    assertEquals(t.getLessonsList().size(), csvParser.getRecords().size());
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    @Test
    public void testIsCSV() {
	TimeTable t = new TimeTable(HORARIO_CSV);
	
	assertTrue(t.isCSV());
	t.saveAsJSON(HORARIO_CSV_CONVERTIDO_JSON);
	assertFalse(t.isCSV());
	t = new TimeTable(HORARIO_JSON);
	assertFalse(t.isCSV());
	t.saveAsCSV(HORARIO_JSON_CONVERTIDO_CSV);
	assertTrue(t.isCSV());
	
    }
    
    @Test
    public void testIsjson() {
	TimeTable t = new TimeTable(HORARIO_JSON);
	assertTrue(t.isJSON());
	t.saveAsCSV(HORARIO_JSON_CONVERTIDO_CSV);
	assertFalse(t.isJSON());
	t = new TimeTable(HORARIO_CSV);
	assertFalse(t.isJSON());
	t.saveAsJSON(HORARIO_CSV_CONVERTIDO_JSON);
	assertTrue(t.isJSON());
    }
    
    @Test
    public void testGetFile() {
	assertEquals(new TimeTable(HORARIO_CSV).getFile(),new File(HORARIO_CSV));
	assertEquals(new TimeTable(HORARIO_JSON).getFile(),new File(HORARIO_JSON));
    }

}
