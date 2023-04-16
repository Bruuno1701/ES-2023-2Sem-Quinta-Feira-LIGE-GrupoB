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
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class FileConverter
{

    public static File csvTojson(File csvFile, String path) throws IOException
    {
	File jsonFile = new File(path);
	PrintWriter writer = new PrintWriter(jsonFile, Charset.forName("UTF-8"));

	CSVParser csvParser = CSVParser.parse(csvFile, Charset.forName("UTF-8"),
		CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(';'));
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

    public static File JSONtoCSV(String path)
    {
	File f = new File(path);
	String jsonString;

	JSONObject horario;
	File file = null;

	try
	{
	    jsonString = FileUtils.readFileToString(f, "UTF-8");
	    JSONTokener token = new JSONTokener(jsonString); // vai buscar ao objeto JSON o array a converter
	    horario = new JSONObject(token);

	    JSONArray documento = new JSONArray().put(horario); // coloca o objeto num array

	    List<String> estrutura = new ArrayList<String>();

	    String cabecalho = ("Curso, Unidade Curricular, Turno, Turma, Inscritos no Turno, Dia da Semana, Hora de Inicio da Aula, Hora Final da Aula, Data da Aula, Sala Atribuida, Lotacao da Sala");
	    estrutura.add(cabecalho);

	    for (int i = 0; i < documento.length(); i++)
	    {
		JSONObject object = documento.getJSONObject(i);

		String curso = object.getString("curso");
		String unidadeCurricular = object.getString("unidadeCurricular");
		String turno = object.getString("turno");
		String turma = object.getString("turma");
		int inscritosNoTurno = object.getInt("inscritosNoTurno");
		String diaDaSemana = object.getString("diaDaSemana");
		String horaInicioAula = object.getString("horaInicioAula");
		String horaFimAula = object.getString("horaFimAula");
		String dataAula = object.getString("dataAula");
		String salaAtribuida = object.getString("salaAtribuida");
		int lotacaoDaSala = object.getInt("lotacaoDaSala");

		String csvString = String.format("%s, %s, %s, %s, %d, %s, %s, %s, %s, %s, %d", curso, unidadeCurricular,
			turno, turma, inscritosNoTurno, diaDaSemana, horaInicioAula, horaFimAula, dataAula,
			salaAtribuida, lotacaoDaSala); // converte o array JSON numa string CSV
		estrutura.add(csvString);
	    }

	    file = new File("output.csv");
	    FileUtils.writeLines(file, estrutura);
	} catch (Exception e)
	{
	    e.printStackTrace();
	}
	return file;

    }

    public static File JSONtoCSV(File jsonFile, String path)
    {
	return null;
    }

}
