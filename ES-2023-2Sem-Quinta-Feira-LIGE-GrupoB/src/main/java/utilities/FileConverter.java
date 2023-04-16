package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class FileConverter {

    public static File csvTojson(File csvFile, String path) throws IOException
    {
//		String fileName = csvFile.getName();
//		if (fileName.substring(fileName.lastIndexOf(".") + 1).equals("json"))
//			return csvFile;
//		Path csvPath = Paths.get(csvFile.getAbsolutePath());

	File jsonFile = new File(path);
	PrintWriter writer = new PrintWriter(jsonFile,Charset.forName("UTF-8"));
	System.out.println("ficheiro criado");

	CSVParser csvParser = CSVParser.parse(csvFile, Charset.forName("UTF-8"),
		CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(';'));
	List<String> headers = csvParser.getHeaderNames();
	writer.println("[");
	for (CSVRecord record : csvParser)
	{
	    writer.println("\t{");
	    List<String> list = record.toList();
	    for (int i = 0; i < list.size(); i++)
	    {
		if (i != list.size() - 1)
		{
		    writer.println("\t\t\"" + headers.get(i) + "\": \"" + list.get(i) + "\",");
		}
		else
		{
		    writer.println("\t\t\"" + headers.get(i) + "\": \"" + list.get(i) + "\"");
		}
	    }
	    if (csvParser.getRecordNumber() != csvParser.getRecordNumber())
	    {
		writer.println("\t},");
	    }
	    else
	    {
		writer.println("\t}");
	    }
	}

	writer.println("]");
	try
	{
	    return jsonFile;
	}
	finally
	{
	    csvParser.close();
	    writer.close();
	}
    }

    public static File JSONtoCSV(File jsonFile, String path)
    {
	return null;
    }

    public static void main(String[] args) throws Exception
    {
	csvTojson(new File("src/test/resources/horario_exemplo.csv"), "lalal.json");
    }

}
