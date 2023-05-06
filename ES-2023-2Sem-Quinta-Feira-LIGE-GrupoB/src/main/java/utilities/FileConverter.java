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
	    list.removeAll(Arrays.asList("", null));
	    for (int j = 0; j < list.size(); j++)
	    {
		String value = list.get(j);
		String header = headers.get(j);
		if (j != list.size() - 1)
		{
		    writer.println("   \"" + header + "\": \"" + value + "\",");
		}
		else
		{
		    writer.println("   \"" + header + "\": \"" + value + "\"");
		}
	    }
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
}