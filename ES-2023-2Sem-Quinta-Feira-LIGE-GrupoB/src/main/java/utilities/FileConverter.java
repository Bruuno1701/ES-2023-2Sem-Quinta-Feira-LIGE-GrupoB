package utilities;

import java.io.File;
import java.io.FileWriter;
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
import org.apache.commons.lang3.StringUtils;
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
//	System.out.println(f.exists());
	   
	if (!FilenameUtils.getExtension(jsonPath).equals("json"))
		    return f;
	    
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

		String csvString = String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s", curso, unidadeCurricular,
			turno, turma, inscritosNoTurno, diaDaSemana, horaInicioAula, horaFimAula, dataAula,
			salaAtribuida, lotacaoDaSala);
		estrutura.add(csvString);
	    }

	    file = new File(csvPath);
	    FileUtils.writeStringToFile(file, StringUtils.join(estrutura, "\n"), "UTF-8");   
	} catch (Exception e)
	{
	    e.printStackTrace();
	}
	return file;

    }
}
