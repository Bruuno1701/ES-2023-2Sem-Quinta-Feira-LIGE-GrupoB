package utilities;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class FileConverter
{

    /**
     * @author filipa
     * Devolve um ficheiro json resultante da conversão de um ficheiro csv.
     *
     * @param csvPath  caminho para o ficheiro csv a ser convertido
     * @param jsonPath caminho onde o ficheiro json resultante da conversão deve ser
     *                 guardado
     * @return o ficheiro json resultante da conversão
     * @throws IOException
     */
    public static File csvTojson(String csvPath, String jsonPath) throws IOException
    {
	File csvFile = new File(csvPath);
	System.out.println(FilenameUtils.getExtension(csvPath));
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

    public static File jsonTocsv(String jsonPath, String csvPath)
    {
	File f = new File(jsonPath);
	    
	if (!FilenameUtils.getExtension(jsonPath).equals("json"))
		    return f;
	    
	String jsonString;

	JSONObject horario;
	File file = null;

	try
	{
	    jsonString = FileUtils.readFileToString(f, "UTF-8");
	    JSONTokener token = new JSONTokener(jsonString);
	    horario = new JSONObject(token);

	    JSONArray documento = new JSONArray().put(horario);

	    List<String> estrutura = new ArrayList<String>();

	    String cabecalho = ("Curso, Unidade Curricular, Turno, Turma, Inscritos no Turno, Dia da Semana, Hora de Inicio da Aula, Hora Final da Aula, Data da Aula, Sala Atribuida, Lotacao da Sala");
	    estrutura.add(cabecalho);

	    for (int i = 0; i < documento.length(); i++)
	    {
		JSONObject object = documento.getJSONObject(i);

		String curso = object.getString("Curso");
		String unidadeCurricular = object.getString("Unidade Curricular");
		String turno = object.getString("Turno");
		String turma = object.getString("Turma");
		String inscritosNoTurno = object.getString("Inscritos no turno");
		String diaDaSemana = object.getString("Dia da semana");
		String horaInicioAula = object.getString("Hora início da aula");
		String horaFimAula = object.getString("Hora fim da aula");
		String dataAula = object.getString("Data da aula");
		String salaAtribuida = object.getString("Sala atribuída à aula");
		String lotacaoDaSala = object.getString("Lotação da sala");

		String csvString = String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s", curso, unidadeCurricular,
			turno, turma, inscritosNoTurno, diaDaSemana, horaInicioAula, horaFimAula, dataAula,
			salaAtribuida, lotacaoDaSala);
		estrutura.add(csvString);
	    }

	    file = new File(csvPath, "horario_exemplo_convertido.csv");
	    FileUtils.writeLines(file, estrutura);
	} catch (Exception e)
	{
	    e.printStackTrace();
	}
	return file;

    }
}
