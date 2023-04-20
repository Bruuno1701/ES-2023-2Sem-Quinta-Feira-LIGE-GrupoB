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

    private static final String HORARIO_CSV = "src/test/resources/horario_exemplo.csv";
    private static final String HORARIO_JSON = "src/test/resources/horario_exemplo.json";
    private static final String HORARIO_CSV_CONVERTIDO_JSON = "src/test/resources/horario_exemplo_convertido.json";
    private static final String HORARIO_JSON_CONVERTIDO_CSV = "src/test/resources/horario_exemplo_convertido.csv";


    /**
     * Test method for {@link TimeTable#saveAsJSON(java.lang.String)}.
     * 
     * @throws IOException
     */
    @Test
    public void testSaveAsJSON() throws IOException
    {
    	//TimeTable timetable = new TimeTable(HORARIO_CSV);
    	//timetable.saveAsJSON(HORARIO_CSV_CONVERTIDO_JSON);
    	Path jsonpath = Paths.get(HORARIO_JSON);
    	Path convertedJsonPath = Paths.get(HORARIO_CSV_CONVERTIDO_JSON);
    	//assert (Files.mismatch(convertedJsonPath, jsonpath) == -1);
	
	TimeTable timetable = new TimeTable(
		    "https://raw.githubusercontent.com/Bruuno1701/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/FileConverter/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/src/test/resources/horario_exemplo.csv"
		    ,DESTINO);
	System.out.println(timetable.getFile().getAbsolutePath());
	timetable.saveAsJSON(DESTINO);
	timetable.saveFile(DESTINO);
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
	timetable.saveAsJSON(DESTINO);
	Path csvpath = Paths.get(HORARIO_CSV);
	Path convertedJsonPath = Paths.get(DESTINO);
	
	assert (Files.mismatch(convertedJsonPath, csvpath) == -1);
	
	timetable = new TimeTable(
		    "https://raw.githubusercontent.com/Bruuno1701/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/FileConverter/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/src/test/resources/horario_exemplo.csv"
			,"src/test/resources/");
	timetable.saveAsJSON(HORARIO_CSV_CONVERTIDO_JSON);
	assert (Files.mismatch(convertedJsonPath, csvpath) == -1);
    }
    
    
    
    private static final String CSV = "src/test/resources/horario_exemplo.csv";
    private static final String JSON = "src/test/resources/horario_exemplo.json";
    private static final String DESTINO = "C:\\Users\\Pedro Ferraz\\OneDrive - ISCTE-IUL\\Ambiente de Trabalho\\Iscte\\3º Ano\\2º Semestre\\ES\\Projeto\\testes";
    private static final String GUARDAR1 = "C:\\Users\\Pedro Ferraz\\OneDrive - ISCTE-IUL\\Ambiente de Trabalho\\Iscte\\3º Ano\\2º Semestre\\ES\\Projeto\\outrosTestes";
    private static final String GUARDAR2 = "https://github.com/Bruuno1701/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/tree/FileConverter/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/src/test/resources";
    
    @Test
    public void testTimeTable2()
    {
	try
	{
	    new TimeTable(CSV);
	    new TimeTable(JSON);
	    new TimeTable("https://raw.githubusercontent.com/Bruuno1701/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/FileConverter/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/src/test/resources/horario_exemplo.json",DESTINO);
	    new TimeTable("https://raw.githubusercontent.com/Bruuno1701/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/FileConverter/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/src/test/resources/horario_exemplo.csv",DESTINO);
	} catch (Exception e)
	{
	    fail(e.getMessage());
	}
    }
    
    @Test
    public void testSaveFile() 
    {
    try 
    {
    	TimeTable t1 = new TimeTable(CSV);
    	TimeTable t2 = new TimeTable("https://raw.githubusercontent.com/Bruuno1701/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/FileConverter/ES-2023-2Sem-Quinta-Feira-LIGE-GrupoB/src/test/resources/horario_exemplo.json",DESTINO);
    	t1.saveFile(GUARDAR2);
    	t2.saveFile(GUARDAR1);
    }catch(Exception e) 
    {
    	fail(e.getMessage());
    }
    	
    }
    
    
    
}
