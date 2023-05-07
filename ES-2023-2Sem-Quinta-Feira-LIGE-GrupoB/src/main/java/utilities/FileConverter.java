package utilities;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import java.io.*;
import biweekly.*;
import biweekly.component.VEvent;
import biweekly.io.*;
import biweekly.property.*;
import biweekly.Biweekly;
import biweekly.ICalendar;


import org.apache.commons.csv.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import gestaohorarios.TimeTable;

/**
 * 
 * A classe FileConverter é uma classe de funções estáticas que servem para
 * fazer a conversão de ficheiros de json para csv e vice versa.
 *
 */
public class FileConverter
{
    
    private static final Logger LOGGER = Logger.getLogger(TimeTable.class.getName());
    
    /**
     * Função que devolve um ficheiro json resultante da conversão de um ficheiro
     * csv.
     * 
     * @param csvPath  Caminho para o ficheiro csv a ser convertido.
     * @param jsonPath Caminho onde o ficheiro json resultante da conversão deve ser
     *                 guardado.
     * @return ficheiro json resultante da conversão.
     * @throws IOException se o parse do ficheiro csv não puder acontecer.
     */
    public static File csvTojson(String csvPath, String jsonPath) throws IOException
    {
	File csvFile = new File(csvPath);
	if (!FilenameUtils.getExtension(csvPath).equals("csv"))
	    return csvFile;

	File jsonFile = new File(jsonPath);
	PrintWriter writer = new PrintWriter(jsonFile, Charset.forName("UTF-8"));

	CSVParser csvParser = CSVParser.parse(csvFile, Charset.forName("UTF-8"),
		CSVFormat.DEFAULT.withFirstRecordAsHeader().builder().setDelimiter(';').build());
	List<CSVRecord> records = csvParser.getRecords();
	List<String> headers = csvParser.getHeaderNames();
	writer.println("[");
	for (int i = 0; i < records.size(); i++)
	{
	    CSVRecord record = records.get(i);
	    writer.println(" {");
	    List<String> list = record.toList();
//	    list.removeAll(Arrays.asList("", null));
	    boolean start =false;
	    for (int j = 0; j < list.size(); j++)
	    {
		
		String value = list.get(j);
		String header = headers.get(j);
		if (value != null && !value.equals(""))
		{
		    if (j != 0 && start)
		    {
			writer.println(",");
		    }
		    writer.print("   \"" + header + "\": \"" + value + "\"");
		    start = true;
		}
	    }
	    writer.println();
	    if (i != records.size() - 1)
	    {
		writer.println(" },");
	    }
	    else
	    {
		writer.println(" }");
	    }
	}

	writer.print("]");
	try
	{
	    return jsonFile;
	}

	finally
	{
	    writer.close();
	    csvParser.close();
	}
    }

    /**
     * Função que devolve um ficheiro csv resultante da conversão de um ficheiro
     * json.
     * 
     * @param jsonPath Caminho para o ficheiro json a ser convertido.
     * @param csvPath  Caminho onde o ficheiro csv resultante da conversão deve ser
     *                 guardado.
     * @return ficheiro csv resultante da conversão.
     */
    public static File jsonTocsv(String jsonPath, String csvPath)
    {
	File f = new File(jsonPath);

	if (!FilenameUtils.getExtension(jsonPath).equals("json"))
	{
	    return f;
	}

	String jsonString;
	File file = null;

	try
	{
	    jsonString = FileUtils.readFileToString(f, "UTF-8");

	    JSONArray documento = new JSONArray(jsonString);

	    List<String> estrutura = new ArrayList<String>();
	    estrutura.add(new String());

	    String cabecalho = ("Curso;Unidade Curricular;Turno;Turma;Inscritos no turno;Dia da semana;Hora início da aula;Hora fim da aula;Data da aula;Sala atribuída à aula;Lotação da sala");
	    estrutura.add(cabecalho);

	    for (int i = 0; i < documento.length(); i++)
	    {
		JSONObject object = documento.getJSONObject(i);

		String curso = object.optString("Curso", "");
		String unidadeCurricular = object.optString("Unidade Curricular", "");
		String turno = object.optString("Turno", "");
		String turma = object.optString("Turma", "");
		String inscritosNoTurno = object.optString("Inscritos no turno", "");
		String diaDaSemana = object.optString("Dia da semana", "");
		String horaInicioAula = object.optString("Hora início da aula", "");
		String horaFimAula = object.optString("Hora fim da aula", "");
		String dataAula = object.optString("Data da aula", "");
		String salaAtribuida = object.optString("Sala atribuída à aula", "");
		String lotacaoDaSala = object.optString("Lotação da sala", "");

		String csvString = String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s", curso, unidadeCurricular, turno,
			turma, inscritosNoTurno, diaDaSemana, horaInicioAula, horaFimAula, dataAula, salaAtribuida,
			lotacaoDaSala);
		estrutura.add(csvString);
	    }

	    file = new File(csvPath);
	    FileUtils.writeStringToFile(file, StringUtils.join(estrutura, "\n"), "UTF-8");
	} catch (Exception e)
	{
	    LOGGER.log(Level.WARNING, e.getMessage());
	}
	return file;

    }

	
	/**
     * @param icsPath o path do ficheiro ics
     * @param csvPath local onde vai ser guardado o ficheiro
     * 
     * @throws IOException
     * 
     * @return File retorna o ficheiro como csv
     */
	public static File IcsToCsv(String icsPath, String csvPath) throws IOException {
		FileInputStream fis = new FileInputStream(icsPath);
	    ICalendar ical = Biweekly.parse(fis).first();
	    List<VEvent> events = ical.getEvents();
	    
	    CSVFormat csvFormat = CSVFormat.DEFAULT.withDelimiter(';');
	    CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(csvPath), csvFormat);
	    csvPrinter.printRecord();
	    csvPrinter.printRecord("Curso","Unidade Curricular", "Turno","Turma","Inscritos no turno","Dia da semana" ,"Hora início da aula", "Hora fim da aula", "Data da aula", "Sala atribuída à aula", "Lotação da sala");
	    
	    for (VEvent event : events) {
	        String[] lines = event.getDescription().getValue().split("\n");

	        String unidadeExecucao = null;
	        String turno = null;
	        String inicio = null;
	        String fim = null;
	        String data = null;
	        String curso = null;
	        String turma = null;
	        String inscritosNoTurno = null;
	        String diaDaSemana= null;
	        String sala = null;
	        String lotacao = null;

	        for (String line : lines) {
	            if (line.startsWith("Unidade de execução:")) {
	                unidadeExecucao = line.substring(line.indexOf(":") + 1).trim();
	            } else if (line.startsWith("Turno:")) {
	                turno = line.substring(line.indexOf(":") + 1).trim();
	            } else if (line.startsWith("Início:")) {
	            	String inicioString = line.substring(line.indexOf(":") + 1).trim();
	            	data = inicioString.split(" ")[0];  
	                inicio = inicioString.split(" ")[1];
	            } else if (line.startsWith("Fim:")) {
	            	String fimString = line.substring(line.indexOf(":") + 1).trim();
	                fim = fimString.split(" ")[1];
	            }else if (line.startsWith("LOCATION:")) {
	                sala = line.substring(line.indexOf(":") + 1).trim();
	                System.out.println("sala");
	            }
	        }

	        csvPrinter.printRecord(curso,unidadeExecucao, turno,turma,inscritosNoTurno,diaDaSemana, inicio, fim, data,sala,lotacao);
	    }
	    csvPrinter.flush();
	    csvPrinter.close();
	    File csvFile = new File(csvPath);
	    return csvFile;
	}
	

}